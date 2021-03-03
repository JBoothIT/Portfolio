from flask import Flask, render_template, request, flash, redirect, url_for
from flask_login import LoginManager, current_user, login_user, logout_user
from flask_migrate import Migrate

from datetime import datetime, timedelta
from flask_sqlalchemy import SQLAlchemy

from flask_wtf import FlaskForm
from wtforms import StringField, SubmitField,  SelectField

from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
import smtplib

import random


import json

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = "postgresql://Team2:2Team2@164.111.161.243/Team2DB"
db = SQLAlchemy(app)
migrate = Migrate(app, db)

# HERE WE WILL HAVE OUR db CLASS; THIS CLASS WILL ALLOW US TO DEFINE AND MANIPULATE DATA THAT WE CAN STORE

class weatherData(db.Model):
    #Here we are giving a name to a table that we create
    _tablename_ = 'example'

    id = db.Column(db.Integer, primary_key=True)
    date = db.Column(db.String())
    temp = db.Column(db.Integer())

    def __init__(self, date, temp):
        self.date = date
        self.temp = temp

    def __repr__(self):
        return f"<Weather {self.temp}>"

class applianceList(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    apptype = db.Column(db.String())
    location = db.Column(db.String())

    def __init__(self, apptype, location):
        self.apptype = apptype
        self.location = location

    def __repr__(self):
        return f"<Appliance {self.location}>"

class historical(db.Model):
    
    id = db.Column(db.Integer, primary_key=True)
    date = db.Column(db.String())
    waterusage = db.Column(db.Integer())
    powerusage = db.Column(db.Integer())


    def __init__(self, date, waterusage, powerusage):
        self.date = date
        self.waterusage = waterusage
        self.powerusage = powerusage

    def __repr__(self):
        return f"<Water usage {self.waterusage}>"

class Usage(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    app_id = db.Column(db.Integer, db.ForeignKey('appliance_list.id'), nullable = False)
    on = db.Column(db.String())
    off = db.Column(db.String())
    usage_type = db.Column(db.Integer())

    def __init__(self, app_id, on, off, usage_type):
        self.app_id = app_id
        self.on = on
        self.off = off
        self.usage_type = usage_type

    def __repr__(self):
        return f"Usage {self.id}, {self.app_id}, {self.on}, {self.off}, {self.usage_type}"

class Facts(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    fact = db.Column(db.String())

    def __init__(self, fact):
        self.fact = fact

    def __repr__(self):
        return f"Facts {self.fact}"

db.create_all()
db.session.commit()

def jsonBuilder():
    bigJson = {}
    #pulls all unique locations
    bigDict = applianceList.query.all()
    for i in bigDict:
        keylist = []
        if(bigJson.get(i.location) is None):
            #keylist.clear()
            valueList = applianceList.query.filter(applianceList.location == i.location).all()
            #pulls each appliance for every location
            for j in valueList:
                subdict = {}
                statuses = Usage.query.filter(Usage.off == '', Usage.on != '', Usage.app_id == j.id).first()
                subdict.clear()
                subdict.update({'name':j.apptype})
                
                if(statuses is None):
                    subdict.update({'status':'off'})
                else:
                    subdict.update({'status':'on'})
                
                keylist.append(subdict)  

            bigJson[i.location] = keylist
    return bigJson

objectStatus = jsonBuilder()

################# HISTORICAL TABLE CALCULATIONS #######################
###################### RUN AT END OF DAY ##############################
def calculate(apptype, loc, on, off):
    appliance_toggled = applianceList.query.filter(applianceList.location == loc, applianceList.apptype == apptype).first()
    '''add later to take extra time and insert into next day if on past midnight'''
    '''strips datetime strings in database to get time difference'''
    strp_On = datetime.strptime(on, '%Y-%m-%d %H:%M:%S.%f')
    strp_Off = datetime.strptime(off, '%Y-%m-%d %H:%M:%S.%f')
    #difference of on and off times for calculations
    min_diff = divmod((strp_Off - strp_On).total_seconds(), 60)[0] + (divmod((strp_Off - strp_On).total_seconds(), 60)[1] / 100)
    
    #check if current date has entry in historical table
    checkHistory = historical.query.filter(historical.date.ilike(off.split(" ")[0] + "%")).count()
    #if no entry for that day exists
    if(checkHistory == 0):
        now = datetime.now()
        #add current day to historical table
        print("no historical day detected adding new one " + datetime.strftime(datetime.now().date(),"%Y-%m-%d"))
        newHistoricalDay = historical(datetime.strftime(datetime.now().date(),"%Y-%m-%d"), 0, fridge())
        db.session.add(newHistoricalDay)
        db.session.commit()
        print("new day added on " + datetime.strftime(datetime.now().date(),"%Y-%m-%d"))
        #check last entry in usage table
        lastUsageEntry = Usage.query.order_by(Usage.on.desc()).first()
        #last "on" date entered into usage table
        lastOn = datetime.strptime(lastUsageEntry.on, '%Y-%m-%d %H:%M:%S.%f')
        #last "off" date entered into usage table
        lastOff = datetime.strptime(lastUsageEntry.off, '%Y-%m-%d %H:%M:%S.%f')
        #if "off" time is in the next day
        if(lastOff.day != lastOn.day):
            midnight = datetime(now.year, now.month, now.day, 0, 0, 0, 0)
            endOfDay = datetime(on.year, on.month, on.day, 23, 59, 59,999)
            
            '''for final entry of previous day'''
            #get difference of between last usage on time and 11:59 pm and add to total
            min_diff = divmod((endOfDay - on).total_seconds(), 60)[0] + (divmod((endOfDay - on).total_seconds(), 60)[1] / 100)
            addToTotal(apptype, min_diff, on, loc)
            #Purge Usage
            Usage.delete()
            db.session.commit()
            
            '''for first entry of next day'''
            #add leftover time from midnight to "off" time to fresh usage table and add to total of new historical day
            nextDayEntry = Usage(appliance_toggled.id, datetime.strftime(midnight, '%Y-%m-%d %H:%M:%S.%f'), off,1)
            db.session.add(nextDayEntry)
            db.session.commit()
            #get difference of time between midnight of current day and 'off' time of appliance
            min_diff = divmod((off- midnight).total_seconds(), 60)[0] + (divmod((off - midnight).total_seconds(), 60)[1] / 100)
            addToTotal(apptype, min_diff, datetime.strftime(off, '%Y-%m-%d %H:%M:%S.%f'),loc)
        
        #if 'on' and 'off' in same day but day not in historical 
        #MAINTAINS NEW USAGE ENTRY BUT PURGES ALL FROM DAY BEFORE#
        else:
            Usage.query.delete()
            db.session.commit()
            entry = Usage(appliance_toggled.id, on, off, 1)
            db.session.add(entry)
            db.session.commit()
            addToTotal(apptype, min_diff, off, loc)
    
    #if day exists in historical, just add to existing totals
    else:
        addToTotal(apptype, min_diff, off, loc)

def addToTotal(apptype, min_diff, off, loc):
    #####THIS IS GOOD TO GO######
    pullHistory = historical.query.filter(historical.date.ilike(off.split(" ")[0] + "%")).first()
    totalPower = float(pullHistory.powerusage)
    totalWater = float(pullHistory.waterusage)
    
    if (apptype == 'overheadLight' or apptype == 'lamp1' or apptype == 'lamp2'):
        totalPower += lights(min_diff)

    elif(apptype == 'hvac'):
        totalPower += (3500 * min_diff) * 0.000017
    
    elif (apptype == 'microwave'):
        totalPower += microwave(min_diff)
    
    elif (apptype == 'stove'):
        totalPower += stove(min_diff)
    
    elif (apptype == 'oven'):
        totalPower += oven(min_diff)
    
    elif (apptype == 'tv'):
        totalPower += tv(min_diff, loc)
    
    elif (apptype == 'exhaustFan'):
        totalPower += bathFan(min_diff)

    elif (apptype == 'bath'):
        totalPower += bathPower()
        totalWater += bathWater()
    
    elif (apptype == 'shower'):
        totalPower += showerPower()
        totalWater += showerWater()

    elif (apptype == 'dishwasher'):
        totalPower += dishwashPower()
        totalWater += dishwashWater()
    
    elif (apptype == 'clothesWasher'):
        totalPower += clothwashPower()
        totalWater += clothwashWater()
    
    elif (apptype == 'clothesDryer'):
        totalPower += clothDryer()
        
    pullHistory.waterusage = totalWater
    pullHistory.powerusage = round(totalPower,2)
    db.session.commit()
    return

def microwave(duration):
    print("Running microwave")
    return (duration * 1100) * 0.000017
    
def stove(duration):
    print("Running stove")
    return (duration * 3500) * 0.000017

def oven(duration):
    print("Running oven")
    return (duration * 4000) * 0.000017

def tv(duration,location):
    if(location == 'livingRoom'):
        print("Running living room tv")
        return (duration * 636) * 0.000017
    elif(location == 'masterBedroom'):
        print("Running master bedroom tv")
        return (duration * 100) * 0.000017

def bathFan(duration):
    print("Running bath exhaust fan")
    return (30 * duration) * 0.000017

def bathPower():
    return (18000 * 16.25) * 0.000017

def showerPower():
    return (18000 * 19.5) * 0.000017

def bathWater():
    print('Taking a bath')
    return 30

def showerWater():
    print('Taking a shower')
    return 25

def fridge():
    return (150 * (24 * 60)) * 0.000017

def lights(duration):
    print("Running light")
    return (duration * 60) * 0.000017

def dishwashPower():
    print("Running dishwasher")
    return (45 * 1800 + (24 * 4500)) * 0.000017

def dishwashWater():
    return 6 

def clothwashPower():
    print("Running clothes washer")
    return (500 * 30 + (68 * 4500)) * 0.000017

def clothwashWater():
    return 20

def clothDryer():
    print("Running clothes dryer")
    return (3000 * 30) * 0.000017

#########################################################

@app.route('/home')
@app.route('/')
def homepage():

    hisfact = []
    log = Facts.query.filter_by().all()
    # for i in log:
        # print(i.fact)
    for i in log:
        hisfact.append(str(i.fact))
    # print(hisfact[0])

    randomhistfact = random.choice(hisfact)

    print(randomhistfact)
    return render_template('home.html', randomhistfact = randomhistfact)

@app.route('/floorplan', methods = ['GET', 'POST'])
def floorplan():
    print('made it to floorplan')
    # outsidetemp = []
    tempdate = []

    index = random.randint(1,100)
    query = weatherData.query.all()
    outsidetemp = query[index].temp
    tempdate = query[index].date
    currentTempQuery = Usage.query.filter(Usage.app_id == 52).first()
    currentTemp = currentTempQuery.usage_type


    print('we made it')
    if request.method == "POST":

        print(request.data.decode('utf-8'))
        data = json.loads((request.data.decode('utf-8')))

        if(data['name'] == 'overheadLight' or data['name'] == 'lamp1' or data['name'] == 'lamp2'):
            appliance_toggled = applianceList.query.filter(applianceList.location == data['room'], applianceList.apptype == data['name']).first()
            if(data['status'] == 'on'):
                print('inserting record')
                log = Usage(appliance_toggled.id, datetime.now(), '', 1)
                db.session.add(log)
                db.session.commit()
            elif(data['status'] == 'off'):
                '''pull query of existing log and add off time to datetime.now()'''
                print('closing record')
                insertOff = Usage.query.filter(Usage.app_id == appliance_toggled.id, Usage.on != '', Usage.off == '').first()
                insertOff.off = datetime.now()
                db.session.commit()
                calculate(data['name'],data['room'],insertOff.on,insertOff.off)

        
        
    # elif request.method == "POST":
    #     temperature = str(request.data).split(':')[1][:-2]
    #    temperature = str(request.data).split(':')[1][:-2]
    #     print ('current temp' + temperature)
        # print(temperature)
        # return render_template('floorplan.html',  objectStatus=objectStatus)
    # else:
        
    #     print(outsidetemp)
    #     print(tempdate)

    #     return render_template('floorplan.html', outsidetemp = outsidetemp, objectStatus=objectStatus)

        
    return render_template('floorplan.html', outsidetemp = outsidetemp,  objectStatus=jsonBuilder(), currentTemp=currentTemp)

@app.route('/iconcal', methods = ['POST'])
def iconcal():
    print('we made it')
    if request.method == "POST":
        
        print(request.data.decode('utf-8'))
        data = json.loads((request.data.decode('utf-8')))

        # for k in data:
        #     print(k)
        # print(type(request.data))
        print(data["status"])
        #'on' 'off'
        print(data["room"])
        print(data["name"])
        #Calculations Start Here:
        appliance_toggled = applianceList.query.filter(applianceList.location == data['room'], applianceList.apptype == data['name']).first()
        if(data['status'] == 'on'):
            print('inserting record')
            log = Usage(appliance_toggled.id, datetime.now(), '', 1)
            db.session.add(log)
            db.session.commit()
        elif(data['status'] == 'off'):
            '''pull query of existing log and add off time to datetime.now()'''
            print('closing record')
            insertOff = Usage.query.filter(Usage.app_id == appliance_toggled.id, Usage.on != '', Usage.off == '').first()
            insertOff.off = datetime.now()
            db.session.commit()
            calculate(data['name'],data['room'],insertOff.on,insertOff.off)
        
       
        
    return ('success')

@app.route('/tempcal', methods = ['POST'])
def tempcal():

    index = random.randint(1,100)
    query = weatherData.query.all()
    outsidetemp = query[index].temp
    tempdate = query[index].date
    if request.method == "POST":
        temperature = str(request.data).split(':')[1][:-2]
        #updating one entry for every temp change, one log in Usage
        hvac = applianceList.query.filter(applianceList.apptype == 'hvac', applianceList.location == 'livingRoom').first()
        checkUsage = Usage.query.filter(Usage.app_id == 52).count()
        #add entry for hvac if no entry
        if(checkUsage == 0):
            print("no hvac for today")
            #runs once per day 
            if(abs(int(outsidetemp) - int(temperature)) > 10):
                print('outside temp out of range')
                #add 2 mintues of runtime for 24 hours
                outsideDayUse = datetime.now() + timedelta(seconds = (60 * 48))
                print(outsideDayUse)
                #get minute difference 
                outside_diff = divmod((outsideDayUse - datetime.now()).total_seconds(), 60)[0]
                addHvac = Usage(52, datetime.strftime(datetime.now(), '%Y-%m-%d %H:%M:%S.%f'), datetime.strftime(outsideDayUse, '%Y-%m-%d %H:%M:%S.%f'),int(temperature))
            else:
                outside_diff = 0
                print('outside temp in range')
                #add placeholder entry
                addHvac = Usage(52, datetime.strftime(datetime.now(), '%Y-%m-%d %H:%M:%S.%f'), datetime.strftime(datetime.now(), '%Y-%m-%d %H:%M:%S.%f'),int(temperature))
            db.session.add(addHvac)
            db.session.commit()
            print("new hvac added with outside_diff = " + str(outside_diff))
        
        queryHvac = Usage.query.filter(Usage.app_id == 52).first()
        if(abs(int(temperature) - 70) >= 2):
            outside_diff = 0
            print("temp outside range")
            now = datetime.now()
            #change string to datetime for subtraction (min_diff)
            strp_On = datetime.strptime(queryHvac.on, '%Y-%m-%d %H:%M:%S.%f')
            strp_Off = datetime.strptime(queryHvac.off, '%Y-%m-%d %H:%M:%S.%f')
            print(strp_On)
            ##current total time run up to this point
            min_diff = divmod((strp_Off - strp_On).total_seconds(), 60)[0]
            #add number of extra minutes run (subtract time from external temp, subtract time run to this point)
            #leaves excess minutes run
            addedMin = abs(int(temperature) - queryHvac.usage_type)
            #update "on" time to current time and "off" time to current time + min between 70 and current temp
            newOff = datetime.now() + timedelta(seconds=(60 * (min_diff + addedMin)))
            print("Running HVAC for: " + str(min_diff + addedMin) + " minutes")
            #update hvac entry with current time + all minutes ran during day
            queryHvac.on = now
            queryHvac.off = newOff
            queryHvac.usage_type = int(temperature)
            print("usage_type")
            db.session.commit()
            print("Total hvac minutes: " + str(min_diff + outside_diff))
        else:
            min_diff = 0
            
    addToTotal('hvac', (min_diff + outside_diff), queryHvac.on, 'living room')

    return ('success')

@app.route('/graphs')
def maitenance():

    hisfact = []
    log = Facts.query.filter_by().all()
    # for i in log:
        # print(i.fact)
    for i in log:
        hisfact.append(str(i.fact))
    # print(hisfact[0])

    randomhistfact = random.choice(hisfact)

    print(randomhistfact)

    octmonth = str(10)
   
    # labels = []
    powerValuesOct = []
    waterValuesOct = []
    powerValuesNov= []
    waterValuesNov = []
    powerValues = []
    waterValues = []
    labelsOct = []
    labelsNov = []
    costsOct = []
    costsNov = []
    
    legend = 'Monthly Data'
    
    log = historical.query.filter(historical.date.ilike('2020-' + octmonth + '%')).order_by(historical.id)
    for i in log:
        powerValuesOct.append(float(i.powerusage))
        waterValuesOct.append(float(i.waterusage))
        labelsOct.append(i.date)
    for i in range(len(powerValuesOct)):
         totalOct = round(((powerValuesOct[i]) * .12),2) + round(((waterValuesOct[i] / 748)*2.52), 2)
         costsOct.append(totalOct)
    # print(costs)
    # print( total)
    print(powerValuesOct)

    novmonth = str(11)
    
    log1 = historical.query.filter(historical.date.ilike('2020-' + novmonth + '%')).order_by(historical.id)
    for c in log1:
        powerValuesNov.append(float(c.powerusage))
        waterValuesNov.append(float(c.waterusage))
        labelsNov.append(c.date)
    for c in range(len(powerValuesNov)):
         totalNov = round(((powerValuesNov[c]) * .12),2) + round(((waterValuesNov[c] / 748)*2.52), 2)
         costsNov.append(totalNov)
    print(labelsNov)

    powerValues = powerValuesOct + powerValuesNov
    waterValues = waterValuesOct + waterValuesNov
    
    return render_template('graphs.html', costsNov = costsNov, costsOct = costsOct, powerValuesOct = powerValuesOct, powerValuesNov = powerValuesNov, waterValuesOct = waterValuesOct, waterValuesNov = waterValuesNov,  randomhistfact = randomhistfact,  powerValues= powerValues, waterValues=waterValues,  labelsNov=labelsNov, 
    labelsOct = labelsOct, legend=legend)
@app.route('/maitenance')
def graphest():
    '''
    for p in objectStatus["master_bathroom"]:
        print(p["name"] + " " + p["status"])
        '''
    return render_template('maitenance.html', objectStatus=jsonBuilder())

@app.route('/about')
def about():
    return render_template('about.html')

if __name__ == "__main__":
	app.run(debug=True)

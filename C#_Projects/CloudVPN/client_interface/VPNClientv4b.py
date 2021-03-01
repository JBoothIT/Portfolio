# -*- coding: utf-8 -*-
"""
Created on Thu Jul 18 14:43:39 2019

@author: Jeremy Booth and John Bedingfield
same as VPNClientv3b_test.py
"""

import tkinter
import time
import sys
import tkinter as tk
import datetime
from chargeClass import Charge

#create the GUI form
x = tkinter.Tk()
x.title('VPN Client')
# x.geometry("365x308")
# x.resizable(0,0)

# import main code ========== CHANGED THESE TWO LINES TO MATCH BACK-END =====
from AlphaVPNFunctionsv4b import startVPN
from AlphaVPNFunctionsv4b import deleteVPNs

#Various labels, textboxes and combobox
from tkinter import *
from tkinter import ttk
Label(x, text='Connection', justify=LEFT).grid(row=0)
Label(x, text='# of Hops', justify=LEFT).grid(row=1)
tb = ttk.Combobox(width=15)
tb['values'] = (" ", "Virginia", "Ohio", "California", "Oregon", "London", "Stockholm", "Tokyo")
tb1 = ttk.Combobox(width=15)
tb1['values'] = ('', 1, 2, 3, 4, 5)
tb.grid(row=0, column=1)
tb1.grid(row=1, column=1)
a = StringVar()
stat1 = Label(x, textvariable=a, fg='red', justify=LEFT).grid(row=2)

# variables for the clock and charges
start_time = time.time()
myCharges = Charge()

def tick(time1=''):
    # get the current local time from the PC
    time2 = time.strftime('%H:%M:%S')

    # if time string has changed, update it
    if time2 != time1:
        time1 = time2
        current_time = time.time()
        delt_time = myCharges.get_delta_time(current_time)
        clock.config(text=delt_time)
        charges = myCharges.get_charges()
        totalCost.set(str(charges))
    # calls itself every 200 milliseconds
    # to update the time display as needed
    clock.after(200, tick)
    

def calcCosts():
    delta_time = (time.time() - start_time)
    delt_time = time.strftime("%H:%M:%S", time.gmtime(delta_time))
    print(delt_time)
    clock.config(text=delt_time)
    #clock.config(text=delta_time_inHMS)
    
    chrgs = delta_time * 0.0001
    charges = '${:,.2f}'.format(chrgs)
    totalCost.set(str(charges))
    return

clock = tk.Label(x, font=('times', 20, 'bold'))
#clock.pack(fill='both', expand=1)
clock.grid(row=6, column=0)

totalCost = tk.StringVar()
cost = tk.Label(x, textvariable=totalCost, font=('times', 20, 'bold'))
cost.grid(row=6, column=2)
charges = 0.00
totalCost.set(str(charges))


#Clear Text and selections from the Form
def clearTB():
	tb.delete('0', END)
	tb1.delete('0', END)
	a.set('Cleared')

#Open a VPN connection and make the specified number of hops to the destination
def connTB():
    location = tb.get()
    numHops = tb1.get()
    a.set("Setting up your VPN")
    # calling AlphaVPNFunctions to get it all started
    startVPN(location, numHops)
    a.set("Connected")
    start_time = time.time()
    myCharges.set_start_time(start_time)
    tick()
	
#Disconnect from the VPN Connection
def disconTB():
    location = tb.get()
    myCharges.set_areWeChargingFalse()
    a.set("Shutting down your VPN")
    # calling AlphaVPNFunctions to shut it all down
    deleteVPNs(location)
    a.set("Disconnected")
    

#Buttons
button0 = tkinter.Button(x, text='Connect', width=10, command=connTB)
button1 = tkinter.Button(x, text='Clear', width=10, command=clearTB)
button2 = tkinter.Button(x, text='Disconnect', width=10, command=disconTB)
button3 = tkinter.Button(x, text='Exit', width=10, command=x.destroy)
button0.grid(row=3, column=0)
button1.grid(row=3, column=1)
button2.grid(row=3, column=2)
button3.grid(row=4, column=1)
#tick()

x.mainloop()


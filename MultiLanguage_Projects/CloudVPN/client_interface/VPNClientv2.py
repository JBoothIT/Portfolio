# -*- coding: utf-8 -*-
"""
Created on Thu Jul 18 14:43:39 2019

@author: Jeremy
"""

import tkinter
import time
import sys
import tkinter as tk
#create the GUI form
x = tkinter.Tk()
x.title('VPN Client')
# x.geometry("365x308")
# x.resizable(0,0)

# import main code
from AlphaVPNFunctionsv3 import startVPN
from AlphaVPNFunctionsv3 import deleteVPNs

#Various labels, textboxes and combobox
from tkinter import *
from tkinter import ttk
Label(x, text='Connection', justify=LEFT).grid(row=0)
Label(x, text='# of Hops', justify=LEFT).grid(row=1)
tb = ttk.Combobox(width=15)
tb['values'] = (" ", "Virginia", "Ohio", "California", "Oregon", "Stockholm", "Tokyo")
tb1 = ttk.Combobox(width=15)
tb1['values'] = ('', 1, 2, 3, 4, 5)
tb.grid(row=0, column=1)
tb1.grid(row=1, column=1)
a = StringVar()
stat1 = Label(x, textvariable=a, fg='red', justify=LEFT).grid(row=2)

# variables for the clock and charges
def tick(time1=''):
    # get the current local time from the PC
    time2 = time.strftime('%H:%M:%S')
    # if time string has changed, update it
    if time2 != time1:
        time1 = time2
        clock.config(text=time2)
        calcCosts()
    # calls itself every 200 milliseconds
    # to update the time display as needed
    clock.after(200, tick)
    
start_time = time.time()
def calcCosts():
    delta_time = (time.time() - start_time)
    print(delta_time)
    #full_time = datetime.timedelta(seconds=0.01 * delta_time.total_seconds())
    #full_time = datetime.timedelta(seconds=0.01 * delta_time)
    #print(full_time)
    
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
charges = 0.01
totalCost.set(str(charges))


#Clear Text and selections from the Form
def clearTB():
	tb.delete('0', END)
	tb1.delete('0', END)
	a.set('Cleared')

#Disconnect from the VPN Connection [I think this is connect]
def connTB():
    location = tb.get()
    numHops = tb1.get()
    startVPN(location, numHops)
    a.set("Connected")
    start_time = time.time()
	
#Open a VPN connection and make the specified number of hops to the destination
def disconTB():
    location = tb.get()
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
tick()

x.mainloop()


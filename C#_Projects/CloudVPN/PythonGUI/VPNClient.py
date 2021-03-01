import tkinter
#create the GUI form
x = tkinter.Tk()
x.title('VPN Client')
# x.geometry("365x308")
# x.resizable(0,0)

# import main code
from AlphaVPNFunctionsv2 import startVPN
from AlphaVPNFunctionsv2 import deleteVPNs

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

#Clear Text and selections from the Form
def clearTB():
	tb.delete('0', END)
	tb1.delete('0', END)
	a.set('Cleared')

#Disconnect from the VPN Connection [I think this is connect, so I added the connect code here]
def connTB():
    location = tb.get()
    numHops = tb1.get()
    startVPN(location, numHops)
    a.set("Connected")
	
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
x.mainloop()
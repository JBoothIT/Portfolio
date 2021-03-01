# -*- coding: utf-8 -*-
"""
Created on Mon Jul 15 15:26:15 2019

@author: johnb
"""

'''
# old test code
from AlphaVPNClass import MyAlphaVPNClass

location = 'Virginia'
numHops = 2
myVPN = MyAlphaVPNClass(location, numHops)
'''

from AlphaVPNFunctionsv2 import startVPN
from AlphaVPNFunctionsv2 import deleteVPNs


location = 'Virginia'
numHops = 1
print(startVPN(location, numHops))
getIn = input('Ready to delete? ')
print(deleteVPNs(location))


# -*- coding: utf-8 -*-
"""
Created on Mon Jul 22 19:56:37 2019

@author: johnb
"""
import time

class Charge:

    
    def __init__(self):
        # initialize variables
        self.rate = 0.0001
        #self.start_time = ''
        self.delta_time = ''
        self.delt_time = ''
        self.areWeCharging = True
        self.totalTime = ''
        self.totalCharges = 0.0
    
    def set_start_time(self, s_time):
        self.start_time = s_time
        #print('==+++=== Start Time: '+ str(self.start_time))
        return
    
    def get_delta_time(self, t):
        aWC = self.get_areWeCharging()
        if aWC == True:
            self.delta_time = (t - self.start_time)
            self.delt_time = time.strftime("%H:%M:%S", time.gmtime(self.delta_time))
            #print('==+++=== Delt Time: ' + self.delt_time)
            self.totalTime = self.delt_time
            return (self.delt_time)
        elif aWC == False:
            return (self.totalTime)
    
    def get_d_time(self):
        return self.delta_time
    
    def get_charges(self):
        #delta_time = (time.time() - self.start_time)
        #delt_time = time.strftime("%H:%M:%S", time.gmtime(delta_time))
        aWC = self.get_areWeCharging()
        if aWC == True:
            chrgs = self.delta_time * 0.0001
            charges = '${:,.2f}'.format(chrgs)
            #print('==+++=== charges: ' + charges)
            self.totalCharges = charges
            return charges
        elif aWC == False:
            return self.totalCharges
    
    def set_areWeChargingFalse(self):
        self.areWeCharging = False
        return
    
    def get_areWeCharging(self):
        return self.areWeCharging
    
    
    
    
        
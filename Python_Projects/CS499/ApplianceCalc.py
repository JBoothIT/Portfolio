import re
from datetime import datetime

curr_temp = 0

"""
Author: Jeremy Booth
Class: CS499 Senior Capstone

What is this?
This file contains various functions which control how the smart home app keeps track of the power and water consumption of various appliances found throughout the physical house.
In addition, it calculates the temperature of the house based on provided conditions from the "client".

Note: All of these code functions were eventually integrated into the Smart Home App project.
"""

# returns the amount of power used to heat a gallon of water over the course of 4 minutes.
def ret_water_heater():
    return 4 * 4500


# returns the appropriate microwave usage for a standard day.
def ret_microwave(date):
    # variables to distinguish year, month and day.
    match = re.search(r'\d{4}-\d{2}-\d{2}', date)
    p_date = datetime.strptime(match.group(), '%Y-%m-%d').date()
    if p_date.weekday() < 5:
        return 20 * 1100
    else:
        return 30 * 1100


# returns the appropriate stove usage for a standard day.
def ret_stove(date):
    # variables to distinguish year, month and day.
    match = re.search(r'\d{4}-\d{2}-\d{2}', date)
    p_date = datetime.strptime(match.group(), '%Y-%m-%d').date()
    if p_date.weekday() < 5:
        return 15 * 3500
    else:
        return 30 * 3500


# returns the appropriate oven usage for a standard day.
def ret_oven(date):
    # variables to distinguish year, month and day.
    match = re.search(r'\d{4}-\d{2}-\d{2}', date)
    p_date = datetime.strptime(match.group(), '%Y-%m-%d').date()
    if p_date.weekday() < 5:
        return 45 * 4000
    else:
        return 60 * 4000


# returns the appropriate microwave usage for a standard day.
def ret_tv(date, loc):
    # variables to distinguish year, month and day.
    match = re.search(r'\d{4}-\d{2}-\d{2}', date)
    date = datetime.strptime(match.group(), '%Y-%m-%d').date()
    if loc == "lr":  # this tag is to change to whatever the possible value is in the database
        if date.weekday() < 5:
            return 240 * 636
        else:
            return 480 * 636
    elif loc == "br":  # this tag is to change to whatever the possible value is in the database
        if date.weekday() < 5:
            return 120 * 100
        else:
            return 240 * 100


# returns the appropriate microwave usage for a standard day.
def set_curr_temp(new_temp):
    curr_temp = new_temp


def ret_curr_temp():
    return curr_temp


# Ensures the temperature remains within 2 degrees of the set temperature.
# Works on a minute basis.
def main_temp_hvac(set_temp):
    if abs(curr_temp) > 2 + set_temp:
        if curr_temp > 0:
            set_curr_temp((curr_temp - 1))
            ret_curr_temp()
        elif curr_temp < 0:
            set_curr_temp((curr_temp + 1))
    else:
        return curr_temp


# Calculates the internal temperature of the house based on the status of doors and windows.
# status = whether something is open/closed
# opening = indicates the type of opening. This is either a door, window or none.
# ext_temp = is the current external temperature.
# count = the number of minutes which have passed
# use: This method is meant to be run every minute.
def interior_temp_hvac(status, opening, ext_temp, count):
    # if door is open +/- 2 deg F per 5 min door open
    if status == "open" and opening == "door" and count == 5:
        if abs(curr_temp - ext_temp) >= 10:
            if ext_temp > 0:
                set_curr_temp((ret_curr_temp() + 2))
                return ret_curr_temp()
            else:
                set_curr_temp(ret_curr_temp() - 2)
                return ret_curr_temp()

    # if window is open +/- 1 deg F per 5 min window open
    elif status == "open" and opening == "window" and count == 5:
        if abs(curr_temp - ext_temp) >= 10:
            if ext_temp > 0:
                set_curr_temp(ret_curr_temp() + 1)
                return ret_curr_temp()
            else:
                set_curr_temp(ret_curr_temp() - 1)
                return ret_curr_temp()

    # if house is completely closed then +/- 2 deg F per hour.
    if status == "closed" and opening == "none":
        if abs(curr_temp - ext_temp) >= 10 & count == 60:
            if ext_temp > 0:
                set_curr_temp(ret_curr_temp() + 2)
                return ret_curr_temp()
            else:
                set_curr_temp(ret_curr_temp() - 2)
                return ret_curr_temp()


set_curr_temp(5)
print(ret_curr_temp())

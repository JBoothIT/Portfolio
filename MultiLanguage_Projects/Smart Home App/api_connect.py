import requests
from requests.auth import HTTPBasicAuth
import json

headers = {'x-api-key': 'oKrKSjifpzkGI06mrhRcuqr3hmyUY9tK'}

#'https://bulk.meteostat.net/hourly/72228.csv.gz'
response = requests.get("https://bulk.meteostat.net/hourly/72228.csv.gz", headers = headers)
##response = requests.get("https://api.meteostat.net/v2/stations/hourly?station=72228&start=2020-08-22&end=2020-08-31", headers = headers)

"""data = response.json()
dataObject = json.dumps(data, indent = 4)

with open("api.json", "w") as outfile:
    outfile.write(dataObject)"""
print(response.text)

##oKrKSjifpzkGI06mrhRcuqr3hmyUY9tK

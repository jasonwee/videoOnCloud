#!/usr/bin/python

import csv
import pycurl
import json

def insertToElasticSearch(data):
    esData={'year' :           data[0],
            'week':            data[1],
            'state' :          data[2],
            'area':            data[3],
            'location'    :    data[4],
            'totalCase'      : data[5],
            'durationInDays':  data[6],
            'geo' : {
              'lat' :     data[7],
              'lon' :    data[8],
            }
           }

    # 1 to 4 inclusive
    #server = str(random.randrange(1,5))
    server = "localhost"

    c = pycurl.Curl()
    url = 'http://' + server + ':9200/govmy/dengue/?pretty'
    c.setopt(c.URL, url)
    c.setopt(c.POSTFIELDS, json.dumps(esData))
    c.perform()

with open('lokalitihotspot2015.csv', 'rb') as csvfile:
#with open('test.csv', 'rb') as csvfile:
    propreader = csv.reader(csvfile)
    next(csvfile)
    for row in propreader:
        insertToElasticSearch(row)

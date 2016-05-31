#!/usr/bin/python

import csv
import pycurl
import json

def insertToElasticSearch(data):
    esData={'date' :            data[0],
            'state':            data[1],
            'town' :            data[2],
            'propertyCategory': data[3],
            'propertyType'    : data[4],
            'lotSize'         : data[5],
            'transactedAmount': data[6]
           }

    # 1 to 4 inclusive
    #server = str(random.randrange(1,5))
    server = "localhost"

    c = pycurl.Curl()
    url = 'http://' + server + ':9200/iproperty/AHTransactionDataSelangor2016/?pretty'
    c.setopt(c.URL, url)
    c.setopt(c.POSTFIELDS, json.dumps(esData))
    c.perform()

with open('AHTransactionDataSelangor2016.csv', 'rb') as csvfile:
#with open('test.csv', 'rb') as csvfile:
    propreader = csv.reader(csvfile)
    next(csvfile)
    for row in propreader:
        insertToElasticSearch(row)

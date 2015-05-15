#!/bin/bash


curl -XPOST "http://localhost:9200/foods/fruits/1?pretty" -d '
{
    "insert_date"     : "2015-05-15T20:18:50",
    "name"            : "apple-a",
    "grade"           : "A",
    "price"           : 1.98,
    "price_date"      : "2015-05-15",
    "staff_update"    : {"staff" : {"id" : 9739, "name" : "John Smith"} },
    "quantity"        : 20,
    "quantity_max"    : 30,
    "quantity_min"    : 10,
    "tags"            : ["fruits", "foods", "large", "red"],
    "quantity_enough" : true,
    "suppliers"       : [{"vendor_name": "company-A", "vendor_ip": "10.10.10.1", "vendor_coordinate": "41.72,-10.35"}, {"vendor_name": "company-B", "vendor_ip": "10.20.10.1", "vendor_coordinate": "45.72,8.35"}]
}'

curl -XPOST "http://localhost:9200/foods/fruits/2?pretty" -d '
{
    "insert_date"     : "2015-05-14T20:18:50",
    "name"            : "apple-b",
    "grade"           : "B",
    "price"           : 1.38,
    "price_date"      : "2015-05-14",
    "staff_update"    : {"staff" : {"id" : 7795, "name" : "Tide Hunter"} },
    "quantity"        : 18,
    "quantity_max"    : 30,
    "quantity_min"    : 10,
    "tags"            : ["fruits", "foods", "medium", "red"],
    "quantity_enough" : true,
    "suppliers"       : [{"vendor_name": "company-A", "vendor_ip": "10.10.10.1", "vendor_coordinate": "41.72,-10.35"}, {"vendor_name": "company-B", "vendor_ip": "10.20.10.1", "vendor_coordinate": "45.72,8.35"}]
}'

curl -XPOST "http://localhost:9200/foods/fruits/3?pretty" -d '
{
    "insert_date"     : "2015-05-14T20:18:55",
    "name"            : "apple-c",
    "grade"           : "C",
    "price"           : 0.99,
    "price_date"      : "2015-05-14",
    "staff_update"    : {"staff" : {"id" : 7795, "name" : "Tide Hunter"} },
    "quantity"        : 9,
    "quantity_max"    : 40,
    "quantity_min"    : 10,
    "tags"            : ["fruits", "foods", "small", "red"],
    "quantity_enough" : false,
    "suppliers"       : [{"vendor_name": "company-A", "vendor_ip": "10.10.10.1", "vendor_coordinate": "41.72,-10.35"}, {"vendor_name": "company-B", "vendor_ip": "10.20.10.1", "vendor_coordinate": "45.72,8.35"}, {"vendor_name": "company-C", "vendor_ip": "203.83.10.55", "vendor_coordinate": "11.72,18.72"}]
}'


# apricot
#avocado
#banana
#blackberry
#blueberry
#blackcurrent
#cherry
#coconut
#cranberry
#dragonfruit
#durian
#grape
#guava
#jambul
#kiwi
#kumquat
#lemon
#lime
#lychee
#orange
#peach
#pear
#papaya
#passionfruit
#peach
#pineapple
#rambutan

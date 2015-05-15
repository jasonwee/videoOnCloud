#!/bin/bash


curl -XPUT 'http://localhost:9200/foods/?pretty'

sleep 3

#<<COMMENT1
curl -XPUT 'http://localhost:9200/foods/_mapping/fruits' -d '
{
    "fruits" : {
        "properties" : {
            "insert_date"     : { "type" : "date"},
            "name"            : { "type" : "string" },
            "grade"           : { "type" : "string" },
            "price"           : { "type" : "float"},
            "price_date"      : { "type" : "date"},
            "staff_update"    : { "type" : "object", "properties" : { "staff" : { "type": "object", "properties" : { "id" : { "type" : "string"}, "name" : {"type": "string"} } } } },
            "quantity"        : { "type" : "integer"},
            "quantity_max"    : { "type" : "integer"},
            "quantity_min"    : { "type" : "integer"},
            "tags"            : { "type" : "string"},
            "quantity_enough" : { "type" : "boolean"},
            "suppliers"       : { "type" : "nested", "properties" : { "vendor_name" : {"type": "string"}, "vendor_ip": {"type": "ip"}, "vendor_coordinate": {"type": "geo_point"} } }
        }
    }
}'
#COMMENT1

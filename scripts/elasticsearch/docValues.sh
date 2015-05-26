#!/bin/bash

# https://www.elastic.co/guide/en/elasticsearch/guide/master/doc-values.html

# create index
curl -XPUT 'http://localhost:9200/music?pretty' -d ' {
    "settings": {
        "number_of_shards": 5
    }
}'


# check index settings.
curl -XGET 'http://localhost:9200/music/_settings?pretty'

# create mapping on type
# 
# Doc values can be enabled for numeric, date, Boolean, binary, and geo-point
# fields, and for not_analyzed string fields. They do not currently work with
# analyzed string fields.
curl -XPUT 'http://localhost:9200/music/_mapping/song?pretty' -d '
{
    "song": {
        "properties": {
            "tag": {
                "type": "string",
                "index": "not_analyzed",
                "doc_values": true
            }
        }
    }
}'

#check mapping.
curl -XGET 'http://localhost:9200/music/_mapping?pretty'


curl -XPOST 'http://localhost:9200/music/song/_bulk?pretty' -d '
{ "index": { "_id": 1 }}
{ "tag": "piano" }
{ "index": { "_id": 2 }}
{ "tag": "music" }
{ "index": { "_id": 3 }}
{ "tag": "pop" }
{ "index": { "_id": 4 }}
{ "tag": "rock" }
{ "index": { "_id": 5 }}
{ "tag": "jazz" }'

# do a query all
curl -XGET 'http://localhost:9200/music/_search?pretty' -d '
{
   "query" : {
    "match_all" : { }
   },
   "sort": ["tag"]
}'


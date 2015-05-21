#!/bin/bash

#https://www.elastic.co/guide/en/elasticsearch/guide/master/shingles.html


# create index
curl -XPUT 'http://localhost:9200/my_index?pretty' -d ' {
    "settings": {
        "number_of_shards": 5,
        "analysis": {
            "filter": {
                "my_shingle_filter": {
                    "type": "shingle",
                    "min_shingle_size": 2,
                    "max_shingle_size": 2,
                    "output_unigrams":  false
                }
            },
            "analyzer": {
                "my_shingle_analyzer": {
                    "type":"custom",
                    "tokenizer": "standard",
                    "filter": [ "lowercase", "my_shingle_filter" ]
                }
            }
        }
    }
}'


# check index settings.
curl -XGET 'http://localhost:9200/my_index/_settings?pretty'

# quickly check the analyzer
curl -XGET 'http://localhost:9200/my_index/_analyze?analyzer=my_shingle_analyzer&pretty' -d 'Sue ate the alligator'


# create mapping on type
curl -XPUT 'http://localhost:9200/my_index/_mapping/my_type?pretty' -d '
{
    "my_type": {
        "properties": {
            "title": {
                "type": "string",
                "fields": {
                    "shingles": {
                        "type":     "string",
                        "analyzer": "my_shingle_analyzer"
                    }
                }
            }
        }
    }
}'

#check mapping.
curl -XGET 'http://localhost:9200/my_index/_mapping?pretty'

curl -XPOST 'http://localhost:9200/my_index/my_type/_bulk?pretty' -d '
{ "index": { "_id": 1 }}
{ "title": "Sue ate the alligator" }
{ "index": { "_id": 2 }}
{ "title": "The alligator ate Sue" }
{ "index": { "_id": 3 }}
{ "title": "Sue never goes anywhere without her alligator skin purse" }'


# do a query all
curl -XGET 'http://localhost:9200/my_index/_search?pretty' -d '
{
   "query" : {
    "match_all" : { }
   }
}'

# search now
curl -XGET 'http://localhost:9200/my_index/my_type/_search?pretty' -d '
{
   "query": {
        "match": {
           "title": "the hungry alligator ate sue"
        }
   }
}'


curl -XGET 'http://localhost:9200/my_index/my_type/_search?pretty' -d '
{
   "query": {
      "bool": {
         "must": {
            "match": {
               "title": "the hungry alligator ate sue"
            }
         },
         "should": {
            "match": {
               "title.shingles": "the hungry alligator ate sue"
            }
         }
      }
   }
}'

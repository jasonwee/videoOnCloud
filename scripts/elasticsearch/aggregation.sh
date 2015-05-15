#!/bin/sh

<<COMMENT
curl -X POST "http://localhost:9200/foods/_search?pretty=true" -d '
{
    "aggs" : {
        "min_price" : { "min" : { "field" : "price" } }
    }
}
}'

curl -X POST "http://localhost:9200/foods/_search?pretty=true" -d '
{
    "aggs" : {
        "max_price" : { "max" : { "field" : "price" } }
    }
}
}'

curl -X POST "http://localhost:9200/foods/_search?pretty=true" -d '
{
    "aggs" : {
        "sum_all_item_price" : { "sum" : { "field" : "price" } }
    }
}'


curl -X POST "http://localhost:9200/foods/_search?pretty=true" -d '
{
    "aggs" : {
        "avg_price" : { "avg" : { "field" : "price" } }
    }
}'

curl -X POST "http://localhost:9200/foods/_search?pretty=true" -d '
{
    "aggs" : {
        "prices_stats" : { "stats" : { "field" : "price" } }
    }
}'

COMMENT

curl -X POST "http://localhost:9200/foods/_search?pretty=true" -d '
{
    "aggs" : {
        "prices_stats" : { "extended_stats" : { "field" : "price" } }
    }
}'

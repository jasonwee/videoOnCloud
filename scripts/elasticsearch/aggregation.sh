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

curl -X POST "http://localhost:9200/foods/_search?pretty=true" -d '
{
    "aggs" : {
        "prices_stats" : { "extended_stats" : { "field" : "price" } }
    }
}'

curl -X POST "http://localhost:9200/foods/_search?pretty=true" -d '
{
    "aggs" : {
        "prices_count" : { "value_count" : { "field" : "price" } }
    }
}'

curl -X POST "http://localhost:9200/foods/_search?pretty=true" -d '
{
    "aggs" : {
        "quantity_outlier" : {
            "percentiles" : {
                "field" : "quantity",
            }
        }
    }
}'

curl -X POST "http://localhost:9200/foods/_search?pretty=true" -d '
{
    "aggs" : {
        "quantity_outlier" : {
            "percentile_ranks" : {
                "field" : "quantity",
                "values" : [15, 30]
            }
        }
    }
}'

curl -X POST "http://localhost:9200/foods/_search?pretty=true" -d '
{
    "aggs" : {
        "grade_count" : {
            "cardinality" : {
                "field" : "grade"
            }
        }
    }
}'

curl -X POST "http://localhost:9200/foods/_search?pretty=true" -d '
{
    "query" : {
        "match" : { "tags" : "fruits" }
    },
    "aggs" : {
        "viewport" : {
            "geo_bounds" : {
                "field" : "vendor_coordinate"
            }
        }
    }
}'

curl -X POST "http://localhost:9200/foods/_search?pretty=true" -d '
{
    "aggs": {
        "top-tags": {
            "terms": {
                "field": "tags",
                "size": 2
            },
            "aggs": {
                "top_tag_hits": {
                    "top_hits": {
                        "sort": [
                            {
                                "insert_date": {
                                    "order": "desc"
                                }
                            }
                        ],
                        "_source": {
                            "include": [
                                "price"
                            ]
                        },
                        "size" : 1
                    }
                }
            }
        }
    }
}'

curl -X POST "http://localhost:9200/foods/_search?pretty=true" -d '
{
    "query" : {
        "match_all" : {}
    },
    "aggs": {
        "profit": {
            "scripted_metric": {
                "init_script" : "_agg["transactions"] = []",
                "map_script" : "if (doc["type"].value == \"sale\") { _agg.transactions.add(doc["amount"].value) } else { _agg.transactions.add(-1 * doc["amount"].value) }",
                "combine_script" : "profit = 0; for (t in _agg.transactions) { profit += t }; return profit",
                "reduce_script" : "profit = 0; for (a in _aggs) { profit += a }; return profit"
            }
        }
    }
}'

curl -X POST "http://localhost:9200/foods/_search?pretty=true" -d '
{
    "query" : {
        "match" : { "tags" : "fruits" }
    },
    "aggs" : {
        "all_products" : {
            "global" : {}, 
            "aggs" : { 
                "avg_price" : { "avg" : { "field" : "price" } }
            }
        }
    }
}'

curl -X POST "http://localhost:9200/foods/_search?pretty=true" -d '
{
    "aggs" : {
        "in_stock_products" : {
            "filter" : { "range" : { "quantity" : { "gt" : 11 } } },
            "aggs" : {
                "avg_price" : { "avg" : { "field" : "price" } }
            }
        }
    }
}'

curl -X POST "http://localhost:9200/foods/_search?pretty=true" -d '
{
    "aggs" : {
        "fruits_without_a_price" : {
            "missing" : { "field" : "price" }
        }
    }
}'

curl -X POST "http://localhost:9200/foods/_search?pretty=true" -d '
{
    "query" : {
        "match" : { "tags" : "fruits" }
    },
    "aggs" : {
        "suppliers" : {
            "nested" : {
                "path" : "suppliers"
            },
            "aggs" : {
                "near" : { "min" : { "field" : "suppliers.vendor_ip" } }
            }
        }
    }
}'

curl -X POST "http://localhost:9200/foods/_search?pretty=true" -d '
{
  "query": {
    "match": {
      "tags": "fruits"
    }
  },
  "aggs": {
    "suppliers": {
      "nested": {
        "path": "suppliers"
      },
      "aggs": {
        "top_suppliers": {
          "terms": {
            "field": "suppliers.vendor_name"
          },
          "aggs": {
            "suppliers_to_issue": {
              "reverse_nested": {}, 
              "aggs": {
                "top_tags_per_comment": {
                  "terms": {
                    "field": "tags"
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}'

curl -X POST "http://localhost:9200/foods/_search?pretty=true" -d '
{
    "aggs" : {
        "tags" : {
            "terms" : { "field" : "tags" }
        }
    }
}'

curl -X POST "http://localhost:9200/foods/_search?pretty=true" -d '
{
    "query" : {
        "terms" : {"tags" : [ "fruits" ]}
    },
    "aggregations" : {
        "significantQuantityTypes" : {
            "significant_terms" : { "field" : "quantity" }
        }
    }
}'

curl -X POST "http://localhost:9200/foods/_search?pretty=true" -d '
{
    "aggs" : {
        "price_ranges" : {
            "range" : {
                "field" : "price",
                "ranges" : [
                    { "to" : 4 },
                    { "from" : 1, "to" : 3 },
                    { "from" : 4 }
                ]
            }
        }
    }
}
'

curl -X POST "http://localhost:9200/foods/_search?pretty=true" -d '
{
    "aggs": {
        "range": {
            "date_range": {
                "field": "price_date",
                "ranges": [
                    { "to": "now-10M/M" }, 
                    { "from": "now-10M/M" } 
                ]
            }
        }
    }
}'

curl -X POST "http://localhost:9200/foods/_search?pretty=true" -d '
{
    "aggs" : {
        "suppliers_ip_ranges" : {
            "ip_range" : {
                "field" : "suppliers.vendor_ip",
                "ranges" : [
                    { "to" : "10.0.0.1" },
                    { "from" : "10.30.0.1" }
                ]
            }
        }
    }
}'

curl -X POST "http://localhost:9200/foods/_search?pretty=true" -d '
{
    "aggs" : {
        "prices" : {
            "histogram" : {
                "field" : "price",
                "interval" : 1 
            }
        }
    }
}'

curl -X POST "http://localhost:9200/foods/_search?pretty=true" -d '
{
    "aggs" : {
        "prices_over_time" : {
            "date_histogram" : {
                "field" : "price_date",
                "interval" : "day"
            }
        }
    }
}'

curl -X POST "http://localhost:9200/foods/_search?pretty=true" -d '
{
    "aggs" : {
        "rings_around_x" : {
            "geo_distance" : {
                "field" : "suppliers.vendor_coordinate",
                "origin" : "41.72,-10.35",
                "ranges" : [
                    { "to" : 100 },
                    { "from" : 100, "to" : 300 },
                    { "from" : 300 }
                ]
            }
        }
    }
}'

COMMENT
curl -X POST "http://localhost:9200/foods/_search?pretty=true" -d '
{
    "aggregations" : {
        "myLarge-GrainGeoHashGrid" : {
            "geohash_grid" : {
                "field" : "suppliers.vendor_coordinate",
                "precision" : 1
            }
        }
    }
}'

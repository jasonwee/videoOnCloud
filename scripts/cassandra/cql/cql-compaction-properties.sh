#!/bin/bash 

# tried compaction properties in cassandra 2.3 using this link from datastax
# http://docs.datastax.com/en/cql/3.1/cql/cql_reference/compactSubprop.html


# size tier compaction strategies

cqlsh:jw_schema1> CREATE TABLE test_table_stcs ( hex ascii, insert_time timestamp, PRIMARY KEY (hex, insert_time)) with COMPACTION = {'class': 'SizeTieredCompactionStrategy', 'enabled': 'true', 'min_threshold': '8', 'max_threshold': '64'};
OperationTimedOut: errors={}, last_host=2001:e68:5424:0:0:0:0:0
cqlsh:jw_schema1> desc tables;

flight_statistics  all_data_types  users  test_table_stcs

cqlsh:jw_schema1> desc table test_table_stcs;

CREATE TABLE jw_schema1.test_table_stcs (
    hex ascii,
    insert_time timestamp,
    PRIMARY KEY (hex, insert_time)
) WITH CLUSTERING ORDER BY (insert_time ASC)
    AND bloom_filter_fp_chance = 0.01
    AND caching = '{"keys":"ALL", "rows_per_partition":"NONE"}'
    AND comment = ''
    AND compaction = {'min_threshold': '8', 'enabled': 'true', 'class': 'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy', 'max_threshold': '64'}
    AND compression = {'sstable_compression': 'org.apache.cassandra.io.compress.LZ4Compressor'}
    AND dclocal_read_repair_chance = 0.1
    AND default_time_to_live = 0
    AND gc_grace_seconds = 864000
    AND max_index_interval = 2048
    AND memtable_flush_period_in_ms = 0
    AND min_index_interval = 128
    AND read_repair_chance = 0.0
    AND speculative_retry = '99.0PERCENTILE';


# date tier compaction strategies

cqlsh:jw_schema1> CREATE TABLE test_table_dtcs ( hex ascii, insert_time timestamp, PRIMARY KEY (hex, insert_time)) with COMPACTION = {'class': 'DateTieredCompactionStrategy', 'enabled': 'true', 'min_threshold': '8', 'max_threshold': '64', 'max_sstable_age_days': '731'};
OperationTimedOut: errors={}, last_host=2001:e68:5424:0:0:0:0:0
cqlsh:jw_schema1> desc table test_table_dtcs;

CREATE TABLE jw_schema1.test_table_dtcs (
    hex ascii,
    insert_time timestamp,
    PRIMARY KEY (hex, insert_time)
) WITH CLUSTERING ORDER BY (insert_time ASC)
    AND bloom_filter_fp_chance = 0.01
    AND caching = '{"keys":"ALL", "rows_per_partition":"NONE"}'
    AND comment = ''
    AND compaction = {'min_threshold': '8', 'class': 'org.apache.cassandra.db.compaction.DateTieredCompactionStrategy', 'enabled': 'true', 'max_sstable_age_days': '731', 'max_threshold': '64'}
    AND compression = {'sstable_compression': 'org.apache.cassandra.io.compress.LZ4Compressor'}
    AND dclocal_read_repair_chance = 0.1
    AND default_time_to_live = 0
    AND gc_grace_seconds = 864000
    AND max_index_interval = 2048
    AND memtable_flush_period_in_ms = 0
    AND min_index_interval = 128
    AND read_repair_chance = 0.0
    AND speculative_retry = '99.0PERCENTILE';


# level tier compaction strategies

cqlsh:jw_schema1> CREATE TABLE test_table_lcs ( hex ascii, insert_time timestamp, PRIMARY KEY (hex, insert_time)) with COMPACTION = {'class': 'LeveledCompactionStrategy', 'enabled': 'true', 'sstable_size_in_mb': '170'};
OperationTimedOut: errors={}, last_host=2001:e68:5424:0:0:0:0:0
cqlsh:jw_schema1> desc table test_table_lcs;

CREATE TABLE jw_schema1.test_table_lcs (
    hex ascii,
    insert_time timestamp,
    PRIMARY KEY (hex, insert_time)
) WITH CLUSTERING ORDER BY (insert_time ASC)
    AND bloom_filter_fp_chance = 0.1
    AND caching = '{"keys":"ALL", "rows_per_partition":"NONE"}'
    AND comment = ''
    AND compaction = {'sstable_size_in_mb': '170', 'enabled': 'true', 'class': 'org.apache.cassandra.db.compaction.LeveledCompactionStrategy'}
    AND compression = {'sstable_compression': 'org.apache.cassandra.io.compress.LZ4Compressor'}
    AND dclocal_read_repair_chance = 0.1
    AND default_time_to_live = 0
    AND gc_grace_seconds = 864000
    AND max_index_interval = 2048
    AND memtable_flush_period_in_ms = 0
    AND min_index_interval = 128
    AND read_repair_chance = 0.0
    AND speculative_retry = '99.0PERCENTILE';

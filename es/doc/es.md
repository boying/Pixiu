## 节点属性

### master-eligible node

node.master: true
一个集群只有一个主节点，但可以多个候选主节点，主节点从master-eligible节点中选出。

### date node

node.date: true

### ingest node

node.ingest: true
预处理节点，可在该节点设置pipeline，在index文档之前对文档进行预处理

### tribe node

deprecated

## 节点分类

### 主节点

负责集群操作，例如新建或删除index，跟踪集群节点，分配分片到节点上。

discovery.zen.minimum_master_nodes 设置至少有这么多个候选主节点后，才能选主。

### 候选主节点

node.master: true
node.date: false
node.ingest: false

### 数据节点

node.master: false
node.date: true
node.ingest: false

### 协作（coordinate）节点

查询分两个阶段：分散（scatter）和收集（gather）两个阶段。每一个节点都是协作节点。
由于gather是有开销的，可以从集群中专门选几个节点作为协作节点，他们不存储数据，专门用来做分散和收集功能。

node.master: false
node.date: false
node.ingest: false

## 常用命令

### 查看集群状态

curl -XGET 'localhost:9200/_cat/shards?pretty'

### 查看节点状态

curl -XGET 'localhost:9200/_cat/nodes?v&pretty'

curl -XGET 'localhost:9200/_cat/nodes?v&h=name,longValue,ip,port,v,m&pretty'

### 查看分片状态

curl -XGET 'localhost:9200/_cat/shards?pretty'

## 文档

es官方文档写得非常好

### 节点说明

https://www.elastic.co/guide/en/elasticsearch/reference/current/modules-node.html

### 节点发现 选主

https://www.elastic.co/guide/en/elasticsearch/reference/current/modules-discovery-zen.html

# Apache Kafka Notes
## Topics, Partitions and Offsets:
* A Particular stream of Data. Similar to a RDBMS Table (without constraints).
* Topics are split into partitions. Partitions start with 0 to n.
* Each message within a partition gets an incremental id, called offset. 
* An offset is referred by its topic name followed by partition name and then its offset ID. An offset by itself does not mean anything.
* **Message order is only guaranteed within a partition (not across partitions).**
* Data is kept in partitions only for a limited time (default 1 week).
* Data written to a partition is immutable. Once written, it cannot be changed.
* Data is randomly assigned to a partition unless a key is provided.

## Brokers:
* A kafka cluster is composed of multiple brokers.
* A kafka broker is a server holding kafka data. Each broker is identified with its ID.
* Each broker contains certain topic partitions. 
* There is a special broker called _a bootstrap broker_ which gets a client connected to the entire cluster.
* A good number for the number of brokers is 3 to get started with any kafka topic.
* Whenever a topic is created, kafka automatically distributes its partitions over multiple brokers.

## Topic Replication:
* Topics should have a replication factor > 1 (Usually 3). This way, if a broker is down, another broker can serve data.
* **A replication factor of n means that each partition in the topic is replicated n times**.
* **Concept of leader of a partition: At any point in time, only ONE broker can be a leader of a partition**.
* **Only that leader can receive and serve data for a partition**. Other brokers will synchronize the data.
* **Therefore, each partition has only one leader and multiple ISRs (in-sync replica)**.
* If a leader dies, then a replica becomes a leader _(gets promoted to a leader)_.

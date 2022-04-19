# Apache Kafka Notes
## Topics, Partitions and Offsets:
* A Particular stream of Data. Similar to a RDBMS Table (without constraints).
* Topics are split into partitions. Partitions start with 0 to n.
* Each message within a partition gets an incremental id, called offset. 
* An offset is referred by its topic name followed by partition name and then its offset ID. An offset by itself does not mean anything.
* **Message order is only guaranteed within a partition (not across partitions). There is no order guarantee of messages across partitions**.
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

## Producers:
* Producers write data to topics (which is made of partitions). Producers automatically know to which broker and partition to write to.
* In case of broker failures, producers will automatically recover.
* Producers can choose to receive an acknowledgement of data writes.

## Message Keys:
* Producers can choose to send a key with the message (string, number).
* If key=null, data is sent in a round-robin fashion (first data -> broker 101, second data -> broker 102).
* **If a key is sent, all messages for that key will always end up in the same partition** This is done by hashing the key.
* In other words, a key is basically sent if you need message ordering for a specific field (eg. truck_id).

## Consumers:
* Consumers read data from a topic (identified by its name).
* Consumers know which broker to read from.
* In case of broker failures, consumers know how to recover.
* Data is read in order **within each partition**.
* Consumers read data in _Consumer Groups_.
* Each consumer within a group reads from exclusive partitions.
* If you have more consumers than partitions, some consumers will be inactive.

## Consumer offsets:
* Kafka stores the offsets at which a consumer group has been reading.
* The offsets committed live in a kafka topic named **__consumer_offets**.
* When a consumer in a group has processed data received from kafka, it commits the offets.
* If a consumer dies, it will be able to read back from where it left off.
* Consumers choose when to commit offsets. This gives with 3 delivery semantics.
* At most once: offsets are committed as soon as the message is received. If the processing goes wrong, the message will be lost (it won't be read again).
* At least once: offsets are committed after a message is processed successfully. If the processing goes wrong, the message will be read again. Since, this can result in duplicate messages, processing system needs to be idempotent.
* Exactly once: for Kafka => External System Workflows, use an idempotent consumer.
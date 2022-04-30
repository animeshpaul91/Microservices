# Apache Kafka Notes

## Topics, Partitions and Offsets:

* A Particular stream of Data. Similar to a RDBMS Table (without constraints).
* Topics are split into partitions. Partitions start with 0 to n.
* Each message within a partition gets an incremental id, called offset.
* An offset is referred by its topic name followed by partition name and then its offset ID. An offset by itself does
  not mean anything.
* **Message order is only guaranteed within a partition (not across partitions). There is no order guarantee of messages
  across partitions**.
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
* You cannot create a replication factor greater than the number of available brokers.

## Producers:

* Producers write data to topics (which is made of partitions). Producers automatically know to which broker and
  partition to write to.
* In case of broker failures, producers will automatically recover.
* Producers can choose to receive an acknowledgement of data writes.

## Message Keys:

* Producers can choose to send a key with the message (string, number).
* If key=null, data is sent in a round-robin fashion (first data -> broker 101, second data -> broker 102).
* **If a key is sent, all messages for that key will always end up in the same partition** This is done by hashing the
  key.
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
* When a consumer in a group has processed data received from kafka, it commits the offsets.
* If a consumer dies, it will be able to read back from where it left off.
* Consumers choose when to commit offsets. This gives with 3 delivery semantics.
* At most once: offsets are committed as soon as the message is received. If the processing goes wrong, the message will
  be lost (it won't be read again).
* At least once: offsets are committed after a message is processed successfully. If the processing goes wrong, the
  message will be read again. Since, this can result in duplicate messages, processing system needs to be idempotent.
* Exactly once: for Kafka => External System Workflows, use an idempotent consumer.

## Kafka Broker Discovery

* Every kafka broker is also called a **bootstrap server**.
* This means you need to connect to only one broker, and you will be connected to the entire cluster.
* Each broker knows about all brokers, topics and partitions (metadata).
* A client can connect to any broker and can fetch all this metadata.

## Zookeeper

* Zookeeper manages brokers (keeps a list of them).
* Zookeeper helps in performing leader election for partitions.
* Zookeeper sends notifications to kafka in case of changes (new topic, broker dies, broker comes up, delete topics).
* Apache kakfa cannot work without Zookeeper.
* Zookeeper by design operates with an odd number of servers (3, 5, 7).
* Zookeeper has a leader that handles writes. The rest of the servers are followers which handle reads. The leader can
  also handle reads.
* Zookeeper does **NOT** store consumer offsets with kafka > v0.10.

## Kafka Guarantees

* Messages are appended to a topic-partition in the order they are sent.
* Consumers read messages in the order stored in a topic-partition.
* With a replication factor of N, producers and consumers can tolerate upto N-1 brokers being down.
* This is why a replication factor of 3 is a good idea. It allows for 1 broker to be taken down for maintenance. It
  tolerates the unexpected shutdown of 1 broker.
* As long as the number of partitions remains constant for a topic (no new partitions), the same key will always go to
  the same partition.

# Kafka CLI Documentation (v3.1.0)

## Start Zookeeper

`zookeeper-server-start.sh config/zookeeper.properties`

## Start Kafka Server

`kafka-topics.sh --bootstrap-^Crver localhost:9092 --list`

## Create Topic

`kafka-topics.sh --bootstrap-server localhost:9092 --topic firstTopic --partitions 3 --replication-factor 1 --create`

## List Topics

`kafka-topics.sh --bootstrap-server localhost:9092 --list`

## List topics with partitions and replication information

`kafka-topics.sh --bootstrap-server localhost:9092 --topic firstTopic --describe`

## Describe Topic

`kafka-topics.sh --bootstrap-server localhost:9092 --topic firstTopic --describe`

## Delete a topic

`kafka-topics.sh --bootstrap-server localhost:9092 --topic firstTopic --delete`

## Produce Messages to a topic

`kafka-console-producer.sh --bootstrap-server localhost:9092 --broker-list localhost:9092 --topic firstTopic`

## Consume Messages from a topic

#### This resets the consumer offset

`kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic firstTopic`
`kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic firstTopic --from-beginning`

## Consume Messages in Consumer Group

### Different consumers read data from different partitions of the topic. Essentially they lead-balance the consumption

`kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic firstTopic --group my-first-application`

#### From beginning with a new consumer group will read all new messages

`kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic firstTopic --group my-fifth-application --from-beginning`

## List Consumer Groups

`kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list`

## Describe Consumer Group

`kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group my-first-application`

## Reset Offsets

`kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group my-first-application --reset-offsets --to-earliest --execute --topic firstTopic`

### This should reset offsets to the left by 2 for all partitions. Reading again will generate 6 messages
`kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group my-first-application --reset-offsets --shift-by -2 --execute --topic firstTopic`

## Idemptence Properties
`enable.idempotence = true` (Producer Level) and `min.insync.replicas = 2` (Broker/Topic Level. This is the minimum # of insync replicas that need to acknowledge) implies:
* `acks = all`
* `retries = MAX_INT`
* `max.in.flight.requests.per.connection = 5`
* While keeping ordering guarantees and improving performance.


## Batching in Production of Data
* Kafka can send upto 5 requests in flight.
* Kafka batches messages and sends all these batched messages in one request. This is called smart batching.
* Batches have high compression ratio.

### There are two settings for this
* Linger.ms: # of milliseconds a producer is willing to wait before sending out a batch (default 0).
* batch.size: Maximum # of bytes that will be included in a batch (default 16KB).
* A batch is allocated per partition, so setting it to a number too high might cause to run out of memory in the partition.

### Replay Data by resetting all consumer offsets of a consumer group
`kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group kafka-demo-elasticsearch --reset-offsets --execute --to-earliest --topic twitter_tweets`
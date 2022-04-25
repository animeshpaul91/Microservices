package com.animesh.kafka.consumers;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ConsumerDemoAssignSeek {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerDemoAssignSeek.class); // creates a logger for this class

    private static void consumeSimpleMessage() {
        final String bootstrapServers = "127.0.0.1:9092";

        // create consumer properties
        Properties properties = new Properties();

        // key and value serializer lets kafka know what type of value we are sending over kafka
        // Producer deserializes string to bytes
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // earliest means consumer will read from the very beginning of the topic
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // create consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        // assign and seek are mostly used to replay data or fetch a specific message

        // assign
        // This assigns the consumer to a particular topic partition.
        final String topicName = "firstTopic";
        final int partitionId = 0;
        TopicPartition topicPartition = new TopicPartition(topicName, partitionId);
        consumer.assign(Collections.singletonList(topicPartition));
        long offsetToReadFrom = 60L;

        // seek
        consumer.seek(topicPartition, offsetToReadFrom);
        int numberOfMessagesToRead = 5, numberOfMessagesReadSoFar = 0;

        // poll for new data
        while (numberOfMessagesReadSoFar < numberOfMessagesToRead) { // busy waiting
            final ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                numberOfMessagesReadSoFar++;
                logger.info("Key: " + record.key() + ", Value: " + record.value());
                logger.info("Offset: " + record.offset());
                logger.info("Partition: " + record.partition());
            }
        }

        logger.info("Exiting the Application");
    }

    public static void main(String[] args) {
        consumeSimpleMessage();
    }
}

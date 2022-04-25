package com.animesh.kafka.helloworld;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class ProducerDemoWithKeys {
    private static final Logger logger = LoggerFactory.getLogger(ProducerDemoWithCallBack.class); // creates a logger for this class

    private static void produceSimpleMessageWithKeys() throws ExecutionException, InterruptedException {
        final String bootstrapServers = "127.0.0.1:9092";

        // create Producer Properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        // key and value serializer lets kafka know what type of value we are sending over kafka
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // create the producer. Key is the topic name and value is the actual message that needs to be sent
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        final String topicName = "firstTopic";

        // create producer record
        for (int i = 0; i < 10; i++) {
            // create producer record
            final String message = "Hello World " + i;
            final String key = "id_" + i;

            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topicName, key, message);

            logger.info("Key: " + key);

            // send data - asynchronous. This is a Non-Blocking call.
            producer.send(producerRecord, (recordMetadata, exception) -> {
                // executes everytime when a record is successfully sent to kafka topic or if an exception occurs
                if (exception == null) { // happy path
                    logger.info("Received new meta-data:");
                    logger.info("Topic: " + recordMetadata.topic());
                    logger.info("Partition: " + recordMetadata.partition());
                    logger.info("Offset: " + recordMetadata.offset());
                    logger.info("Timestamp: " + recordMetadata.timestamp());
                } else {
                    logger.error("Error while Producing occurred: ", exception);
                }
            }).get(); // block send to make it synchronous to see if the same key goes to the same partition
        }

        // flush data blocking call. Main thread waits for all threads to finish
        producer.flush();

        // flush and close
        producer.close();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        produceSimpleMessageWithKeys();
    }
}

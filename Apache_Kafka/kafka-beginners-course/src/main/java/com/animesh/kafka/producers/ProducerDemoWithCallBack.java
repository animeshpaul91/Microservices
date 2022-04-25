package com.animesh.kafka.producers;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemoWithCallBack {
    private static final Logger logger = LoggerFactory.getLogger(ProducerDemoWithCallBack.class); // creates a logger for this class

    private static void produceSimpleMessage() {
        final String bootstrapServers = "127.0.0.1:9092";

        // create Producer Properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        // key and value serializer lets kafka know what type of value we are sending over kafka
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // create the producer. Key is the topic name and value is the actual message that needs to be sent
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        for (int i = 1; i < 11; i++) {
            // create producer record
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>("firstTopic", "Hello World " + i);

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
            });
        }

        // flush record from buffer and waits for the request to complete. This is a blocking call
        producer.flush();

        // flush and close
        producer.close();
    }

    public static void main(String[] args) {
        produceSimpleMessage();
    }
}

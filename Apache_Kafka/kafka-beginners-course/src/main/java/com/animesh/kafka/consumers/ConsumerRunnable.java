package com.animesh.kafka.consumers;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class ConsumerRunnable implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerRunnable.class); // creates a logger for this class

    private final CountDownLatch latch;
    private final KafkaConsumer<String, String> consumer;

    public ConsumerRunnable(String bootstrapServers, String groupId, String topicName, CountDownLatch latch) {
        this.latch = latch;

        // create consumer properties
        Properties properties = new Properties();

        // key and value serializer lets kafka know what type of value we are sending over kafka
        // Producer deserializes string to bytes
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        // earliest means consumer will read from the very beginning of the topic
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        this.consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singleton(topicName));
    }

    @Override
    public void run() {
        try {
            while (true) {
                final ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    logger.info("Key: " + record.key() + ", Value: " + record.value());
                    logger.info("Offset: " + record.offset());
                    logger.info("Partition: " + record.partition());
                }
            }
        } catch (WakeupException wakeupException) {
            logger.info("Received Shutdown Signal!"); // Wakeup Exception is expected.
        } finally {
            consumer.close();
            latch.countDown(); // tell our main code to exit as we are done with the consumer.
        }
    }

    public void shutdown() {
        consumer.wakeup(); // wakeup method is a special method to interrupt consumer.poll(). This throws Wakeup Exception
    }
}

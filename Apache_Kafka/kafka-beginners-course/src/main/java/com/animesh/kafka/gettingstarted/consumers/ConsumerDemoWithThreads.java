package com.animesh.kafka.gettingstarted.consumers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class ConsumerDemoWithThreads {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerDemoWithThreads.class); // creates a logger for this class

    private static void consumeSimpleMessage() {
        final String bootstrapServers = "127.0.0.1:9092";
        final String groupId = "my-third-application";
        final String topicName = "firstTopic";

        // latch for dealing with multiple threads
        final CountDownLatch latch = new CountDownLatch(1);

        // create the consumer runnable
        final ConsumerRunnable myConsumerRunnable = new ConsumerRunnable(bootstrapServers, groupId, topicName, latch);
        logger.info("Creating Consumer Thread");
        final Thread myThread = new Thread(myConsumerRunnable);
        myThread.start(); // start the task

        // add a shutdown hook
        final Thread shutdownHookThread = new Thread(() -> {
            logger.info("Caught shutdown Hook");
            myConsumerRunnable.shutdown();
            try {
                latch.await();
            } catch (InterruptedException e) {
                logger.error("Application got Interrupted", e);
            } finally {
                logger.info("Application has exited");
            }
        });

        Runtime.getRuntime().addShutdownHook(shutdownHookThread);
    }

    public static void main(String[] args) {
        consumeSimpleMessage();
    }
}

package com.animesh.kafka.twitter.producers;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class TwitterProducer {
    private static final Logger logger = LoggerFactory.getLogger(TwitterProducer.class);

    private static final String consumerKey = "CONSUMER_KEY";
    private static final String consumerSecret = "CONSUMER_SECRET";
    private static final String token = "TOKEN";
    private static final String tokenSecret = "TOKEN_SECRET";

    private static final String CONSUMER_KEY = Optional.ofNullable(System.getenv(consumerKey)).orElseThrow(() -> new RuntimeException(consumerKey + " not set in the environment"));
    private static final String CONSUMER_SECRET = Optional.ofNullable(System.getenv(consumerSecret)).orElseThrow(() -> new RuntimeException(consumerSecret + " not set in the environment"));
    private static final String TOKEN = Optional.ofNullable(System.getenv(token)).orElseThrow(() -> new RuntimeException(token + " not set in the environment"));
    private static final String TOKEN_SECRET = Optional.ofNullable(System.getenv(tokenSecret)).orElseThrow(() -> new RuntimeException(tokenSecret + " not set in the environment"));

    private static Client createTwitterClient(BlockingQueue<String> msgQueue) {
        /* Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth) */
        Hosts hoseBirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hoseBirdEndpoint = new StatusesFilterEndpoint();

        // Optional: set up some followings and track terms
        List<String> terms = Lists.newArrayList("bitcoin"); //search string
        hoseBirdEndpoint.trackTerms(terms);

        // These secrets should be read from a config file
        Authentication hoseBirdAuth = new OAuth1(CONSUMER_KEY, CONSUMER_SECRET, TOKEN, TOKEN_SECRET);

        // create client
        ClientBuilder builder = new ClientBuilder()
                .name("HoseBird-Client-01")                              // optional: mainly for the logs
                .hosts(hoseBirdHosts)
                .authentication(hoseBirdAuth)
                .endpoint(hoseBirdEndpoint)
                .processor(new StringDelimitedProcessor(msgQueue));

        return builder.build();
    }

    private static void run() {
        logger.info("Setup");

        // create twitter client
        /* Set up your blocking queues: Be sure to size these properly based on expected TPS of your stream */
        BlockingQueue<String> msgQueue = new LinkedBlockingQueue<>(100000);
        Client client = createTwitterClient(msgQueue);

        // Attempts to establish a connection
        client.connect();

        // create Kafka Producer


        // loop to send tweets to kafka
        // on a different thread, or multiple different threads....
        while (!client.isDone()) {
            String msg = null;
            try {
                msg = msgQueue.poll(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
                client.stop();
            }
            if (msg != null) {
                logger.info(msg);
            }
        }
        logger.info("End of Application");
    }

    public static void main(String[] args) {
        run();
    }
}

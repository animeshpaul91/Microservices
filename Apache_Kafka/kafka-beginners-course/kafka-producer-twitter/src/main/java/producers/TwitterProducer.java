package producers;

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
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
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

    private static final List<String> terms = Lists.newArrayList("kafka", "bitcoin", "trump"); //search string

    private static Client createTwitterClient(BlockingQueue<String> msgQueue) {
        /* Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth) */
        Hosts hoseBirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hoseBirdEndpoint = new StatusesFilterEndpoint();

        // Optional: set up some followings and track terms
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
        final Client client = createTwitterClient(msgQueue);

        // Attempts to establish a connection
        client.connect();

        // create Kafka Producer
        final KafkaProducer<String, String> kafkaProducer = createKafkaProducer();

        // add a shutdown hook
        Runnable runnable = () -> {
            logger.info("Stopping Application");
            logger.info("Shutting down Twitter Client");
            client.stop();
            logger.info("Closing Producer");
            kafkaProducer.close();
        };

        Thread shutdownHookThread = new Thread(runnable);
        Runtime.getRuntime().addShutdownHook(shutdownHookThread);

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
                final String topicName = "twitter_tweets";
                ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topicName, null, msg);
                kafkaProducer.send(producerRecord, (recordMetadata, exception) -> {
                    if (exception != null) {
                        logger.error("Error occurred in sending data to topic: " + topicName, exception);
                    }
                });
            }
        }
        logger.info("End of Application");
    }

    private static KafkaProducer<String, String> createKafkaProducer() {
        final String bootstrapServers = "127.0.0.1:9092";

        // create Producer Properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        // key and value serializer lets kafka know what type of value we are sending over kafka
        // Producer serializes string to bytes
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // create a SAFE producer
        properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        properties.setProperty(ProducerConfig.RETRIES_CONFIG, Integer.toString(Integer.MAX_VALUE));
        properties.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5");

        // high throughput producer (at the expense of a bit of latency and CPU usage)
        properties.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "20");
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32 * 1024));

        // create the producer. Key is the topic name and value is the actual message that needs to be sent
        return new KafkaProducer<>(properties);
    }

    public static void main(String[] args) {
        run();
    }
}

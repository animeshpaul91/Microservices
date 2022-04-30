package consumers;


import com.google.gson.JsonParser;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Optional;
import java.util.Properties;

public class ElasticSearchConsumer {
    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchConsumer.class);
    private static final JsonParser jsonParser = new JsonParser();

    private static RestHighLevelClient createClient() {
        final String hostnameEnvKey = "HOSTNAME";
        final String usernameEnvKey = "USERNAME";
        final String passwordEnvKey = "PASSWORD";

        final String hostname = Optional.ofNullable(System.getenv(hostnameEnvKey)).orElseThrow(() -> new RuntimeException(hostnameEnvKey + " not set in the environment"));
        final String username = Optional.ofNullable(System.getenv(usernameEnvKey)).orElseThrow(() -> new RuntimeException(usernameEnvKey + " not set in the environment"));
        final String password = Optional.ofNullable(System.getenv(passwordEnvKey)).orElseThrow(() -> new RuntimeException(passwordEnvKey + " not set in the environment"));

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        final UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
        credentialsProvider.setCredentials(AuthScope.ANY, credentials);

        HttpHost httpHost = new HttpHost(hostname, 443, "https");
        RestClientBuilder restClientBuilder = RestClient.builder(httpHost)
                .setHttpClientConfigCallback((httpClientBuilder) -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));

        return new RestHighLevelClient(restClientBuilder);
    }

    private static KafkaConsumer<String, String> createConsumer(final String topicName) {
        final String bootstrapServers = "127.0.0.1:9092";
        final String groupId = "kafka-demo-elasticsearch";

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

        // disable auto commit of offsets
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "10");

        // create consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList(topicName));
        return consumer;
    }

    private static void runner() throws IOException, InterruptedException {
        RestHighLevelClient client = createClient();
        KafkaConsumer<String, String> consumer = createConsumer("twitter_tweets");

        while (true) {
            final ConsumerRecords<String, String> batchOfRecords = consumer.poll(Duration.ofMillis(100)); // this is a batch

            logger.info("Received " + batchOfRecords.count() + " count");
            for (ConsumerRecord<String, String> record : batchOfRecords) {
                // Create Kafka Generic ID
                // final String id = record.topic() + "_" + record.partition() + "_" + record.offset();

                // Twitter Feed Specific ID
                final String tweetId = extractIdFromTweet(record.value());
                // insert data into elastic search
                IndexRequest indexRequest = new IndexRequest("twitter", "tweets", tweetId)
                        .source(record.value(), XContentType.JSON); // tweetId will make ElasticSearch to avoid duplicates and update same record
                // This preserves Idempotency.

                IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
                String indexResponseId = indexResponse.getId();
                logger.info("IndexResponseId: " + indexResponseId);
                Thread.sleep(10);
            }

            // At this point the batch of messages has been already processed
            logger.info("Committing offsets");
            consumer.commitSync();
            logger.info("Offsets Committed");
            Thread.sleep(1000);
        }
    }

    private static String extractIdFromTweet(String tweetJson) {
        // using gson
        return jsonParser.parse(tweetJson)
                .getAsJsonObject()
                .get("id_str")
                .getAsString();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        runner();
    }
}

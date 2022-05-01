package kafkastreams;

import com.google.gson.JsonParser;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class StreamsFilterTweets {
    private static final Logger logger = LoggerFactory.getLogger(StreamsFilterTweets.class);
    private static final JsonParser jsonParser = new JsonParser();

    private static int extractNumberOfFollowersFromTweet(String tweetJson) {
        // using gson
        try {
            return jsonParser.parse(tweetJson)
                    .getAsJsonObject()
                    .get("user")
                    .getAsJsonObject()
                    .get("followers_count")
                    .getAsInt();
        } catch (NullPointerException e) {
            return 0;
        }
    }

    private static void filterTweets() {
        // create properties
        Properties properties = new Properties();
        properties.setProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, "demo-kafka-streams"); // similar to consumer group for kafka streams
        properties.setProperty(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class.getName());
        properties.setProperty(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.StringSerde.class.getName());

        // create a topology
        StreamsBuilder streamsBuilder = new StreamsBuilder();

        // input topic
        final KStream<String, String> inputStream = streamsBuilder.stream("twitter_tweets");
        final KStream<String, String> filteredStream = inputStream.filter((topicName, jsonTweet) -> extractNumberOfFollowersFromTweet(jsonTweet) > 10000);
        filteredStream.to("filtered_tweets");

        // build topology
        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), properties);

        // start our streams application
        kafkaStreams.start();
        logger.info("Starting to Filter Tweets on Followers Count");
    }

    public static void main(String[] args) {
        filterTweets();
    }
}

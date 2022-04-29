package gettingstarted.producers;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerDemo {
    private static void produceSimpleMessage() {
        final String bootstrapServers = "127.0.0.1:9092";

        // create Producer Properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        // key and value serializer lets kafka know what type of value we are sending over kafka
        // Producer serializes string to bytes
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // create the producer. Key is the topic name and value is the actual message that needs to be sent
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        // create producer record
        final String topicName = "firstTopic";
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topicName, "Hello World");

        // send data - asynchronous
        producer.send(producerRecord);

        // flush data
        producer.flush();

        // flush and close
        producer.close();
    }

    public static void main(String[] args) {
        produceSimpleMessage();
    }
}

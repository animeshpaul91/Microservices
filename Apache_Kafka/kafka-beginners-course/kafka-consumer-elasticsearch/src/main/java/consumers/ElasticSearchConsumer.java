package consumers;


import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
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

public class ElasticSearchConsumer {
    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchConsumer.class);

    private static RestHighLevelClient createClient() {
        final String hostname = "kafka-course-4799412004.us-west-2.bonsaisearch.net";
        final String username = "31d9x7r1d1";
        final String password = "sppeyzs8jt";

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        final UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
        credentialsProvider.setCredentials(AuthScope.ANY, credentials);

        HttpHost httpHost = new HttpHost(hostname, 443, "https");
        RestClientBuilder restClientBuilder = RestClient.builder(httpHost)
                .setHttpClientConfigCallback((httpClientBuilder) -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));

        return new RestHighLevelClient(restClientBuilder);
    }

    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = createClient();
        final String jsonString = "{ \"foo\": \"bar\" }";

        IndexRequest indexRequest = new IndexRequest("twitter", "tweets")
                .source(jsonString, XContentType.JSON);

        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        String id = indexResponse.getId();
        logger.info("Id: " + id);

        client.close();
    }
}

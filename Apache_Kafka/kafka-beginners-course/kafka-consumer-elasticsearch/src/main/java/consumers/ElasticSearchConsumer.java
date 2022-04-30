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
import java.util.Optional;

public class ElasticSearchConsumer {
    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchConsumer.class);

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

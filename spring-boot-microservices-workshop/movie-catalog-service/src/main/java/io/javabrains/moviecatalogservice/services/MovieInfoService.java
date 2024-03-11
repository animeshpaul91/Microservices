package io.javabrains.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Movie;
import io.javabrains.moviecatalogservice.models.Rating;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static io.javabrains.moviecatalogservice.utils.MovieUtils.map;

@Service
public class MovieInfoService {

    // the movie-info-service performs as a key for service discovery
    private static final String GET_MOVIES_ENDPOINT = "http://movie-info-service/movies/";
    private final RestTemplate restTemplate;

    @Autowired
    public MovieInfoService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem", commandProperties = {
            // request timeout set to 2 seconds
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
            // number of requests that it needs to see. Last 5 requests
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "6"),
            // if last (50% of 6) == 3 requests fail, kick in CB
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            // how long CB is going to sleep before it picks up again
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")})
    public CatalogItem getCatalogItem(final Rating rating) {
        final Movie retrievedMovie = restTemplate.getForObject(GET_MOVIES_ENDPOINT + rating.getMovieId(), Movie.class);
        return retrievedMovie != null ? map(retrievedMovie, rating) : new CatalogItem();
    }

    public CatalogItem getFallbackCatalogItem(final Rating rating) {
        return new CatalogItem("Movie name not found", StringUtils.EMPTY, rating.getRating());
    }

    /*
     * Bulkhead Hystrix Configuration
     *
     * @HystrixCommand(fallbackMethod = "getFallbackCatalogItem",
            // create a new threadPool and use this key to reference the threadPool
            threadPoolKey = "movieInfoPool", threadPoolProperties = {
                    // max # of concurrent threads allowed for this bulkhead. 20 threads have invoked the movie-info API and will at most wait for response
                    // from the movie-info-service.
                    @HystrixProperty(name = "coreSize", value = "20"),
                    // However, 21st thread through 30 onwards can be queued. They're just waiting and are not consuming resources.
                    // Once a thread in the active threadpool has received a response, then these waiting threads will pick up the spot in the threadpool.
                    @HystrixProperty(name = "maxQueueSize", value = "10") })
     */
}

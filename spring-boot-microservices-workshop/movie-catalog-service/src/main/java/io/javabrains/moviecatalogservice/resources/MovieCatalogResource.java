package io.javabrains.moviecatalogservice.resources;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Catalogs;
import io.javabrains.moviecatalogservice.models.Movie;
import io.javabrains.moviecatalogservice.models.Rating;
import io.javabrains.moviecatalogservice.models.UserRating;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.javabrains.moviecatalogservice.utils.MovieUtils.map;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    private static final Logger log = LoggerFactory.getLogger(MovieCatalogResource.class);

    private final RestTemplate restTemplate;
    private final WebClient webClient;

    private DiscoveryClient discoveryClient;

    // the movie-info-service performs as a key for service discovery
    private static final String GET_MOVIES_ENDPOINT = "http://movie-info-service/movies/";

    // the ratings-data-service performs as a key for service discovery
    private static final String GET_RATINGS_ENDPOINT = "http://ratings-data-service/ratingsdata/user/";

    private static final Map<String, String> CATALOG_OWNER_MAP = Map.of(
            "1", "Animesh Paul",
            "2", "Swagat Bhattacharjee",
            "3", "Boris Laishram",
            "4", "Sandeep Gohain",
            "5", "Praveen Chhetri"
    );

    @Autowired
    public MovieCatalogResource(final RestTemplate restTemplate, final WebClient webClient, final DiscoveryClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.webClient = webClient;
        this.discoveryClient = discoveryClient;
    }

    @RequestMapping("/{userId}")
    @HystrixCommand(fallbackMethod = "getFallbackCatalog")
    public Catalogs getCatalog(@PathVariable("userId") String userId) {
        // get all rated movie IDs for this user
        final UserRating userRating = getUserRatings(userId);
        final List<Rating> userRatings = new ArrayList<>(userRating.getRatings());

        // For each movie ID, call movie info service and get details
        final List<CatalogItem> catalogItems = userRatings.stream()
                .map(this::getCatalogItem)
                .collect(Collectors.toList());

        final String catalogOwner = CATALOG_OWNER_MAP.getOrDefault(userId, "Unknown Owner");
        return new Catalogs(catalogOwner, catalogItems);
    }
    
    private UserRating getUserRatings(final String userId) {
        return restTemplate.getForObject(GET_RATINGS_ENDPOINT + userId, UserRating.class);
    }

    private CatalogItem getCatalogItem(final Rating rating) {
        final Movie retrievedMovie = restTemplate.getForObject(GET_MOVIES_ENDPOINT + rating.getMovieId(), Movie.class);
        return retrievedMovie != null ? map(retrievedMovie, rating) : new CatalogItem();
    }

    public Catalogs getFallbackCatalog(@PathVariable("userId") String userId) {
        // return a dummy hardcoded response
        log.debug("CircuitBreaker tripping in");
        final String catalogOwner = "Animesh Paul";
        return new Catalogs(catalogOwner, Collections.singletonList(new CatalogItem("No movie", StringUtils.EMPTY, 0)));
    }


    /* final Movie retrievedMovie = webClient
                .get()
                .uri(GET_MOVIES_ENDPOINT + rating.getMovieId())
                .retrieve()
                .bodyToMono(Movie.class) // Mono is like a promise/future
                .block(); // blocking call until mono is fulfilled */
}

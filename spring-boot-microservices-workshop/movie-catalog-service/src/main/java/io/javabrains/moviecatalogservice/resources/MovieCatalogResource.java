package io.javabrains.moviecatalogservice.resources;

import io.javabrains.moviecatalogservice.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    private final RestTemplate restTemplate;
    private final WebClient webClient;
    private static final String GET_MOVIES_ENDPOINT = "http://localhost:8082/movies/";
    private static final String GET_RATINGS_ENDPOINT = "http://localhost:8083/ratingsdata/user/";

    private static final Map<String, String> CATALOG_OWNER_MAP = Map.of(
            "1", "Animesh Paul",
            "2", "Swagat Bhattacharjee",
            "3", "Boris Laishram",
            "4", "Sandeep Gohain",
            "5", "Praveen Chhetri"
    );

    @Autowired
    public MovieCatalogResource(final RestTemplate restTemplate, final WebClient webClient) {
        this.restTemplate = restTemplate;
        this.webClient = webClient;
    }

    @RequestMapping("/{userId}")
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
        final String movieName = Optional.ofNullable(retrievedMovie).map(Movie::getName).orElse(null);
        return new CatalogItem(movieName, "default description", rating.getRating());
    }

    /* final Movie retrievedMovie = webClient
                .get()
                .uri(GET_MOVIES_ENDPOINT + rating.getMovieId())
                .retrieve()
                .bodyToMono(Movie.class) // Mono is like a promise/future
                .block(); // blocking call until mono is fulfilled */
}

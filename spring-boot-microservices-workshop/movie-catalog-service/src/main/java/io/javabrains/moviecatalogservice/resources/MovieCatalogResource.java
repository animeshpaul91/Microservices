package io.javabrains.moviecatalogservice.resources;

import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Movie;
import io.javabrains.moviecatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    private final RestTemplate restTemplate;
    private final WebClient webClient;

    private static final String GET_MOVIES_ENDPOINT = "http://localhost:8082/movies/";

    @Autowired
    public MovieCatalogResource(final RestTemplate restTemplate, final WebClient webClient) {
        this.restTemplate = restTemplate;
        this.webClient = webClient;
    }

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
        // get all rated movie IDs
        final List<Rating> ratings = new ArrayList<>(Arrays.asList(
                new Rating("1234", 4),
                new Rating("5678", 3)
        ));

        // For each movie ID, call movie info service and get details
        return ratings.stream()
                .map(rating -> getCatalogItem(rating, restTemplate))
                .collect(Collectors.toList());
    }

    private CatalogItem getCatalogItem(Rating rating, RestTemplate restTemplate) {
        final Movie retrievedMovie = restTemplate.getForObject(GET_MOVIES_ENDPOINT + rating.getMovieId(), Movie.class);

        /* final Movie retrievedMovie = webClient
                .get()
                .uri(GET_MOVIES_ENDPOINT + rating.getMovieId())
                .retrieve()
                .bodyToMono(Movie.class) // Mono is like a promise/future
                .block(); // blocking call until mono is fulfilled */

        final String movieName = Optional.ofNullable(retrievedMovie).map(Movie::getName).orElse(null);

        return new CatalogItem(movieName, "default description", rating.getRating());
    }
}

package io.javabrains.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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

    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
    public CatalogItem getCatalogItem(final Rating rating) {
        final Movie retrievedMovie = restTemplate.getForObject(GET_MOVIES_ENDPOINT + rating.getMovieId(), Movie.class);
        return retrievedMovie != null ? map(retrievedMovie, rating) : new CatalogItem();
    }

    public CatalogItem getFallbackCatalogItem(final Rating rating) {
        return new CatalogItem("Movie name not found", StringUtils.EMPTY, rating.getRating());
    }
}

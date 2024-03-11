package io.javabrains.moviecatalogservice.resources;

import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Catalogs;
import io.javabrains.moviecatalogservice.models.Rating;
import io.javabrains.moviecatalogservice.services.MovieInfoService;
import io.javabrains.moviecatalogservice.services.UserRatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
    private static final Logger log = LoggerFactory.getLogger(MovieCatalogResource.class);

    private final MovieInfoService movieInfoService;

    private final UserRatingService userRatingService;

    private static final Map<String, String> CATALOG_OWNER_MAP = Map.of("1", "Animesh Paul", "2",
            "Swagat Bhattacharjee", "3", "Boris Laishram", "4", "Sandeep Gohain", "5", "Praveen Chhetri");

    @Autowired
    public MovieCatalogResource(final MovieInfoService movieInfoService, final UserRatingService userRatingService) {
        // spring injects the proxy for hystrix
        this.movieInfoService = movieInfoService;
        this.userRatingService = userRatingService;
    }

    /*
     * Hystrix does not work when annotated with private helper methods because the proxy wrapper class does not have
     * access to these methods. In order to make it work, we have to make these methods available for Hystrix. So, we
     * need to include it in another layer of abstraction which gets injected. So spring injects the proxy class.
     */

    @RequestMapping("/{userId}")
    public Catalogs getCatalog(@PathVariable("userId") String userId) {
        // get all rated movie IDs for this user
        final List<Rating> userRatings = new ArrayList<>(userRatingService.getUserRatings(userId).getRatings());

        // For each movie ID, call movie info service and get details
        final List<CatalogItem> catalogItems = userRatings.stream().map(movieInfoService::getCatalogItem)
                .collect(Collectors.toList());

        final String catalogOwner = CATALOG_OWNER_MAP.getOrDefault(userId, "Unknown Owner");
        return new Catalogs(catalogOwner, catalogItems);
    }

    /*
     * final Movie retrievedMovie = webClient .get() .uri(GET_MOVIES_ENDPOINT + rating.getMovieId()) .retrieve()
     * .bodyToMono(Movie.class) // Mono is like a promise/future .block(); // blocking call until mono is fulfilled
     */
}

package io.javabrains.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.javabrains.moviecatalogservice.models.Rating;
import io.javabrains.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class UserRatingService {

    // the ratings-data-service performs as a key for service discovery
    private static final String GET_RATINGS_ENDPOINT = "http://ratings-data-service/ratingsdata/user/";
    private final RestTemplate restTemplate;

    @Autowired
    public UserRatingService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "getFallbackUserRatings", commandProperties = {

    })
    public UserRating getUserRatings(final String userId) {
        return restTemplate.getForObject(GET_RATINGS_ENDPOINT + userId, UserRating.class);
    }

    public UserRating getFallbackUserRatings(final String userId) {
        final UserRating userRating = new UserRating();
        userRating.setUserId(userId);
        userRating.setRatings(Collections.singletonList(new Rating("0", 0)));
        return userRating;
    }
}

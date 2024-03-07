package io.javabrains.ratingsdataservice.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserRating {

    private static final Random random = new Random();
    private static final int high = 201;
    private static final int low = 1;
    private static final int ratingUpperLimit = 11;
    private static final int maxNumberOfMoviesAUserCanRate = 8;
    private String userId;
    private List<Rating> ratings;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Rating> getRatings() {
        return List.copyOf(new ArrayList<>(ratings));
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = List.copyOf(new ArrayList<>(ratings));
    }

    public void initData(String userId) {
        this.setUserId(userId);
        int numberOfMovies = getRandomRating(maxNumberOfMoviesAUserCanRate);
        List<Rating> movieRatings = new ArrayList<>(numberOfMovies);

        for(int i = 0; i < numberOfMovies; i++) {
            movieRatings.add(getRating());
        }

        this.setRatings(movieRatings);
    }

    private Rating getRating() {
        return new Rating(getRandomMovieID(), getRandomRating(ratingUpperLimit));
    }

    private String getRandomMovieID() {
        int movieID = low + random.nextInt(high - low);
        return Integer.toString(movieID);
    }

    private int getRandomRating(int limit) {
        return random.nextInt(limit);
    }
}

package io.javabrains.moviecatalogservice.utils;

import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Movie;
import io.javabrains.moviecatalogservice.models.Rating;

public class MovieUtils {
    private MovieUtils() {
    }

    public static final String RATING = "rating";
    public static final String ADULT = "adult";
    public static final String BACKDROP_PATH = "backdrop_path";
    public static final String BUDGET = "budget";
    public static final String ID = "id";
    public static final String HOME_PAGE = "homepage";
    public static final String IMDB_ID = "imdb_id";
    public static final String ORIGINAL_LANGUAGE = "original_language";
    public static final String ORIGINAL_TITLE = "original_title";
    public static final String OVERVIEW = "overview";
    public static final String POPULARITY = "popularity";
    public static final String POSTER_PATH = "poster_path";
    public static final String RELEASE_DATE = "release_date";
    public static final String REVENUE = "revenue";
    public static final String RUNTIME = "runtime";
    public static final String STATUS = "status";
    public static final String TAGLINE = "tagline";
    public static final String TITLE = "title";
    public static final String VIDEO = "video";
    public static final String VOTE_AVERAGE = "vote_average";
    public static final String VOTE_COUNT = "vote_count";
    public static final String CATALOG_OWNER = "catalog_owner";
    public static final String CATALOGS = "catalogs";

    // Maps movie to CatalogItem
    public static CatalogItem map(final Movie movie, final Rating rating) {
        final CatalogItem catalogItem = new CatalogItem();

        catalogItem.setRating(rating.getRating());
        catalogItem.setMovieId(movie.getMovieId());
        catalogItem.setName(movie.getName());
        catalogItem.setDescription(movie.getDescription());
        catalogItem.setAdult(movie.getAdult());
        catalogItem.setBackDropPath(movie.getBackDropPath());
        catalogItem.setBudget(movie.getBudget());
        catalogItem.setHomepage(movie.getHomepage());
        catalogItem.setImdbId(movie.getImdbId());
        catalogItem.setOriginalLanguage(movie.getOriginalLanguage());
        catalogItem.setOriginalTitle(movie.getOriginalTitle());
        catalogItem.setPopularity(movie.getPopularity());
        catalogItem.setPosterPath(movie.getPosterPath());
        catalogItem.setReleaseDate(movie.getReleaseDate());
        catalogItem.setRevenue(movie.getRevenue());
        catalogItem.setRuntime(movie.getRuntime());
        catalogItem.setStatus(movie.getStatus());
        catalogItem.setTagline(movie.getTagline());
        catalogItem.setVideo(movie.getVideo());
        catalogItem.setVoteAverage(movie.getVoteAverage());
        catalogItem.setVoteCount(movie.getVoteCount());

        return catalogItem;
    }
}

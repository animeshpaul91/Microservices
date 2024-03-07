package io.javabrains.movieinfoservice.constants;

import io.javabrains.movieinfoservice.models.Movie;
import io.javabrains.movieinfoservice.models.MovieSummary;

public class MovieUtils {
    private MovieUtils() {
    }

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

    public static Movie map(final String movieId, final MovieSummary movieSummary) {
        Movie movie = new Movie();
        movie.setMovieId(movieId);
        movie.setName(movieSummary.getTitle());
        movie.setDescription(movieSummary.getOverview());
        movie.setAdult(movieSummary.getAdult());
        movie.setBackDropPath(movieSummary.getBackDropPath());
        movie.setBudget(movieSummary.getBudget());
        movie.setHomepage(movieSummary.getHomepage());
        movie.setImdbId(movieSummary.getImdbId());
        movie.setOriginalLanguage(movieSummary.getOriginalLanguage());
        movie.setOriginalTitle(movieSummary.getOriginalTitle());
        movie.setPopularity(movieSummary.getPopularity());
        movie.setPosterPath(movieSummary.getPosterPath());
        movie.setReleaseDate(movieSummary.getReleaseDate());
        movie.setRevenue(movieSummary.getRevenue());
        movie.setRuntime(movieSummary.getRuntime());
        movie.setStatus(movieSummary.getStatus());
        movie.setTagline(movieSummary.getTagline());
        movie.setVideo(movieSummary.getVideo());
        movie.setVoteAverage(movieSummary.getVoteAverage());
        movie.setVoteCount(movieSummary.getVoteCount());

        return movie;
    }
}

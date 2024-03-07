package io.javabrains.movieinfoservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import static io.javabrains.movieinfoservice.constants.MovieUtils.ADULT;
import static io.javabrains.movieinfoservice.constants.MovieUtils.BACKDROP_PATH;
import static io.javabrains.movieinfoservice.constants.MovieUtils.BUDGET;
import static io.javabrains.movieinfoservice.constants.MovieUtils.HOME_PAGE;
import static io.javabrains.movieinfoservice.constants.MovieUtils.ID;
import static io.javabrains.movieinfoservice.constants.MovieUtils.IMDB_ID;
import static io.javabrains.movieinfoservice.constants.MovieUtils.ORIGINAL_LANGUAGE;
import static io.javabrains.movieinfoservice.constants.MovieUtils.ORIGINAL_TITLE;
import static io.javabrains.movieinfoservice.constants.MovieUtils.OVERVIEW;
import static io.javabrains.movieinfoservice.constants.MovieUtils.POPULARITY;
import static io.javabrains.movieinfoservice.constants.MovieUtils.POSTER_PATH;
import static io.javabrains.movieinfoservice.constants.MovieUtils.RELEASE_DATE;
import static io.javabrains.movieinfoservice.constants.MovieUtils.REVENUE;
import static io.javabrains.movieinfoservice.constants.MovieUtils.RUNTIME;
import static io.javabrains.movieinfoservice.constants.MovieUtils.STATUS;
import static io.javabrains.movieinfoservice.constants.MovieUtils.TAGLINE;
import static io.javabrains.movieinfoservice.constants.MovieUtils.TITLE;
import static io.javabrains.movieinfoservice.constants.MovieUtils.VIDEO;
import static io.javabrains.movieinfoservice.constants.MovieUtils.VOTE_AVERAGE;
import static io.javabrains.movieinfoservice.constants.MovieUtils.VOTE_COUNT;

public class Movie {
    @JsonProperty(ID)
    private String movieId;
    @JsonProperty(TITLE)
    private String name;
    @JsonProperty(OVERVIEW)
    private String description;

    @JsonProperty(ADULT)
    private Boolean adult;

    @JsonProperty(BACKDROP_PATH)
    private String backDropPath;
    @JsonProperty(BUDGET)
    private Long budget;
    @JsonProperty(HOME_PAGE)
    private String homepage;

    @JsonProperty(IMDB_ID)
    private String imdbId;

    @JsonProperty(ORIGINAL_LANGUAGE)
    private String originalLanguage;

    @JsonProperty(ORIGINAL_TITLE)
    private String originalTitle;

    @JsonProperty(POPULARITY)
    private Double popularity;

    @JsonProperty(POSTER_PATH)
    private String posterPath;

    @JsonProperty(RELEASE_DATE)
    private String releaseDate;

    @JsonProperty(REVENUE)
    private Long revenue;

    @JsonProperty(RUNTIME)
    private Integer runtime;

    @JsonProperty(STATUS)
    private String status;

    @JsonProperty(TAGLINE)
    private String tagline;
    @JsonProperty(VIDEO)
    private Boolean video;
    @JsonProperty(VOTE_AVERAGE)
    private Double voteAverage;
    @JsonProperty(VOTE_COUNT)
    private Integer voteCount;

    public Movie() {
        // for Jackson
    }

    public Movie(String movieId, String name, String description, Boolean adult, String backDropPath, Long budget, String homepage, String imdbId, String originalLanguage, String originalTitle, Double popularity, String posterPath, String releaseDate, Long revenue, Integer runtime, String status, String tagline, Boolean video, Double voteAverage, Integer voteCount) {
        this.movieId = movieId;
        this.name = name;
        this.description = description;
        this.adult = adult;
        this.backDropPath = backDropPath;
        this.budget = budget;
        this.homepage = homepage;
        this.imdbId = imdbId;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.revenue = revenue;
        this.runtime = runtime;
        this.status = status;
        this.tagline = tagline;
        this.video = video;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getBackDropPath() {
        return backDropPath;
    }

    public void setBackDropPath(String backDropPath) {
        this.backDropPath = backDropPath;
    }

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Long getRevenue() {
        return revenue;
    }

    public void setRevenue(Long revenue) {
        this.revenue = revenue;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }
}

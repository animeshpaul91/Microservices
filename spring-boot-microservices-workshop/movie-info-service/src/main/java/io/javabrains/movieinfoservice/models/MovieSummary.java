package io.javabrains.movieinfoservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import static io.javabrains.movieinfoservice.constants.MovieUtils.*;

public class MovieSummary {
    @JsonProperty(ID)
    private String id;
    @JsonProperty(TITLE)
    private String title;
    @JsonProperty(OVERVIEW)
    private String overview;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
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

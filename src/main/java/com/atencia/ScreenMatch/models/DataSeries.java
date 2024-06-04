package com.atencia.ScreenMatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataSeries(
        @JsonAlias("Title") String title,
        String totalSeasons,
        String imdbRating,
        @JsonAlias("Year") String year,
        @JsonAlias("Genre") String genre,
        @JsonAlias("Language") String language,
        @JsonAlias("Poster") String poster,
        @JsonAlias("Country") String country,
        @JsonAlias("Awards") String awards,
        @JsonAlias("Plot") String plot
) {
}

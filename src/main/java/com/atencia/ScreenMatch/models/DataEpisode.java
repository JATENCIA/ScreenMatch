package com.atencia.ScreenMatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataEpisode(@JsonAlias({"Title"}) String title, @JsonAlias({"Episode"}) String episode,
                          String imdbRating, @JsonAlias("Released") String released) {
}

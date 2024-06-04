package com.atencia.ScreenMatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataSeasons(@JsonAlias("Season") String season, @JsonAlias("Episodes") List<DataEpisode> episodes) {
}

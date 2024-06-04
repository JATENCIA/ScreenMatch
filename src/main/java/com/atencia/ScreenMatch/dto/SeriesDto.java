package com.atencia.ScreenMatch.dto;

import com.atencia.ScreenMatch.models.Category;


public record SeriesDto(
        Long id,
        String title,
        Integer totalSeasons,
        Double imdbRating,
        String year,
        String language,
        String poster,
        String country,
        String awards,
        Category genre,
        String plot) {
}



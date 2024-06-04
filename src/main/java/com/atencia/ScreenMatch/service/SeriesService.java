package com.atencia.ScreenMatch.service;

import com.atencia.ScreenMatch.dto.EpisodeDto;
import com.atencia.ScreenMatch.dto.SeriesDto;
import com.atencia.ScreenMatch.exception.SeriesException;
import com.atencia.ScreenMatch.models.Category;

import java.util.List;
import java.util.Optional;

public interface SeriesService {

    public List<SeriesDto> getAllSeries();

    public List<SeriesDto> getTopFiveSeries();

    public List<SeriesDto> findSeriesLatestEpisodes();

    public Optional<SeriesDto> findSeriesById(Long seriesId) throws SeriesException;

    public Optional<List<EpisodeDto>> getAllSeasons(Long seriesId) throws SeriesException;

    public List<EpisodeDto> findSeasonById(Long seriesId, Long seasonNumber) throws SeriesException;

    public List<SeriesDto> findSeriesByGenre(Category category) throws SeriesException;
}

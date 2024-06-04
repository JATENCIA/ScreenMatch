package com.atencia.ScreenMatch.service;

import com.atencia.ScreenMatch.dto.EpisodeDto;
import com.atencia.ScreenMatch.dto.SeriesDto;
import com.atencia.ScreenMatch.exception.SeriesException;
import com.atencia.ScreenMatch.models.Category;
import com.atencia.ScreenMatch.models.Episode;
import com.atencia.ScreenMatch.models.Series;
import com.atencia.ScreenMatch.repository.SeriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeriesServiceImpl implements SeriesService {

    private final SeriesRepository seriesRepository;

    public SeriesServiceImpl(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    @Override
    public List<SeriesDto> getAllSeries() {
        List<Series> series = seriesRepository.findAll();
        return series.parallelStream()
                .map(this::mapToSeriesDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SeriesDto> getTopFiveSeries() {
        List<Series> series = seriesRepository.findTop5ByOrderByImdbRatingDesc();
        return series.parallelStream()
                .map(this::mapToSeriesDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SeriesDto> findSeriesLatestEpisodes() {
        List<Series> series = seriesRepository.findSeriesLatestEpisodes();
        return series.parallelStream()
                .map(this::mapToSeriesDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SeriesDto> findSeriesById(Long seriesId) throws SeriesException {
        return Optional.of(mapToSeriesDto(seriesRepository.findById(seriesId).get()));
    }

    @Override
    public Optional<List<EpisodeDto>> getAllSeasons(Long seriesId) throws SeriesException {

        Optional<Series> series = seriesRepository.findById(seriesId);

        return series.flatMap(value -> Optional.of(value.getEpisodes()
                .stream()
                .map(episode -> new EpisodeDto(episode.getTitle(), episode.getSeason(), episode.getEpisode()))
                .collect(Collectors.toList())));

    }

    @Override
    public List<EpisodeDto> findSeasonById(Long seriesId, Long seasonNumber) throws SeriesException {

        List<Episode> episode = seriesRepository.findSeasonById(seriesId, seasonNumber);
        return episode.stream().map(e -> new EpisodeDto(e.getTitle(), e.getSeason(), e.getEpisode()))
                .collect(Collectors.toList());

    }

    @Override
    public List<SeriesDto> findSeriesByGenre(Category category) throws SeriesException {

        List<Series> series = seriesRepository.findByGenre(category);
        return series.parallelStream()
                .map(this::mapToSeriesDto)
                .collect(Collectors.toList());

    }


    private SeriesDto mapToSeriesDto(Series series) {
        return new SeriesDto(
                series.getId(),
                series.getTitle(),
                series.getTotalSeasons(),
                series.getImdbRating(),
                series.getYear(),
                series.getLanguage(),
                series.getPoster(),
                series.getCountry(),
                series.getAwards(),
                series.getGenre(),
                series.getPlot()
        );
    }
}

package com.atencia.ScreenMatch.controller;

import com.atencia.ScreenMatch.dto.EpisodeDto;
import com.atencia.ScreenMatch.dto.SeriesDto;
import com.atencia.ScreenMatch.exception.SeriesException;
import com.atencia.ScreenMatch.models.Category;
import com.atencia.ScreenMatch.service.SeriesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/series")
public class SeriesController {

    SeriesService seriesService;

    public SeriesController(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<SeriesDto>> getAllSeries() {

        List<SeriesDto> seriesDtoList = seriesService.getAllSeries();
        return new ResponseEntity<>(seriesDtoList, HttpStatus.OK);

    }

    @GetMapping("/top5")
    public ResponseEntity<List<SeriesDto>> getTopFiveSeries() {

        List<SeriesDto> seriesDtoList = seriesService.getTopFiveSeries();
        return new ResponseEntity<>(seriesDtoList, HttpStatus.OK);

    }

    @GetMapping("/releases")
    public ResponseEntity<List<SeriesDto>> findSeriesLatestEpisodes() {

        List<SeriesDto> seriesDtoList = seriesService.findSeriesLatestEpisodes();
        return new ResponseEntity<>(seriesDtoList, HttpStatus.OK);

    }

    @GetMapping("/{seriesId}")
    public ResponseEntity<?> findSeriesById(@PathVariable("seriesId") Long seriesId) throws SeriesException {

        Optional<SeriesDto> seriesDto = seriesService.findSeriesById(seriesId);

        if (seriesDto.isEmpty()) {
            return new ResponseEntity<>(new SeriesException("Not found series by id"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(seriesDto.get(), HttpStatus.OK);

    }

    @GetMapping("/{seriesId}/seasons/all")
    public ResponseEntity<?> getAllSeasons(@PathVariable("seriesId") Long seriesId) throws SeriesException {

        Optional<List<EpisodeDto>> episodeDto = seriesService.getAllSeasons(seriesId);

        if (episodeDto.isEmpty()) {
            return new ResponseEntity<>(new SeriesException("Not found series by id"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(episodeDto, HttpStatus.OK);

    }

    @GetMapping("/{seriesId}/seasons/{seasonNumber}")
    public ResponseEntity<?> findSeasonById(@PathVariable("seriesId") Long seriesId, @PathVariable("seasonNumber") Long seasonNumber) throws SeriesException {

        List<EpisodeDto> episodeDto = seriesService.findSeasonById(seriesId, seasonNumber);

        if (episodeDto.isEmpty()) {
            return new ResponseEntity<>(new SeriesException("Not found data"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(episodeDto, HttpStatus.OK);

    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> findSeriesByGenre(@PathVariable("category") String name) throws SeriesException {

        Category category = Category.fromString(name);
        List<SeriesDto> seriesDto = seriesService.findSeriesByGenre(category);

        if (seriesDto.isEmpty()) {
            return new ResponseEntity<>(new SeriesException("Not found series by id"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(seriesDto, HttpStatus.OK);

    }

}

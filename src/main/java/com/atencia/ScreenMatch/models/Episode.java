package com.atencia.ScreenMatch.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "episodes")
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Integer season;
    private Integer episode;
    private Double imdbRating;
    private LocalDate released;
    @ManyToOne
    private Series series;


    public Episode() {
    }

    public Episode(Integer season, DataEpisode episode) {

        this.title = episode.title();
        this.season = season;
        this.episode = Integer.valueOf(episode.episode());


        try {
            this.imdbRating = Double.valueOf(episode.imdbRating());
        } catch (NumberFormatException e) {
            this.imdbRating = 0.0;
        }

        try {
            this.released = LocalDate.parse(episode.released());
        } catch (DateTimeParseException e) {
            this.released = null;
        }

    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public LocalDate getReleased() {
        return released;
    }

    public void setReleased(LocalDate released) {
        this.released = released;
    }

    public Double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(Double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public Integer getEpisode() {
        return episode;
    }

    public void setEpisode(Integer episode) {
        this.episode = episode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Episode{" +
                "released=" + released +
                ", imdbRating=" + imdbRating +
                ", episode=" + episode +
                ", season=" + season +
                ", title='" + title + '\'' +
                '}';
    }
}


package com.atencia.ScreenMatch.models;

import com.atencia.ScreenMatch.models.Category;
import com.atencia.ScreenMatch.models.DataSeries;
import com.atencia.ScreenMatch.models.Episode;
import com.atencia.ScreenMatch.util.PropertyReaderApiKey;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "series")
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    private Integer totalSeasons;
    private Double imdbRating;
    private String year;
    private String language;
    private String poster;
    private String country;
    private String awards;

    @Enumerated(EnumType.STRING)
    private Category genre;
    private String plot;

    @OneToMany(mappedBy = "series", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episode> episodes;


    public Series() {
    }

    public Series(DataSeries dataSeries, PropertyReaderApiKey propertyReaderApiKey) {
        this.title = dataSeries.title();
        this.totalSeasons = Integer.parseInt(dataSeries.totalSeasons());
        this.imdbRating = Double.parseDouble(dataSeries.imdbRating());
        this.year = dataSeries.year();
        this.language = dataSeries.language();
        this.poster = dataSeries.poster();
        this.country = dataSeries.country();
        this.awards = dataSeries.awards();
        this.genre = Category.fromString(dataSeries.genre().split(",")[0].trim());
        this.plot = dataSeries.plot();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTotalSeasons() {
        return totalSeasons;
    }

    public void setTotalSeasons(Integer totalSeasons) {
        this.totalSeasons = totalSeasons;
    }

    public Double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(Double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public Category getGenre() {
        return genre;
    }

    public void setGenre(Category genre) {
        this.genre = genre;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        episodes.forEach(episode -> episode.setSeries(this));
        this.episodes = episodes;
    }


    @Override
    public String toString() {
        return "Series{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", totalSeasons=" + totalSeasons +
                ", imdbRating=" + imdbRating +
                ", year='" + year + '\'' +
                ", language='" + language + '\'' +
                ", poster='" + poster + '\'' +
                ", country='" + country + '\'' +
                ", awards='" + awards + '\'' +
                ", genre=" + genre +
                ", plot='" + plot + '\'' +
                ", episodes=" + episodes +
                '}';
    }
}

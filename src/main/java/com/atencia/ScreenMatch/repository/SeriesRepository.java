package com.atencia.ScreenMatch.repository;

import com.atencia.ScreenMatch.models.Category;
import com.atencia.ScreenMatch.models.Episode;
import com.atencia.ScreenMatch.models.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface SeriesRepository extends JpaRepository<Series, Long> {

    public Optional<Series> findByTitleContainsIgnoreCase(@Param("title") String title);

    public List<Series> findTop5ByOrderByImdbRatingDesc();

    public List<Series> findByGenre(Category category);

    @Query("SELECT s FROM Series s WHERE s.totalSeasons <= :totalSeasons AND s.imdbRating >= :imdbRating")
    public List<Series> getSeriesBySeasonAndImdbRating(@Param("totalSeasons") Integer totalSeasons, @Param("imdbRating") Double imdbRating);

    @Query("SELECT s FROM Series s JOIN s.episodes e WHERE LOWER(CONCAT(e.title, ' ')) LIKE LOWER(CONCAT('%', :title, '%'))")
    public List<Series> findByEpisodeTitleContaining(@Param("title") String title);

    @Query("SELECT e FROM Series s JOIN s.episodes e WHERE LOWER(s.title) = LOWER(:title) ORDER BY e.imdbRating DESC LIMIT 5")
    public List<Episode> findTop5EpisodesBySeries(@Param("title") String title);

    @Query("SELECT s FROM Series s JOIN s.episodes e GROUP BY s ORDER BY MAX(e.released) DESC LIMIT 5")
    public List<Series> findSeriesLatestEpisodes();

    @Query("SELECT e FROM Series s JOIN s.episodes e WHERE s.id = :seriesId AND e.season = :seasonNumber")
    public List<Episode> findSeasonById(@Param("seriesId") Long seriesId, @Param("seasonNumber") Long seasonNumber);
}

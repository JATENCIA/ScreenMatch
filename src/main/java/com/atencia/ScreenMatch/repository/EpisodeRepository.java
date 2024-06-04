package com.atencia.ScreenMatch.repository;

import com.atencia.ScreenMatch.models.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
}

package com.cinema_package.cinema_project.venue;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository extends JpaRepository<Show, Long> {

    List<Show> findByMovieId(Long movieId);

    List<Show> findByAuditoriumVenueCityAndStartTimeBetween(
            String city,
            LocalDateTime start,
            LocalDateTime end
    );
}

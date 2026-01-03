package com.cinema_package.cinema_project.movie;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findByVenue_Id(Long venueId);

    @Query("""
SELECT m FROM Movie m
WHERE (:title IS NULL OR LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%')))
  AND (:genre IS NULL OR LOWER(m.genre) LIKE LOWER(CONCAT('%', :genre, '%')))
  AND (:date IS NULL OR m.date = :date)
""")
List<Movie> filterMovies(
    @Param("title") String title,
    @Param("genre") String genre,
    @Param("date") LocalDate date
);

}
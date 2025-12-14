package com.cinema_package.cinema_project.venue;

import java.time.LocalDateTime;

import com.cinema_package.cinema_project.movie.Movie;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "shows")
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Event
    @ManyToOne(optional = false)
    private Movie movie;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String language;

    @ManyToOne(optional = true)
    private Auditorium auditorium;

    @Column(nullable = false)
    private int totalSeats;

    @Column(nullable = false)
    private int availableSeats;
    /* getters & setters */

    public Long getId() { return id; }

    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) { this.movie = movie; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public Auditorium getAuditorium() { return auditorium; }
    public void setAuditorium(Auditorium auditorium) { this.auditorium = auditorium; }

    public int getAvailableSeats() {
        return availableSeats;
    }
     public int setTotalSeats(int totalSeats) {
        return totalSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
}

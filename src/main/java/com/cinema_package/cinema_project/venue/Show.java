package com.cinema_package.cinema_project.venue;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.cinema_package.cinema_project.movie.Movie;
import com.cinema_package.cinema_project.seat.Seat;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "shows")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Show implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    // Movie
    @ManyToOne(optional = false)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String language;

    // Auditorium
    @ManyToOne(optional = true)
    private Auditorium auditorium;

    @Column(nullable = false)
    private int totalSeats;

    @Column(nullable = false)
    private int availableSeats;

    @Column(nullable = false)
    private double regularSeatPrice;

    @Column(nullable = false)
    private double premiumSeatPrice;

    @OneToMany(
        mappedBy = "show",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @JsonBackReference("show-seats") // ✅ Add this instead of @JsonManagedReference
    private List<Seat> seats = new ArrayList<>();

    /* ===== Getters & Setters ===== */

    public Long getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public double getRegularSeatPrice() {
        return regularSeatPrice;
    }

    public void setRegularSeatPrice(double regularSeatPrice) {
        this.regularSeatPrice = regularSeatPrice;
    }

    public double getPremiumSeatPrice() {
        return premiumSeatPrice;
    }

    public void setPremiumSeatPrice(double premiumSeatPrice) {
        this.premiumSeatPrice = premiumSeatPrice;
    }

    public List<Seat> getSeats() {
        return seats;
    }
}

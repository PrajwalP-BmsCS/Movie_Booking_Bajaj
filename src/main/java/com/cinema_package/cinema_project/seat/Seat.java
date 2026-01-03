package com.cinema_package.cinema_project.seat;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.cinema_package.cinema_project.enums.SeatCategory;
import com.cinema_package.cinema_project.enums.SeatStatus;
import com.cinema_package.cinema_project.venue.Show;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "seats",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"show_id", "seat_number"}
        )
    }
)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Seat implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔴 LEGACY (temporary, keep for migration safety)
    @Column(name = "movie_id")
    private Integer movieId;

    // ✅ NEW — booking should use this
    // @Column(name = "show_id", nullable = false)
    // private Long showId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "show_id", nullable = false)
    @JsonBackReference("show-seats")
    private Show show;

    @Column(name = "seat_number", nullable = false)
    private String seatNumber;   // A1, A2, B5...

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatus status = SeatStatus.AVAILABLE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatCategory category;   // REGULAR / PREMIUM

    private String heldByUser;   // user email

    private LocalDateTime holdUntil;

    /* ---------------- GETTERS / SETTERS ---------------- */

    public Long getId() {
        return id;
    }

    // LEGACY
    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    // NEW
    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }

    public SeatCategory getCategory() {
        return category;
    }

    public void setCategory(SeatCategory category) {
        this.category = category;
    }

    public String getHeldByUser() {
        return heldByUser;
    }

    public void setHeldByUser(String heldByUser) {
        this.heldByUser = heldByUser;
    }

    public LocalDateTime getHoldUntil() {
        return holdUntil;
    }

    public void setHoldUntil(LocalDateTime holdUntil) {
        this.holdUntil = holdUntil;
    }
}

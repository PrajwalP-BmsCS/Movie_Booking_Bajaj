package com.cinema_package.cinema_project.seat;

import java.time.LocalDateTime;

import com.cinema_package.cinema_project.enums.SeatStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "seats",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"movie_id", "seat_number"}
        )
    }
)
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "movie_id", nullable = false)
    private Integer movieId;

    @Column(name = "seat_number", nullable = false)
    private String seatNumber;   // A1, A2, B5...

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatus status = SeatStatus.AVAILABLE;

    private String heldByUser;   // user email

    private LocalDateTime holdUntil;

    /* getters & setters */

    public Long getId() { return id; }

    public Integer getMovieId() { return movieId; }
    public void setMovieId(Integer movieId) { this.movieId = movieId; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public SeatStatus getStatus() { return status; }
    public void setStatus(SeatStatus status) { this.status = status; }

    public String getHeldByUser() { return heldByUser; }
    public void setHeldByUser(String heldByUser) { this.heldByUser = heldByUser; }

    public LocalDateTime getHoldUntil() { return holdUntil; }
    public void setHoldUntil(LocalDateTime holdUntil) { this.holdUntil = holdUntil; }
}

package com.cinema_package.cinema_project;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking_history")
public class BookingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long movieId;

    private String userEmail;   

    private int bookedTickets;

    private int totalPrice;

    private LocalDateTime bookedAt;

    public BookingHistory() {}

    public BookingHistory(Long movieId, int bookedTickets,
                          int totalPrice, LocalDateTime bookedAt) {
        this.movieId = movieId;
        this.bookedTickets = bookedTickets;
        this.totalPrice = totalPrice;
        this.bookedAt = bookedAt;
    }

    public Long getId() { return id; }

    public Long getMovieId() { return movieId; }

    public void setMovieId(Long movieId) { this.movieId = movieId; }

    public int getBookedTickets() { return bookedTickets; }

    public void setBookedTickets(int bookedTickets) {
        this.bookedTickets = bookedTickets;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getUserEmail() { return userEmail; }

    public int getTotalPrice() { return totalPrice; }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getBookedAt() { return bookedAt; }

    public void setBookedAt(LocalDateTime bookedAt) {
        this.bookedAt = bookedAt;
    }
}


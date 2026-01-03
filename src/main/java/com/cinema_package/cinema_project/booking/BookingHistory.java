package com.cinema_package.cinema_project.booking;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(
    name = "booking_history",
    indexes = {
        @Index(name = "idx_booking_user", columnList = "userEmail"),
        @Index(name = "idx_booking_show", columnList = "showId")
    }
)
public class BookingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long showId;          // 🔑 Booking is tied to SHOW

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private int bookedTickets;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    private LocalDateTime bookedAt;

    public BookingHistory() {}

    /* ---------- getters & setters ---------- */

    public Long getId() { return id; }

    public Long getShowId() { return showId; }
    public void setShowId(Long showId) { this.showId = showId; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public int getBookedTickets() { return bookedTickets; }
    public void setBookedTickets(int bookedTickets) {
        this.bookedTickets = bookedTickets;
    }

    public int getTotalPrice() { return totalPrice; }
    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getBookedAt() { return bookedAt; }
    public void setBookedAt(LocalDateTime bookedAt) {
        this.bookedAt = bookedAt;
    }
}

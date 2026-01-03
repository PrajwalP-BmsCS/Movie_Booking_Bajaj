package com.cinema_package.cinema_project.kafka;

import java.time.LocalDateTime;
import java.util.List;

public class BookingConfirmedEvent {

    private Long bookingId;
    private Long showId;
    private String userEmail;
    private List<String> seats;
    private LocalDateTime bookedAt;

    // getters + setters
    public Long getBookingId() {
    return bookingId;
}

public void setBookingId(Long bookingId) {
    this.bookingId = bookingId;
}

public Long getShowId() {
    return showId;
}

public void setShowId(Long showId) {
    this.showId = showId;
}

public String getUserEmail() {
    return userEmail;
}

public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
}

public List<String> getSeats() {
    return seats;
}

public void setSeats(List<String> seats) {
    this.seats = seats;
}

public LocalDateTime getBookedAt() {
    return bookedAt;
}

public void setBookedAt(LocalDateTime bookedAt) {
    this.bookedAt = bookedAt;
}

}

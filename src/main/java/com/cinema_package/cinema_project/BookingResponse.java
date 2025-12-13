package com.cinema_package.cinema_project;

public class BookingResponse {

    private Long bookingId;
    private String status;
    private int remainingSeats;

    public BookingResponse() {}

    public BookingResponse(Long bookingId, String status, int remainingSeats) {
        this.bookingId = bookingId;
        this.status = status;
        this.remainingSeats = remainingSeats;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRemainingSeats() {
        return remainingSeats;
    }

    public void setRemainingSeats(int remainingSeats) {
        this.remainingSeats = remainingSeats;
    }
}

package com.cinema_package.cinema_project.booking;

import java.util.List;

public class SeatHoldRequest {
    private Long showId;
    private List<String> seats;

    public Long getShowId() { return showId; }
    public void setShowId(Long showId) { this.showId = showId; }

    public List<String> getSeats() { return seats; }
    public void setSeats(List<String> seats) { this.seats = seats; }
}

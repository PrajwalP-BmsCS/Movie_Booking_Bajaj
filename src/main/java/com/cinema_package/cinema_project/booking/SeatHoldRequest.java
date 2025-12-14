package com.cinema_package.cinema_project.booking;

import java.util.List;

public class SeatHoldRequest {
    private Integer movieId;
    private List<String> seats;

    public Integer getMovieId() { return movieId; }
    public void setMovieId(Integer movieId) { this.movieId = movieId; }

    public List<String> getSeats() { return seats; }
    public void setSeats(List<String> seats) { this.seats = seats; }
}

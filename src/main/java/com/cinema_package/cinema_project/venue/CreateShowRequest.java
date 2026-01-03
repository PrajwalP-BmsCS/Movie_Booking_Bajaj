
package com.cinema_package.cinema_project.venue;

import java.time.LocalDateTime;

public class CreateShowRequest {

    private int movieId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private int totalSeats;
    private int availableSeats;

    private double regularSeatPrice;
    private double premiumSeatPrice;

    // getters & setters

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
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
}

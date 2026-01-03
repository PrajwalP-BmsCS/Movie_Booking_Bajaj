package com.cinema_package.cinema_project.movie;

import java.time.LocalDate;

public record NewMovieRequest(
    String description,
    String director,
    String genre,
    String title,
    LocalDate date,
    String location,
    int totalSeats,
    int availableSeats,
    int price,
    Long venueId
) {}

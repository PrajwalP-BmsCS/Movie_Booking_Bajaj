package com.cinema_package.cinema_project.venue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cinema_package.cinema_project.enums.SeatCategory;
import com.cinema_package.cinema_project.enums.SeatStatus;
import com.cinema_package.cinema_project.movie.Movie;
import com.cinema_package.cinema_project.movie.MovieRepository;
import com.cinema_package.cinema_project.seat.Seat;
import com.cinema_package.cinema_project.seat.SeatRepository;

@Service
public class ShowService {

    private final MovieRepository movieRepository;
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;

    public ShowService(MovieRepository movieRepository,
                       ShowRepository showRepository,
                       SeatRepository seatRepository) {
        this.movieRepository = movieRepository;
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
    }


 @Transactional
 @CacheEvict(value = {"movie-shows", "show"}, allEntries = true)
    public Show createShow(
        int movieId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        int totalSeats,
        int availableSeats,
        double regularSeatPrice,
        double premiumSeatPrice
){

    if (startTime.isAfter(endTime) || startTime.isEqual(endTime)) {
        throw new IllegalArgumentException("Start time must be before end time");
    }

    if (availableSeats > totalSeats) {
        throw new IllegalArgumentException("Available seats cannot exceed total seats");
    }

    Movie movie = movieRepository.findById(movieId)
            .orElseThrow(() -> new IllegalArgumentException("Movie not found"));

    Show show = new Show();
    show.setMovie(movie);
    show.setStartTime(startTime);
    show.setEndTime(endTime);
    show.setTotalSeats(totalSeats);
    show.setAvailableSeats(availableSeats);
    show.setRegularSeatPrice(regularSeatPrice);
    show.setPremiumSeatPrice(premiumSeatPrice);

    Show savedShow = showRepository.save(show);

    generateSeatsForShow(savedShow);

    return savedShow;
}

  private void generateSeatsForShow(Show show) {

    if (!seatRepository.findByShowIdOrderBySeatNumberAsc(show.getId()).isEmpty()) {
        return;
    }

    List<Seat> seats = new ArrayList<>();

    int seatsPerRow = 10;
    int rows = (int) Math.ceil((double) show.getTotalSeats() / seatsPerRow);

    int seatCount = 0;

    for (char row = 'A'; row < 'A' + rows; row++) {
        for (int col = 1; col <= seatsPerRow && seatCount < show.getTotalSeats(); col++) {

            Seat seat = new Seat();
            seat.setShow(show);
            seat.setSeatNumber(row + String.valueOf(col));
            seat.setStatus(SeatStatus.AVAILABLE);

            // First 60% REGULAR, rest PREMIUM
            if (seatCount < show.getTotalSeats() * 0.6) {
                seat.setCategory(SeatCategory.REGULAR);
            } else {
                seat.setCategory(SeatCategory.PREMIUM);
            }

            seats.add(seat);
            seatCount++;
        }
    }

    seatRepository.saveAll(seats);
}
    @Cacheable(value = "movie-shows", key = "#movieId")
    public List<Show> getShowsByMovie(int movieId) {
        return showRepository.findByMovieId((long) movieId);
    }

@Transactional
    @Caching(evict = {
        @CacheEvict(value = "show", key = "#showId"),
        @CacheEvict(value = "movie-shows", allEntries = true)
    })
public Show updateShow(
        Long showId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Double regularSeatPrice,
        Double premiumSeatPrice
) {

    Show show = showRepository.findById(showId)
            .orElseThrow(() -> new IllegalArgumentException("Show not found"));

    if (startTime != null && endTime != null) {
        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
        show.setStartTime(startTime);
        show.setEndTime(endTime);
    }

    if (regularSeatPrice != null && regularSeatPrice > 0) {
        show.setRegularSeatPrice(regularSeatPrice);
    }

    if (premiumSeatPrice != null && premiumSeatPrice > 0) {
        show.setPremiumSeatPrice(premiumSeatPrice);
    }

    return showRepository.save(show);
}
}

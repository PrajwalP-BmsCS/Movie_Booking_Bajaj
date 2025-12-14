package com.cinema_package.cinema_project.venue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cinema_package.cinema_project.enums.SeatCategory;
import com.cinema_package.cinema_project.enums.SeatStatus;
import com.cinema_package.cinema_project.movie.Movie;
import com.cinema_package.cinema_project.movie.MovieRepository;
import com.cinema_package.cinema_project.seat.Seat;
import com.cinema_package.cinema_project.seat.SeatRepository;

import jakarta.transaction.Transactional;

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
    public Show createShow(int movieId, LocalDateTime startTime,int totalSeats,int availSeat) {

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Movie not found"));

        Show show = new Show();
        show.setMovie(movie);
        show.setStartTime(startTime);
        show.setTotalSeats(totalSeats);
        show.setAvailableSeats(totalSeats);
        Show savedShow = showRepository.save(show);

        generateSeatsForShow(savedShow.getId());

        return savedShow;
    }

    private void generateSeatsForShow(Long showId) {

        // safety: avoid duplicate seats
        if (!seatRepository.findByShowIdOrderBySeatNumberAsc(showId).isEmpty()) {
            return;
        }

        List<Seat> seats = new ArrayList<>();

        for (char row = 'A'; row <= 'E'; row++) {
            for (int col = 1; col <= 10; col++) {
                Seat seat = new Seat();
                seat.setShowId(showId);
                seat.setSeatNumber(row + String.valueOf(col));
                seat.setStatus(SeatStatus.AVAILABLE);
                if (row <= 'C') {
                    seat.setCategory(SeatCategory.REGULAR);
                } else {
                    seat.setCategory(SeatCategory.PREMIUM);
                }
                seats.add(seat);
            }
        }

        seatRepository.saveAll(seats);
    }

    public List<Show> getShowsByMovie(int movieId) {
        return showRepository.findByMovieId((long) movieId);
    }
}

package com.cinema_package.cinema_project;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    /* ---------------- MOVIES ---------------- */

    @GetMapping
    public List<Movie> getMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String genre
    ) {
        return movieService.getAllMovies(title, date, location, genre);
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable Integer id) {
        return movieService.getMovieById(id);
    }

    @PostMapping
    public void addMovie(
            @RequestBody CinemaProjectApplication.NewMovieRequest movie) {
        movieService.addMovie(movie);
    }

    @PutMapping("/{movieId}")
    public void updateMovie(
            @PathVariable Integer movieId,
            @RequestBody CinemaProjectApplication.NewMovieRequest movie) {
        movieService.updateMovie(movieId, movie);
    }

    @DeleteMapping("/{movieId}")
    public void deleteMovie(@PathVariable Integer movieId) {
        movieService.deleteMovie(movieId);
    }

    /* ---------------- BOOKINGS ---------------- */

    // ✅ REAL BOOKING (DB + TRANSACTION)
    @PostMapping("/booking/{movieId}/{quantity}/{totalPrice}")
    public BookingResponse createBooking(
            @PathVariable Integer movieId,
            @PathVariable Integer quantity,
            @PathVariable Integer totalPrice
    ) {
        return movieService.bookMovie(movieId, quantity, totalPrice);
    }

    // ✅ READ BOOKING HISTORY FROM DB
    @GetMapping("/bookings")
    public List<BookingHistory> getAllBookings() {
        return movieService.getAllBookings();
    }

    // ✅ SUMMARY (COMPUTED, NOT DB)
    @GetMapping("/booking/summary")
    public List<BookingSummary> getBookingSummary() {
        return movieService.getBookingSummary();
    }
}

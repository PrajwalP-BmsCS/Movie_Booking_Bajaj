package com.cinema_package.cinema_project.movie;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cinema_package.cinema_project.booking.BookingHistory;
import com.cinema_package.cinema_project.booking.BookingResponse;
import com.cinema_package.cinema_project.booking.SeatBookingRequest;
import com.cinema_package.cinema_project.booking.SeatHoldRequest;

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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public void addMovie(
            @RequestBody NewMovieRequest movie) {
        movieService.addMovie(movie);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{movieId}")
    public void updateMovie(
            @PathVariable Integer movieId,
            @RequestBody NewMovieRequest movie) {
        movieService.updateMovie(movieId, movie);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{movieId}")
    public void deleteMovie(@PathVariable Integer movieId) {
        movieService.deleteMovie(movieId);
    }

    /* ---------------- BOOKINGS ---------------- */

    // ✅ REAL BOOKING (DB + TRANSACTION)

    @PostMapping("/booking/seats")
    public BookingResponse bookSeats(
            @RequestBody SeatBookingRequest request) {
        return movieService.bookSelectedSeats(request);
    }

    @PostMapping("/booking/hold")
    public String holdSeats(@RequestBody SeatHoldRequest request) {
        movieService.holdSeats(request);
        return "Seats held successfully (5 min)";
    }

    // @PostMapping("/booking/{movieId}/{quantity}/{totalPrice}")
    // public BookingResponse createBooking(
    //         @PathVariable Integer movieId,
    //         @PathVariable Integer quantity,
    //         @PathVariable Integer totalPrice
    // ) {
    //     return movieService.bookMovie(movieId, quantity, totalPrice);
    // }

    // ✅ READ BOOKING HISTORY FROM DB
    @GetMapping("/bookings")
    public List<BookingHistory> getAllBookings() {
        return movieService.getAllBookings();
    }


    @GetMapping("/booking/my")
    public List<BookingHistory> myBookings() {
        return movieService.getMyBookings();
    }

}

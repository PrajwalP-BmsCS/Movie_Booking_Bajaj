package com.cinema_package.cinema_project.movie;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;

import com.cinema_package.cinema_project.CinemaProjectApplication;
import com.cinema_package.cinema_project.booking.BookingHistory;
import com.cinema_package.cinema_project.booking.BookingHistoryRepository;
import com.cinema_package.cinema_project.booking.BookingResponse;
import com.cinema_package.cinema_project.booking.BookingSummary;

import jakarta.transaction.Transactional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final BookingHistoryRepository bookingHistoryRepository;

    public MovieService(MovieRepository movieRepository,
                        BookingHistoryRepository bookingHistoryRepository) {
        this.movieRepository = movieRepository;
        this.bookingHistoryRepository = bookingHistoryRepository;
    }

    /* ---------------- MOVIE APIs ---------------- */

    public List<Movie> filterMovies(String title, LocalDate date,
                                    String location, String genre) {

        List<Movie> movies = movieRepository.findAll();
        List<Movie> filteredMovies = new ArrayList<>();

        for (Movie movie : movies) {
            boolean match = true;

            if (title != null &&
                !movie.getTitle().toLowerCase().contains(title.toLowerCase()))
                match = false;

            if (date != null && !movie.getDate().isEqual(date))
                match = false;

            if (location != null &&
                !movie.getLocation().toLowerCase().contains(location.toLowerCase()))
                match = false;

            if (genre != null &&
                !movie.getGenre().toLowerCase().contains(genre.toLowerCase()))
                match = false;

            if (match)
                filteredMovies.add(movie);
        }

        return filteredMovies;
    }

    public List<Movie> getAllMovies(String title, LocalDate date,
                                    String location, String genre) {
        if (title == null && date == null && location == null && genre == null) {
            return movieRepository.findAll();
        }
        return filterMovies(title, date, location, genre);
    }

    public Movie getMovieById(Integer id) {
        return movieRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid movie ID: " + id));
    }

    public void addMovie(NewMovieRequest movie2) {
        Movie movie = new Movie();
        movie.setTitle(movie2.title());
        movie.setDirector(movie2.director());
        movie.setDescription(movie2.description());
        movie.setGenre(movie2.genre());
        movie.setDate(movie2.date());
        movie.setLocation(movie2.location());
        movie.setTotalSeats(movie2.totalSeats());
        movie.setAvailableSeats(movie2.availableSeats());
        movie.setPrice(movie2.price());
        movieRepository.save(movie);
    }

    public void updateMovie(Integer id,
                            NewMovieRequest movie2) {

        Movie movie = getMovieById(id);

        movie.setTitle(movie2.title());
        movie.setDirector(movie2.director());
        movie.setDescription(movie2.description());
        movie.setGenre(movie2.genre());
        movie.setDate(movie2.date());
        movie.setLocation(movie2.location());
        movie.setTotalSeats(movie2.totalSeats());
        movie.setAvailableSeats(movie2.availableSeats());
        movie.setPrice(movie2.price());

        movieRepository.save(movie);
    }

    public void deleteMovie(Integer id) {
        Movie movie = getMovieById(id);
        movieRepository.delete(movie);
    }

    /* ---------------- BOOKING SUMMARY (DTO) ---------------- */

    public List<BookingSummary> getBookingSummary() {
        List<Movie> movies = movieRepository.findAll();
        List<BookingSummary> summary = new ArrayList<>();

        for (Movie movie : movies) {
            int bookedTickets =
                    movie.getTotalSeats() - movie.getAvailableSeats();

            if (bookedTickets > 0) {
                BookingSummary bs = new BookingSummary();
                bs.setId(movie.getId());
                bs.setTitle(movie.getTitle());
                bs.setDirector(movie.getDirector());
                bs.setDescription(movie.getDescription());
                bs.setGenre(movie.getGenre());
                bs.setDate(movie.getDate());
                bs.setLocation(movie.getLocation());
                bs.setBookedTickets(bookedTickets);
                bs.setTotalPrice(bookedTickets * movie.getPrice());
                summary.add(bs);
            }
        }
        return summary;
    }

    /* ---------------- REAL BOOKING (DB + TRANSACTION) ---------------- */

@Transactional
public BookingResponse bookMovie(Integer movieId, int tickets, int payment) {

    Movie movie = getMovieById(movieId);
    Authentication auth =
        SecurityContextHolder.getContext().getAuthentication();

    String userEmail = auth.getName();
    if (tickets <= 0) {
        throw new IllegalArgumentException("Invalid ticket count");
    }

    if (movie.getAvailableSeats() < tickets) {
        throw new IllegalArgumentException("Not enough seats available");
    }

    int totalPrice = tickets * movie.getPrice();
    if (payment != totalPrice) {
        throw new IllegalArgumentException("Invalid total price");
    }

    // Reduce seats
    movie.setAvailableSeats(movie.getAvailableSeats() - tickets);
    movieRepository.save(movie);

    // Save booking history (ENTITY)
    BookingHistory booking = new BookingHistory();
    booking.setUserEmail(userEmail);
    booking.setMovieId(movieId.longValue());
    booking.setBookedTickets(tickets);
    booking.setTotalPrice(totalPrice);
    booking.setBookedAt(LocalDateTime.now());

    BookingHistory saved = bookingHistoryRepository.save(booking);

    // Return DTO (NOT entity)
    return new BookingResponse(
            saved.getId(),
            "SUCCESS",
            movie.getAvailableSeats()
    );
}

    /* ---------------- READ BOOKING HISTORY FROM DB ---------------- */

    public List<BookingHistory> getAllBookings() {
        return bookingHistoryRepository.findAll();
    }
}

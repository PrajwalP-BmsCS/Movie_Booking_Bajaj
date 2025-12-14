package com.cinema_package.cinema_project.movie;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cinema_package.cinema_project.booking.BookingHistory;
import com.cinema_package.cinema_project.booking.BookingHistoryRepository;
import com.cinema_package.cinema_project.booking.BookingResponse;
import com.cinema_package.cinema_project.booking.SeatBookingRequest;
import com.cinema_package.cinema_project.booking.SeatHoldRequest;
import com.cinema_package.cinema_project.enums.SeatStatus;
import com.cinema_package.cinema_project.seat.Seat;
import com.cinema_package.cinema_project.seat.SeatRepository;
import com.cinema_package.cinema_project.venue.Show;
import com.cinema_package.cinema_project.venue.ShowRepository;

import jakarta.transaction.Transactional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final BookingHistoryRepository bookingHistoryRepository;
    private final SeatRepository seatRepository;
    private final ShowRepository showRepository;

    public MovieService(MovieRepository movieRepository,
                        BookingHistoryRepository bookingHistoryRepository,
                        SeatRepository seatRepository,ShowRepository showRepository) {


        this.movieRepository = movieRepository;
        this.bookingHistoryRepository = bookingHistoryRepository;
        this.seatRepository = seatRepository;
        this.showRepository = showRepository;


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
    // movie.setTotalSeats(movie2.totalSeats());
    // movie.setAvailableSeats(movie2.availableSeats());
    movie.setPrice(movie2.price());

    movieRepository.save(movie);
    // ❌ NO seat generation here
}

//     private void generateSeatsForMovie(Integer movieId) {

//     // safety check (optional but good)
//     if (!seatRepository.findByMovieId(movieId).isEmpty()) {
//         return;
//     }

//     for (char row = 'A'; row <= 'E'; row++) {
//         for (int seatNum = 1; seatNum <= 10; seatNum++) {

//             Seat seat = new Seat();
//             seat.setMovieId(movieId);
//             seat.setSeatNumber(row + String.valueOf(seatNum));
//             seat.setStatus(SeatStatus.AVAILABLE);
//             if (row <= 'C') {
//                 seat.setCategory(SeatCategory.REGULAR);
//             } else {
//                 seat.setCategory(SeatCategory.PREMIUM);
//             }

//             seatRepository.save(seat);
//         }
//     }
// }


    public void updateMovie(Integer id,
                            NewMovieRequest movie2) {

        Movie movie = getMovieById(id);

        movie.setTitle(movie2.title());
        movie.setDirector(movie2.director());
        movie.setDescription(movie2.description());
        movie.setGenre(movie2.genre());
        movie.setDate(movie2.date());
        movie.setLocation(movie2.location());
        movie.setPrice(movie2.price());

        movieRepository.save(movie);
    }

    public void deleteMovie(Integer id) {
        Movie movie = getMovieById(id);
        movieRepository.delete(movie);
    }

    /* ---------------- BOOKING SUMMARY (DTO) ---------------- */



    /* ---------------- REAL BOOKING (DB + TRANSACTION) ---------------- */
@Transactional
public BookingResponse bookSelectedSeats(SeatBookingRequest request) {

    String userEmail = SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getName();

    Show show = showRepository.findById(request.getShowId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid show"));

    List<Seat> seats = seatRepository
            .findByShowIdAndSeatNumberIn(
                    request.getShowId(),
                    request.getSeats()
            );

    if (seats.size() != request.getSeats().size()) {
        throw new IllegalArgumentException("One or more seats not found");
    }

    LocalDateTime now = LocalDateTime.now();

    for (Seat seat : seats) {

        // auto-expire stale holds
        if (seat.getStatus() == SeatStatus.HELD &&
            seat.getHoldUntil().isBefore(now)) {

            seat.setStatus(SeatStatus.AVAILABLE);
            seat.setHeldByUser(null);
            seat.setHoldUntil(null);
        }

        if (seat.getStatus() != SeatStatus.HELD ||
            !userEmail.equals(seat.getHeldByUser())) {

            throw new IllegalArgumentException(
                "Seat must be held by you: " + seat.getSeatNumber()
            );
        }
    }

    // finalize booking
    for (Seat seat : seats) {
        seat.setStatus(SeatStatus.BOOKED);
        seat.setHeldByUser(null);
        seat.setHoldUntil(null);
    }
    
    show.setAvailableSeats(
    show.getAvailableSeats() - seats.size()
);
    seatRepository.saveAll(seats);

    // persist immutable booking record
    BookingHistory booking = new BookingHistory();
    booking.setUserEmail(userEmail);
    booking.setShowId(show.getId());
    booking.setBookedTickets(seats.size());
    booking.setTotalPrice(seats.size() * 250); // pricing later
    booking.setBookedAt(LocalDateTime.now());

    BookingHistory saved = bookingHistoryRepository.save(booking);

    return new BookingResponse(
            saved.getId(),
            "SUCCESS",
            seats.size()
    );
}


@Transactional
public void holdSeats(SeatHoldRequest request) {

    String userEmail = SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getName();

    // Validate show
    showRepository.findById(request.getShowId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid show"));

    List<Seat> seats = seatRepository
            .findByShowIdAndSeatNumberIn(
                    request.getShowId(),
                    request.getSeats()
            );

    if (seats.size() != request.getSeats().size()) {
        throw new IllegalArgumentException("Invalid seat selection");
    }

    LocalDateTime now = LocalDateTime.now();

    for (Seat seat : seats) {

        // auto-release expired holds
        if (seat.getStatus() == SeatStatus.HELD &&
            seat.getHoldUntil().isBefore(now)) {

            seat.setStatus(SeatStatus.AVAILABLE);
            seat.setHeldByUser(null);
            seat.setHoldUntil(null);
        }

        if (seat.getStatus() != SeatStatus.AVAILABLE) {
            throw new IllegalArgumentException(
                "Seat not available: " + seat.getSeatNumber()
            );
        }
    }

    for (Seat seat : seats) {
        seat.setStatus(SeatStatus.HELD);
        seat.setHeldByUser(userEmail);
        seat.setHoldUntil(now.plusMinutes(5));
    }

    seatRepository.saveAll(seats);
}



    /* ---------------- READ BOOKING HISTORY FROM DB ---------------- */

    public List<BookingHistory> getAllBookings() {
        return bookingHistoryRepository.findAll();
    }

    public List<BookingHistory> getMyBookings() {
    String email = SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getName();

    System.out.println("Fetching bookings for user: " + email);
    System.out.println("Repository instance: " + bookingHistoryRepository.findByUserEmail(email));

    return bookingHistoryRepository.findByUserEmail(email);
    
    }

}

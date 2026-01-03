package com.cinema_package.cinema_project.venue;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cinema_package.cinema_project.movie.Movie;
import com.cinema_package.cinema_project.movie.MovieService;

@RestController
@RequestMapping("/venues")
public class VenueController {

    private final VenueService venueService;
    private final MovieService movieService;

    // ✅ BOTH services injected here
    public VenueController(VenueService venueService,
                           MovieService movieService) {
        this.venueService = venueService;
        this.movieService = movieService;
    }

    /* -------- CREATE -------- */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Venue addVenue(@RequestBody Venue venue) {
        return venueService.addVenue(venue);
    }

    /* -------- READ -------- */
    @GetMapping
    public List<Venue> getAllVenues() {
        return venueService.getAllVenues();
    }

    @GetMapping("/{id}")
    public Venue getVenueById(@PathVariable Long id) {
        return venueService.getVenueById(id);
    }

    /* -------- DELETE -------- */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteVenue(@PathVariable Long id) {
        venueService.deleteVenue(id);
    }

    /* -------- VENUE → MOVIES -------- */
    @GetMapping("/{venueId}/movies")
    public List<Movie> getMoviesInVenue(@PathVariable Long venueId) {
        return movieService.getMoviesByVenue(venueId);
    }
}

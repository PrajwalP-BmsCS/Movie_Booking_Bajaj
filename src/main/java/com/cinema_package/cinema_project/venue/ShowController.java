package com.cinema_package.cinema_project.venue;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shows")
public class ShowController {

    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    // ADMIN: create show
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Show createShow(
            @RequestParam int movieId,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime startTime,
            @RequestParam int totSeat,
            @RequestParam int availSeat
            ) {

        return showService.createShow(movieId, startTime,totSeat,availSeat);
    }

    // PUBLIC: list shows for a movie
    @GetMapping("/movie/{movieId}")
    public List<Show> getShowsByMovie(@PathVariable int movieId) {
        return showService.getShowsByMovie(movieId);
    }
}

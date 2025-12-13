package com.cinema_package.cinema_project.seat;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping("/movie/{movieId}/seats")
    public List<Seat> getSeats(@PathVariable Long movieId) {
        return seatService.getSeatsForMovie(movieId);
    }
}

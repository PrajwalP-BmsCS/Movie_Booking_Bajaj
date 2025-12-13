package com.cinema_package.cinema_project.seat;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public List<Seat> getSeatsForMovie(Long movieId) {
        return seatRepository.findByMovieIdOrderBySeatNumberAsc(movieId);
    }
}

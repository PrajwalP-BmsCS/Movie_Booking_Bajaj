package com.cinema_package.cinema_project.seat;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    
@Cacheable(value = "show-seats-all", key = "#showId")
public List<Seat> getSeatsByShow(Long showId) {
    return seatRepository.findByShowIdOrderBySeatNumberAsc(showId);
}

}

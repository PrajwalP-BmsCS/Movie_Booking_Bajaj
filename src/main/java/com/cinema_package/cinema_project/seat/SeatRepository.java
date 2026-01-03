package com.cinema_package.cinema_project.seat;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cinema_package.cinema_project.enums.SeatStatus;


public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByMovieId(Integer movieId);

    List<Seat> findByMovieIdAndStatus(Integer movieId, SeatStatus status);

    @Query("""
        SELECT s FROM Seat s
        WHERE s.status = 'HELD'
        AND s.holdUntil < :now
    """)
    List<Seat> findExpiredHolds(LocalDateTime now);
    List<Seat> findByMovieIdOrderBySeatNumberAsc(Long movieId);
    List<Seat> findByMovieIdAndSeatNumberIn(
        Integer movieId,
        List<String> seatNumbers
);

    List<Seat> findByShowIdAndSeatNumberIn(
    Long showId,
    List<String> seatNumbers
);

    List<Seat> findByShowIdOrderBySeatNumberAsc(Long showId);
    

    
}

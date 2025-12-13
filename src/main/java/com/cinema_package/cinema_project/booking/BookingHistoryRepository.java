package com.cinema_package.cinema_project.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingHistoryRepository
        extends JpaRepository<BookingHistory, Long> {

    List<BookingHistory> findByUserEmail(String userEmail);
}

package com.cinema_package.cinema_project;

import org.springframework.data.jpa.repository.JpaRepository;


public interface BookingHistoryRepository extends JpaRepository<BookingHistory, Long> {
}

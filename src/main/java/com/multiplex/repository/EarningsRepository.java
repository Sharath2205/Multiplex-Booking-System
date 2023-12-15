package com.multiplex.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multiplex.entity.Earnings;

public interface EarningsRepository extends JpaRepository<Earnings, Integer> {
	Optional<Earnings> findByBookingId(int bookingId);
    List<Earnings> findByBookingDateBetween(LocalDate fromDate, LocalDate toDate);
}

package com.multiplex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multiplex.entity.Booking;
import com.multiplex.entity.BookingDetails;

public interface BookingDetailsRepository extends JpaRepository<BookingDetails, Integer> {
	List<BookingDetails> findByBooking(Booking booking);
}

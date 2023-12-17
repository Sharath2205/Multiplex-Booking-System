package com.multiplex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multiplex.entity.Booking;
import com.multiplex.entity.User;

public interface BookingRepository extends JpaRepository<Booking, Integer>{
	List<Booking> findAllByUser(User user);
}

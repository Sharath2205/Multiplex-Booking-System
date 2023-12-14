package com.multiplex.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.multiplex.entity.Hall;
import com.multiplex.entity.SeatType;
import com.multiplex.entity.ShowAvailability;
import com.multiplex.entity.Show;

public interface ShowAvailabilityRepository extends JpaRepository<ShowAvailability, Integer> {

	@Query("SELECT sa FROM ShowAvailability sa " +
	           "WHERE sa.show = :show " +
	           "AND sa.hall = :hall " +
	           "AND sa.seatType = :seatType " +
	           "AND sa.availabilityDate = :availabilityDate")
	Optional<ShowAvailability> findByShowAndHallAndSeatTypeAndAvailabilityDate(Show show, Hall hall, SeatType seatType,
			LocalDate availabilityDate);

}

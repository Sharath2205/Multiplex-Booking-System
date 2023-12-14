package com.multiplex.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multiplex.entity.SeatType;

public interface SeatTypeRepository extends JpaRepository<SeatType, Integer> {
	SeatType findBySeatTypeDescIgnoreCase(String seatTypeDesc);
	
	List<SeatType> findAllBySeatTypeDescIgnoreCaseIn(Set<String> seatTypeDesc);
	
}

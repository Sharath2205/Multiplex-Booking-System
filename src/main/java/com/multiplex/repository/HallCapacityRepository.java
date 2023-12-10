package com.multiplex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multiplex.embedded.HallCapacityId;
import com.multiplex.entity.Hall;
import com.multiplex.entity.HallCapacity;

public interface HallCapacityRepository extends JpaRepository<HallCapacity, HallCapacityId> {
	List<HallCapacity> findByHall(Hall hall); 
}

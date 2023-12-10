package com.multiplex.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multiplex.entity.Hall;

public interface HallRepository extends JpaRepository<Hall, Integer> {
	Optional<Hall> findByHallDescIgnoreCase(String hallDesc);
}

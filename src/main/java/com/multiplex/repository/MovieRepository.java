package com.multiplex.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multiplex.entity.Movies;

public interface MovieRepository extends JpaRepository<Movies, Integer>{
	Optional<Movies> findByMovieNameIgnoreCase(String movieName);
}

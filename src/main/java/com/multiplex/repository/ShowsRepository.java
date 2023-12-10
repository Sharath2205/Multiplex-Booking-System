package com.multiplex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.multiplex.entity.Shows;

public interface ShowsRepository extends JpaRepository<Shows, Integer> {
	@Query("SELECT s FROM Shows s WHERE s.movie.movieName = :movieName")
	List<Shows> findByMovieName(@Param("movieName") String movieName);
}

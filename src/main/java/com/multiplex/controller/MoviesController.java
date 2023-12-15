package com.multiplex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multiplex.dto.MoviesDto;
import com.multiplex.service.MovieServiceImpl;

@RestController
@RequestMapping("/api/v1/movies")
public class MoviesController {

	@Autowired
	MovieServiceImpl movieService;
	
	@GetMapping(value = "/{movieName}")
	public ResponseEntity<MoviesDto> getMovieByMovieName(@PathVariable String movieName) {
		return new ResponseEntity<>(movieService.getMovieDetailsByName(movieName), HttpStatus.OK);
	}

}

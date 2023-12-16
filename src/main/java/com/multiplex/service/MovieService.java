package com.multiplex.service;

import com.multiplex.dto.MoviesDto;
import com.multiplex.dto.PublishMovieDto;
import com.multiplex.entity.Movies;

public interface MovieService {
	
	String addMovie(PublishMovieDto movie);
	
	Movies getByMovieId(int movieId);
	
	MoviesDto getMovieDetailsByName(String movieName);
}

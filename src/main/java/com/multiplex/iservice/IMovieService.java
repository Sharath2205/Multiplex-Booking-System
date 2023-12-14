package com.multiplex.iservice;

import com.multiplex.dto.PublishMovieDto;

public interface IMovieService {
	
	String addMovie(PublishMovieDto movie);
}

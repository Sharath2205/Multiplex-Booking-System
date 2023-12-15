package com.multiplex.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multiplex.dto.MoviesDto;
import com.multiplex.dto.PublishMovieDto;
import com.multiplex.dto.UserShowsDto;
import com.multiplex.entity.Movies;
import com.multiplex.entity.Show;
import com.multiplex.exception.MovieNotFoundException;
import com.multiplex.repository.MovieRepository;
import com.multiplex.util.AppConstants;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	MovieRepository movieRepository;

	public String addMovie(PublishMovieDto movieDto) {
		Movies movie = new Movies();
		movie.setMovieName(movieDto.getMovieName());
		movie.setGenre(movieDto.getGenre());
		movie = movieRepository.save(movie);
		return movie != null ? "Movie added successfully" : "Oops! Try again";
	}

	public Movies getByMovieId(int movieId) {
		return movieRepository.findById(movieId).get();
	}
	
	public Movies getById(int movieId) {
		Movies m =  movieRepository.findById(movieId).get();
		
		List<Show> temp = new ArrayList<>();
		for(Show s: m.getShows()) {
			Show show = new Show(s.getShowId(), s.getSlotNo(), s.getFromDate(), s.getToDate());
			temp.add(show);
		}
		m.setShows(temp);
		return m;
	}
	
	public MoviesDto getMovieDetailsByName(String movieName) {
        Optional<Movies> opMovie = movieRepository.findByMovieNameIgnoreCase(movieName);
        if (opMovie.isPresent()) {
        	Movies movie = opMovie.get();
        	List<UserShowsDto> shows = new ArrayList<>();
        	
        	for(Show show: movie.getShows()) {
        		shows.add(new UserShowsDto( show.getHall().getHallDesc(), show.getMovie().getMovieName() ,show.getSlotNo(), show.getFromDate(), show.getToDate()));
        	}
        	
        	return new MoviesDto(movie.getMovieId(), movie.getMovieName(), movie.getGenre(), shows);
        }
        throw new MovieNotFoundException(movieName + AppConstants.MOVIE_NOT_FOUND);
    }
}

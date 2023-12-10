package com.multiplex.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multiplex.dto.MoviesDto;
import com.multiplex.dto.UserShowsDto;
import com.multiplex.entity.Movies;
import com.multiplex.entity.Shows;
import com.multiplex.exception.MovieNotFoundException;
import com.multiplex.iservice.IMovieService;
import com.multiplex.repository.MovieRepository;
import com.multiplex.util.AppConstants;

@Service
public class MovieService implements IMovieService {

	@Autowired
	MovieRepository movieRepository;

	public boolean addMovie(Movies movie) {
		movie.setMovieName(movie.getMovieName().toLowerCase());
		Movies saved = movieRepository.save(movie);
		return saved != null;
	}

	public Movies getByMovieId(int movieId) {
		return movieRepository.findById(movieId).get();
	}
	
	public Movies getById(int movieId) {
		Movies m =  movieRepository.findById(movieId).get();
		
		List<Shows> temp = new ArrayList<>();
		for(Shows s: m.getShows()) {
			Shows show = new Shows(s.getShowId(), s.getSlotNo(), s.getFromDate(), s.getToDate());
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
        	
        	for(Shows show: movie.getShows()) {
        		shows.add(new UserShowsDto( show.getHall().getHallDesc(), show.getMovie().getMovieName() ,show.getSlotNo(), show.getFromDate(), show.getToDate()));
        	}
        	
        	return new MoviesDto(movie.getMovieId(), movie.getMovieName(), shows);
        }
        throw new MovieNotFoundException(movieName + AppConstants.MOVIE_NOT_FOUND);
    }
}

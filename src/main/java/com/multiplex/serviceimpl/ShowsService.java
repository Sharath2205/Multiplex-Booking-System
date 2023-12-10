package com.multiplex.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.multiplex.dto.UserShowsDto;
import com.multiplex.entity.Hall;
import com.multiplex.entity.Movies;
import com.multiplex.entity.Shows;
import com.multiplex.exception.MovieNotFoundException;
import com.multiplex.exception.ShowNotFoundException;
import com.multiplex.iservice.IShowsService;
import com.multiplex.repository.HallRepository;
import com.multiplex.repository.MovieRepository;
import com.multiplex.repository.ShowsRepository;
import com.multiplex.util.AppConstants;

@Service
public class ShowsService implements IShowsService {

	@Autowired
	ShowsRepository showsRepository;

	@Autowired
	HallRepository hallRepository;

	@Autowired
	MovieRepository movieRepository;

	@Transactional(readOnly = false)
	public boolean addShow(Shows show) {
		Optional<Hall> existingHall = hallRepository.findByHallDescIgnoreCase(show.getHall().getHallDesc());

		if (existingHall.isEmpty())
			hallRepository.save(show.getHall());
		else
			show.setHall(existingHall.get());

		Optional<Movies> existingMovie = movieRepository.findByMovieNameIgnoreCase(show.getMovie().getMovieName());

		if (existingMovie.isEmpty())
			movieRepository.save(show.getMovie());
		else
			show.setMovie(existingMovie.get());

		Shows savedShow = showsRepository.save(show);

		return savedShow != null;
	}

	@Transactional(readOnly = false)
	public boolean deleteShowById(int id) {
		Optional<Shows> show = showsRepository.findById(id);

		if (show.isPresent()) {
			showsRepository.deleteById(id);
			return true;
		}

		throw new ShowNotFoundException("Show with show id \"" + id + "\" not found");
	}

	@Override
	public List<UserShowsDto> getAllShowsByMovieName(String movieName) {
		Optional<Movies> opMovie = movieRepository.findByMovieNameIgnoreCase(movieName);
		if (opMovie.isPresent()) {
			Movies movie = opMovie.get();
			if (!movie.getShows().isEmpty()) {

				List<UserShowsDto> shows = new ArrayList<>();

				for (Shows show : movie.getShows()) {
					shows.add(new UserShowsDto(show.getHall().getHallDesc(), show.getMovie().getMovieName(),
							show.getSlotNo(), show.getFromDate(), show.getToDate()));
				}

				return shows;
			}
			throw new ShowNotFoundException(AppConstants.SHOWS_NOT_FOUND.replace("#", movieName));
		}
		throw new MovieNotFoundException(movieName + AppConstants.MOVIE_NOT_FOUND);
	}
}

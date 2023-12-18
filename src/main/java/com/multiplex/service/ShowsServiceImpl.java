package com.multiplex.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.multiplex.dto.PublishShowDto;
import com.multiplex.dto.UserShowsDto;
import com.multiplex.entity.Booking;
import com.multiplex.entity.Hall;
import com.multiplex.entity.HallCapacity;
import com.multiplex.entity.Movies;
import com.multiplex.entity.ShowAvailability;
import com.multiplex.entity.Show;
import com.multiplex.entity.User;
import com.multiplex.exception.HallNotFoundException;
import com.multiplex.exception.InvalidSlotNumberException;
import com.multiplex.exception.MovieNotFoundException;
import com.multiplex.exception.ShowDeletionException;
import com.multiplex.exception.ShowNotFoundException;
import com.multiplex.exception.ShowOverlapException;
import com.multiplex.exception.UserNotFoundException;
import com.multiplex.repository.HallRepository;
import com.multiplex.repository.MovieRepository;
import com.multiplex.repository.ShowAvailabilityRepository;
import com.multiplex.repository.ShowsRepository;
import com.multiplex.repository.UserRepository;
import com.multiplex.util.AppConstants;

@Service
public class ShowsServiceImpl implements ShowsService {

	@Autowired
	ShowsRepository showsRepository;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	HallRepository hallRepository;

	@Autowired
	MovieRepository movieRepository;
	
	@Autowired
	ShowAvailabilityRepository showAvailabilityRepository;
	
	@Transactional(readOnly = false)
	public boolean addShow(PublishShowDto showDto) {
		Optional<User> opUser = userRepository.findById(showDto.getAdminId());
	    if (opUser.isEmpty() ||  opUser.get().getUserType() != 'A') throw new UserNotFoundException(AppConstants.ADMIN_NOT_FOUND);

	    Hall hall = hallRepository.findByHallDescIgnoreCase(showDto.getHallName())
	            .orElseThrow(() -> new HallNotFoundException(AppConstants.HALL_DESC_NOT_FOUND.replace("#", showDto.getHallName())));

	    if (showDto.getSlotNo() < 1 || showDto.getSlotNo() > 4) {
	        throw new InvalidSlotNumberException(AppConstants.INVALID_SLOT_NUMBER);
	    }

	    // Save or retrieve the movie
	    Optional<Movies> existingMovie = movieRepository.findByMovieNameIgnoreCase(showDto.getMovieName());
	    if (existingMovie.isEmpty())
	    	throw new MovieNotFoundException(showDto.getMovieName() + AppConstants.MOVIE_NOT_FOUND);

	    LocalDate fromDate = showDto.getFromDate();
	    LocalDate toDate = showDto.getToDate();
	    List<Show> existingShows = showsRepository.findByHallAndSlotNoAndDates(hall, showDto.getSlotNo(), fromDate, toDate);
	    if (!existingShows.isEmpty()) {
	        throw new ShowOverlapException(AppConstants.SHOW_ALREADY_RUNNING.replace("1", showDto.getHallName()).replace("2", Integer.toString(showDto.getSlotNo())).replace("3", existingShows.get(existingShows.size() - 1).getToDate().toString()));
	    }

	    Show show = new Show(hall, existingMovie.get(), showDto.getSlotNo(), showDto.getFromDate(), showDto.getToDate());
	    show = showsRepository.save(show);

	    // Create ShowAvailability records
	    LocalDate currentDate = showDto.getFromDate();
	    List<ShowAvailability> showAvailabilities = new ArrayList<>();

	    while (!currentDate.isAfter(showDto.getToDate())) {
	        for (HallCapacity hallCapacity : hall.getHallCapacities()) {
	            ShowAvailability showAvailability = new ShowAvailability();
	            showAvailability.setShow(show);
	            showAvailability.setHall(hall);
	            showAvailability.setSeatType(hallCapacity.getSeatType());
	            showAvailability.setAvailabilityDate(currentDate);
	            showAvailability.setTotalSeats(hallCapacity.getSeatCount());
	            showAvailability.setRemainingSeats(hallCapacity.getSeatCount());

	            showAvailabilities.add(showAvailability);
	        }

	        currentDate = currentDate.plusDays(1); // Move to the next date
	    }

	    showAvailabilityRepository.saveAll(showAvailabilities);

	    return show != null;
	}

	@Transactional(readOnly = false)
	public boolean deleteShowById(int id) {
		Optional<Show> opShow = showsRepository.findById(id);

		if (opShow.isPresent()) {
			
			Show show = opShow.get();
			
			if(show.getBooking() != null && !show.getBooking().isEmpty()) {
				throw new ShowDeletionException(AppConstants.SHOW_DELECTION_ERROR);
			}
			
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

				for (Show show : movie.getShows()) {
					shows.add(new UserShowsDto(show.getHall().getHallDesc(), show.getMovie().getMovieName(),
							show.getSlotNo(), show.getFromDate(), show.getToDate()));
				}

				return shows;
			}
			throw new ShowNotFoundException(AppConstants.SHOWS_NOT_FOUND.replace("#", movieName));
		}
		throw new MovieNotFoundException(movieName + AppConstants.MOVIE_NOT_FOUND);
	}
	
	public UserShowsDto getShowById(int showId) {
		Optional<Show> opShow = showsRepository.findById(showId);
		if(opShow.isPresent()) {
			Show show = opShow.get();
			return new UserShowsDto(show.getHall().getHallDesc(), show.getMovie().getMovieName(),
					show.getSlotNo(), show.getFromDate(), show.getToDate());
		}
		throw new ShowNotFoundException(AppConstants.SHOWS_NOT_FOUND + showId);
	}
	
	public List<Booking> getAllBookingsByShowId(int showId) {
		Optional<Show> opShow = showsRepository.findById(showId);
		
		if(opShow.isPresent()) {
			return opShow.get().getBooking();
		}
		throw new ShowNotFoundException(AppConstants.SHOW_ID_NOT_FOUND.replace("#", Integer.toString(showId)));
	}
	
}

package com.multiplex.servicetest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.multiplex.dto.PublishShowDto;
import com.multiplex.dto.UserShowsDto;
import com.multiplex.entity.Booking;
import com.multiplex.entity.Hall;
import com.multiplex.entity.Movies;
import com.multiplex.entity.Show;
import com.multiplex.entity.ShowAvailability;
import com.multiplex.entity.User;
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
import com.multiplex.service.ShowsServiceImpl;

@SpringBootTest
class ShowServiceTest {

	@Mock
	private ShowsRepository showsRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private HallRepository hallRepository;

	@Mock
	private MovieRepository movieRepository;

	@Mock
	private ShowAvailabilityRepository showAvailabilityRepository;

	@InjectMocks
	private ShowsServiceImpl showsService;

	@Test
	void addShowTest() {
		Hall hall = createHall("TestHall", 100);
		Movies movie = createMovie("TestMovie", "Action");
		PublishShowDto showDto = createPublishShowDto("TestHall", "TestMovie", 1, LocalDate.now(),
				LocalDate.now().plusDays(7));
		when(userRepository.findById(showDto.getAdminId())).thenReturn(Optional.of(createUser('A')));
		when(hallRepository.findByHallDescIgnoreCase(showDto.getHallName())).thenReturn(Optional.of(hall));
		when(movieRepository.findByMovieNameIgnoreCase(showDto.getMovieName())).thenReturn(Optional.of(movie));
		when(showsRepository.findByHallAndSlotNoAndDates(hall, showDto.getSlotNo(), showDto.getFromDate(),
				showDto.getToDate())).thenReturn(new ArrayList<>());
		when(showsRepository.save(any()))
				.thenReturn(createShow(hall, movie, 2, LocalDate.of(2023, 12, 19), LocalDate.of(2023, 12, 30)));
		boolean result = showsService.addShow(showDto);

		assertTrue(result);
	}

	@Test
	void addShowInvalidSlotNumberExceptionFirstCaseTest() {
		Hall hall = createHall("TestHall", 100);
		Movies movie = createMovie("TestMovie", "Action");
		PublishShowDto showDto = createPublishShowDto("TestHall", "TestMovie", 5, LocalDate.now(),
				LocalDate.now().plusDays(7));
		when(userRepository.findById(showDto.getAdminId())).thenReturn(Optional.of(createUser('A')));
		when(hallRepository.findByHallDescIgnoreCase(showDto.getHallName())).thenReturn(Optional.of(hall));
		when(movieRepository.findByMovieNameIgnoreCase(showDto.getMovieName())).thenReturn(Optional.of(movie));

		assertThrows(InvalidSlotNumberException.class, () -> showsService.addShow(showDto));
	}

	@Test
	void addShowInvalidSlotNumberExceptionAnotherCaseTest() {
		Hall hall = createHall("TestHall", 100);
		Movies movie = createMovie("TestMovie", "Action");
		PublishShowDto showDto = createPublishShowDto("TestHall", "TestMovie", -1, LocalDate.now(),
				LocalDate.now().plusDays(7));
		when(userRepository.findById(showDto.getAdminId())).thenReturn(Optional.of(createUser('A')));
		when(hallRepository.findByHallDescIgnoreCase(showDto.getHallName())).thenReturn(Optional.of(hall));
		when(movieRepository.findByMovieNameIgnoreCase(showDto.getMovieName())).thenReturn(Optional.of(movie));

		assertThrows(InvalidSlotNumberException.class, () -> showsService.addShow(showDto));
	}

	@Test
	void addShowFailureTest() {
		Hall hall = createHall("TestHall", 100);
		Movies movie = createMovie("TestMovie", "Action");
		PublishShowDto showDto = createPublishShowDto("TestHall", "TestMovie", 1, LocalDate.now(),
				LocalDate.now().plusDays(7));
		when(userRepository.findById(showDto.getAdminId())).thenReturn(Optional.of(createUser('A')));
		when(hallRepository.findByHallDescIgnoreCase(showDto.getHallName())).thenReturn(Optional.of(hall));
		when(movieRepository.findByMovieNameIgnoreCase(showDto.getMovieName())).thenReturn(Optional.of(movie));
		when(showsRepository.findByHallAndSlotNoAndDates(hall, showDto.getSlotNo(), showDto.getFromDate(),
				showDto.getToDate())).thenReturn(new ArrayList<>());

		boolean result = showsService.addShow(showDto);

		assertFalse(result);
	}

	@Test
	void addShowUserNotFoundExceptionTest() {
		PublishShowDto showDto = createPublishShowDto("TestHall", "TestMovie", 1, LocalDate.now(),
				LocalDate.now().plusDays(7));
		when(userRepository.findById(showDto.getAdminId())).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> showsService.addShow(showDto));
	}


	@Test
	void deleteShowByIdTest() {
		int showId = 1;
		Show show = createShow(createHall("TestHall", 100), createMovie("TestMovie", "Action"), 1, LocalDate.now(),
				LocalDate.now().plusDays(7));
		when(showsRepository.findById(showId)).thenReturn(Optional.of(show));
		when(showsRepository.save(show)).thenReturn(show);

		boolean result = showsService.deleteShowById(showId);

		assertTrue(result);
	}

	@Test
	void deleteShowByIdShowNotFoundExceptionTest() {
		int showId = 1;
		when(showsRepository.findById(showId)).thenReturn(Optional.empty());

		assertThrows(ShowNotFoundException.class, () -> showsService.deleteShowById(showId));
	}

	@Test
	void deleteShowByIdShowDeletionExceptionTest() {
		int showId = 1;
		Show show = createShow(createHall("TestHall", 100), createMovie("TestMovie", "Action"), 1, LocalDate.now(),
				LocalDate.now().plusDays(7));
		show.getBooking().add(createBooking(show));
		when(showsRepository.findById(showId)).thenReturn(Optional.of(show));

		assertThrows(ShowDeletionException.class, () -> showsService.deleteShowById(showId));
	}

	@Test
	void getAllShowsByMovieNameTest() {
		String movieName = "TestMovie";
		Movies movie = createMovie(movieName, "Action");
		Show show = createShow(createHall("TestHall", 100), movie, 1, LocalDate.now(), LocalDate.now().plusDays(7));
		movie.setShows(Arrays.asList(show));
		List<UserShowsDto> expectedShows = new ArrayList<>();
		expectedShows.add(new UserShowsDto("TestHall", movieName, 1, LocalDate.now(), LocalDate.now().plusDays(7)));
		when(movieRepository.findByMovieNameIgnoreCase(movieName)).thenReturn(Optional.of(movie));
		when(showsRepository.findByMovieName(movie.getMovieName())).thenReturn(List.of(show));

		List<UserShowsDto> result = showsService.getAllShowsByMovieName(movieName);

		assertEquals(expectedShows.toString(), result.toString());
	}

	@Test
	void getAllShowsByMovieNameMovieNotFoundExceptionTest() {
		String movieName = "NonExistentMovie";
		when(movieRepository.findByMovieNameIgnoreCase(movieName)).thenReturn(Optional.empty());

		assertThrows(MovieNotFoundException.class, () -> showsService.getAllShowsByMovieName(movieName));
	}

	@Test
	void addShowMovieNotFoundExceptionTest() {
		Hall hall = createHall("TestHall", 100);
		PublishShowDto showDto = createPublishShowDto("TestHall", "NonExistentMovie", 2, LocalDate.now(),
				LocalDate.now().plusDays(7));
		when(userRepository.findById(showDto.getAdminId())).thenReturn(Optional.of(createUser('A')));
		when(hallRepository.findByHallDescIgnoreCase(showDto.getHallName())).thenReturn(Optional.of(hall));
		when(movieRepository.findByMovieNameIgnoreCase(showDto.getMovieName())).thenReturn(Optional.empty());

		assertThrows(MovieNotFoundException.class, () -> showsService.addShow(showDto));
	}

	@Test
	void addShowShowOverlapExceptionTest() {
		Hall hall = createHall("TestHall", 100);
		Movies movie = createMovie("TestMovie", "Action");
		Show existingShow = createShow(hall, movie, 2, LocalDate.now(), LocalDate.now().plusDays(7));
		PublishShowDto showDto = createPublishShowDto("TestHall", "TestMovie", 2, LocalDate.now(),
				LocalDate.now().plusDays(7));
		when(userRepository.findById(showDto.getAdminId())).thenReturn(Optional.of(createUser('A')));
		when(hallRepository.findByHallDescIgnoreCase(showDto.getHallName())).thenReturn(Optional.of(hall));
		when(movieRepository.findByMovieNameIgnoreCase(showDto.getMovieName())).thenReturn(Optional.of(movie));
		when(showsRepository.findByHallAndSlotNoAndDates(hall, showDto.getSlotNo(), showDto.getFromDate(),
				showDto.getToDate())).thenReturn(List.of(existingShow));

		assertThrows(ShowOverlapException.class, () -> showsService.addShow(showDto));
	}

	@Test
	void addShowCreateShowAvailabilitiesTest() {
		Hall hall = createHall("TestHall", 100);
		Movies movie = createMovie("TestMovie", "Action");
		PublishShowDto showDto = createPublishShowDto("TestHall", "TestMovie", 2, LocalDate.now(),
				LocalDate.now().plusDays(7));
		when(userRepository.findById(showDto.getAdminId())).thenReturn(Optional.of(createUser('A')));
		when(hallRepository.findByHallDescIgnoreCase(showDto.getHallName())).thenReturn(Optional.of(hall));
		when(movieRepository.findByMovieNameIgnoreCase(showDto.getMovieName())).thenReturn(Optional.of(movie));
		when(showsRepository.findByHallAndSlotNoAndDates(hall, showDto.getSlotNo(), showDto.getFromDate(),
				showDto.getToDate())).thenReturn(Collections.emptyList());

		showsService.addShow(showDto);

		verify(showAvailabilityRepository, times(hall.getHallCapacities().size())).save(any(ShowAvailability.class));
	}

	@Test
	void getAllShowsByMovieNameNoShowsForMovieExceptionTest() {
		String movieName = "NonExistentMovie";
		when(movieRepository.findByMovieNameIgnoreCase(movieName))
				.thenReturn(Optional.of(createMovie(movieName, "Action")));

		assertThrows(ShowNotFoundException.class, () -> showsService.getAllShowsByMovieName(movieName));
	}

	@Test
	void getShowByIdShowExistsExceptionTest() {
		int showId = 1;
		when(showsRepository.findById(showId)).thenReturn(Optional.of(createShow()));

		UserShowsDto result = showsService.getShowById(showId);

		assertNotNull(result);
	}

	@Test
	void getShowByIdShowNotFoundExceptionTest() {
		int showId = 1;
		when(showsRepository.findById(showId)).thenReturn(Optional.empty());

		assertThrows(ShowNotFoundException.class, () -> showsService.getShowById(showId));
	}

	@Test
	void getAllBookingsByShowIdShowExistsWithBookingsExceptionTest() {
		int showId = 1;
		Show show = createShow();
		show.setBooking(new ArrayList<>());
		show.getBooking().add(createBooking()); 
		when(showsRepository.findById(showId)).thenReturn(Optional.of(show));

		List<Booking> result = showsService.getAllBookingsByShowId(showId);

		assertNotNull(result);
		assertFalse(result.isEmpty());
	}

	@Test
	void getAllBookingsByShowIdShowExistsNoBookingsExceptionTest() {
		int showId = 1;
		Show show = createShow();
		show.setBooking(new ArrayList<>());
		when(showsRepository.findById(showId)).thenReturn(Optional.of(show));

		List<Booking> result = showsService.getAllBookingsByShowId(showId);

		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	@Test
	void getAllBookingsByShowIdShowNotFoundExceptionTest() {
		int showId = 1;
		when(showsRepository.findById(showId)).thenReturn(Optional.empty());

		assertThrows(ShowNotFoundException.class, () -> showsService.getAllBookingsByShowId(showId));
	}

	private Hall createHall(String hallDesc, int totalCapacity) {
		Hall hall = new Hall();
		hall.setHallDesc(hallDesc);
		hall.setTotalCapacity(totalCapacity);
		hall.setHallCapacities(new ArrayList<>());
		hall.setShows(new ArrayList<>());
		return hall;
	}

	private Movies createMovie(String movieName, String genre) {
		Movies movie = new Movies();
		movie.setMovieName(movieName);
		movie.setGenre(genre);
		movie.setShows(new ArrayList<>());
		return movie;
	}

	private Show createShow(Hall hall, Movies movie, int slotNo, LocalDate fromDate, LocalDate toDate) {
		Show show = new Show();
		show.setHall(hall);
		show.setMovie(movie);
		show.setSlotNo(slotNo);
		show.setFromDate(fromDate);
		show.setToDate(toDate);
		show.setShowAvailabilities(new ArrayList<>());
		show.setBooking(new ArrayList<>());
		return show;
	}

	private User createUser(char userType) {
		User user = new User();
		user.setUserType(userType);
		user.setBooking(new ArrayList<>());
		return user;
	}

	private PublishShowDto createPublishShowDto(String hallName, String movieName, int slotNo, LocalDate fromDate,
			LocalDate toDate) {
		PublishShowDto showDto = new PublishShowDto();
		showDto.setHallName(hallName);
		showDto.setMovieName(movieName);
		showDto.setSlotNo(slotNo);
		showDto.setFromDate(fromDate);
		showDto.setToDate(toDate);
		return showDto;
	}

	private Booking createBooking(Show show) {
		Booking booking = new Booking();
		booking.setShows(show);
		booking.setBookingDetails(new ArrayList<>());
		return booking;
	}

	private Hall createHall() {
		Hall hall = new Hall();
		hall.setHallId(1);
		hall.setHallDesc("TestHall");
		return hall;
	}

	private Show createShow() {
		Show show = new Show();
		Hall hall = createHall();
		Movies movie = createMovie();

		show.setHall(hall);
		show.setMovie(movie);
		show.setSlotNo(1); 
		show.setFromDate(LocalDate.now());
		show.setToDate(LocalDate.now().plusDays(7));

		return show;
	}

	private Movies createMovie() {
		Movies movie = new Movies();
		movie.setMovieName("TestMovie");
		movie.setGenre("Action");
		return movie;
	}

	private Booking createBooking() {
		Booking booking = new Booking();
		User user = createUser();
		Show show = createShow();

		booking.setUser(user);
		booking.setShows(show);
		booking.setBookedDate(LocalDateTime.now());

		return booking;
	}

	private User createUser() {
		User user = new User();
		user.setUserName("TestUser");
		return user;
	}
}

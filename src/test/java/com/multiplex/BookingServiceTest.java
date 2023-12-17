package com.multiplex;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.multiplex.dto.BookingDto;
import com.multiplex.dto.CancelDto;
import com.multiplex.dto.UserBookingDto;
import com.multiplex.embedded.BookingDetailsId;
import com.multiplex.entity.Booking;
import com.multiplex.entity.BookingDetails;
import com.multiplex.entity.Earnings;
import com.multiplex.entity.Hall;
import com.multiplex.entity.Movies;
import com.multiplex.entity.SeatType;
import com.multiplex.entity.Show;
import com.multiplex.entity.ShowAvailability;
import com.multiplex.entity.User;
import com.multiplex.exception.BookingException;
import com.multiplex.exception.BookingNotFoundException;
import com.multiplex.exception.CancelBookingException;
import com.multiplex.exception.InvalidShowException;
import com.multiplex.exception.InvalidUserException;
import com.multiplex.exception.SeatNotAvailableException;
import com.multiplex.exception.ShowNotFoundException;
import com.multiplex.exception.UserNotFoundException;
import com.multiplex.repository.BookingDetailsRepository;
import com.multiplex.repository.BookingRepository;
import com.multiplex.repository.EarningsRepository;
import com.multiplex.repository.SeatTypeRepository;
import com.multiplex.repository.ShowAvailabilityRepository;
import com.multiplex.repository.ShowsRepository;
import com.multiplex.repository.UserRepository;
import com.multiplex.service.BookingServiceImpl;
import com.multiplex.util.AppConstants;

@SpringBootTest
class BookingServiceTest {

	@Mock
	private BookingRepository bookingRepository;

	@Mock
	private BookingDetailsRepository bookingDetailsRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private ShowsRepository showsRepository;

	@Mock
	private SeatTypeRepository seatTypeRepository;

	@Mock
	private ShowAvailabilityRepository showAvailabilityRepository;

	@Mock
	private EarningsRepository earningsRepository;

	@InjectMocks
	private BookingServiceImpl bookingService;

	@Test
	void testBookTickets_SuccessfulBooking() {
		User mockUser = new User();
		mockUser.setUserName("user");
		when(userRepository.findByEmailIdIgnoreCase("user@example.com")).thenReturn(Optional.of(mockUser));

		Show mockShow = new Show();
		Hall hall = new Hall(1, "hall 1", 100);
		Movies movie = new Movies(1, "inception", "Sci-fi");
		mockShow.setHall(hall);
		mockShow.setMovie(movie);
		mockShow.setSlotNo(1);

		mockShow.setFromDate(LocalDate.now().minusDays(1));
		mockShow.setToDate(LocalDate.now().plusDays(1));
		when(showsRepository.findById(1)).thenReturn(Optional.of(mockShow));
		when(bookingRepository.save(Mockito.any(Booking.class))).thenAnswer(invocation -> {
			Booking savedBooking = invocation.getArgument(0);
			savedBooking.setBookingId(123);
			return savedBooking;
		});

		BookingDto bookingDto = new BookingDto();
		bookingDto.setUserEmail("user@example.com");
		bookingDto.setShowId(1);
		bookingDto.setBookingDate(LocalDate.now());

		Map<String, Integer> selectedSeatTypeCounts = new HashMap<>();
		selectedSeatTypeCounts.put("VIP", 2);
		bookingDto.setSelectedSeatType(selectedSeatTypeCounts);

		UserBookingDto userBookingDto = bookingService.bookTickets(bookingDto);

		assertEquals("user", userBookingDto.getUserName());
		assertEquals("inception", userBookingDto.getMovieName());
	}

	@Test
	void testBookTickets_BookingDetailsSaved() {
		BookingDto bookingDto = createBookingDto();
		User user = new User();
		Show show = createShow();
		Hall hall = new Hall(1, "hall 1", 100);
		Movies movie = new Movies(1, "inception", "sci-fi");
		show.setHall(hall);
		show.setMovie(movie);
		SeatType seatType = createSeatType();
		ShowAvailability showAvailability = createShowAvailability(seatType, show);

		when(userRepository.findByEmailIdIgnoreCase(anyString())).thenReturn(java.util.Optional.of(user));
		when(showsRepository.findById(anyInt())).thenReturn(java.util.Optional.of(show));
		when(seatTypeRepository.findAllBySeatTypeDescIgnoreCaseIn(any())).thenReturn(List.of(seatType));
		when(showAvailabilityRepository.findByShowAndHallAndSeatTypeAndAvailabilityDate(any(), any(), any(), any()))
				.thenReturn(java.util.Optional.of(showAvailability));
		when(bookingRepository.save(any())).thenReturn(createBooking());
		when(bookingDetailsRepository.save(any())).thenReturn(new BookingDetails());

		bookingService.bookTickets(bookingDto);

		verify(userRepository, times(1)).findByEmailIdIgnoreCase(anyString());
		verify(showsRepository, times(1)).findById(anyInt());
		verify(seatTypeRepository, times(1)).findAllBySeatTypeDescIgnoreCaseIn(any());
		verify(showAvailabilityRepository, times(1)).findByShowAndHallAndSeatTypeAndAvailabilityDate(any(), any(),
				any(), any());
		verify(bookingRepository, times(1)).save(any());
		verify(bookingDetailsRepository, times(1)).save(any());

	}

	@Test
	void testBookTickets_SuccessfulBookingWithLessAvailableSeats() {
		User user = new User();
		user.setEmailId("user@example.com");
		user.setUserName("TestUser");

		Show show = new Show();
		show.setMovie(new Movies());
		show.setHall(new Hall());
		show.setFromDate(LocalDate.now().minusDays(1));
		show.setToDate(LocalDate.now().plusDays(1));
		show.setSlotNo(1);

		SeatType seatType = new SeatType();
		seatType.setSeatTypeId(1);
		seatType.setSeatTypeDesc("Standard");
		seatType.setSeatFare(10.0f);

		ShowAvailability showAvailability = new ShowAvailability();
		showAvailability.setRemainingSeats(10);
		showAvailability.setShow(show);
		showAvailability.setHall(show.getHall());
		showAvailability.setSeatType(seatType);
		showAvailability.setAvailabilityDate(LocalDate.now());

		BookingDto bookingDto = new BookingDto();
		bookingDto.setUserEmail("user@example.com");
		bookingDto.setShowId(1);
		bookingDto.setBookingDate(LocalDate.now());
		bookingDto.setSelectedSeatType(Collections.singletonMap("Standard", 12));

		when(userRepository.findByEmailIdIgnoreCase(anyString())).thenReturn(Optional.of(user));
		when(showsRepository.findById(anyInt())).thenReturn(Optional.of(show));
		when(seatTypeRepository.findAllBySeatTypeDescIgnoreCaseIn(any()))
				.thenReturn(Collections.singletonList(seatType));
		when(showAvailabilityRepository.findByShowAndHallAndSeatTypeAndAvailabilityDate(any(), any(), any(), any()))
				.thenReturn(Optional.of(showAvailability));
		when(bookingRepository.save(Mockito.any(Booking.class))).thenAnswer(invocation -> {
			Booking savedBooking = invocation.getArgument(0);
			savedBooking.setBookingId(123);
			return savedBooking;
		});

		assertThrows(SeatNotAvailableException.class, () -> bookingService.bookTickets(bookingDto));
	}

	@Test
	void testBookTickets_UserNotFound() {
		BookingDto bookingDto = createBookingDto();

		when(userRepository.findByEmailIdIgnoreCase(any())).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> bookingService.bookTickets(bookingDto));
	}

	@Test
	void testBookTickets_ShowNotFound() {
		BookingDto bookingDto = createBookingDto();
		User user = createUser();

		when(userRepository.findByEmailIdIgnoreCase(any())).thenReturn(Optional.of(user));
		when(showsRepository.findById(any())).thenReturn(Optional.empty());

		assertThrows(ShowNotFoundException.class, () -> bookingService.bookTickets(bookingDto));
	}

	@Test
	void testBookTickets_InvalidShowDate() {
		BookingDto bookingDto = createBookingDto();
		User user = createUser();
		Show show = createShow();
		show.setFromDate(LocalDate.now());

		bookingDto.setBookingDate(LocalDate.now().minusDays(1));

		when(userRepository.findByEmailIdIgnoreCase(any())).thenReturn(Optional.of(user));
		when(showsRepository.findById(any())).thenReturn(Optional.of(show));

		assertThrows(InvalidShowException.class, () -> bookingService.bookTickets(bookingDto));
	}

	@Test
	void testBookTickets_SeatNotAvailable() {
		BookingDto bookingDto = createBookingDto();
		User user = createUser();
		Show show = createShow();
		SeatType seatType = createSeatType();

		when(userRepository.findByEmailIdIgnoreCase(any())).thenReturn(Optional.of(user));
		when(showsRepository.findById(any())).thenReturn(Optional.of(show));
		when(seatTypeRepository.findAllBySeatTypeDescIgnoreCaseIn(any())).thenReturn(List.of(seatType));
		when(showAvailabilityRepository.findByShowAndHallAndSeatTypeAndAvailabilityDate(any(), any(), any(), any()))
				.thenReturn(
						Optional.of(new ShowAvailability(1, show, show.getHall(), seatType, LocalDate.now(), 0, 0)));

		assertThrows(SeatNotAvailableException.class, () -> bookingService.bookTickets(bookingDto));
	}

	@Test
	void testCancelBooking_SuccessfulCancellation() {
		CancelDto cancelDto = createCancelDto();
		User user = createUser();
		Show show = createShow();
		show.setHall(new Hall(1, "hall 1", 120));
		Booking booking = createBooking(user, show);
		booking.setShowDate(LocalDate.now().plusDays(3));

		when(bookingRepository.findById(any())).thenReturn(Optional.of(booking));
		when(userRepository.findByEmailIdIgnoreCase(any())).thenReturn(Optional.of(user));
		when(showAvailabilityRepository.findByShowAndHallAndSeatTypeAndAvailabilityDate(any(), any(), any(), any()))
				.thenReturn(Optional.of(new ShowAvailability()));
		Earnings earnings = createEarnings();
		when(earningsRepository.findByBookingId(anyInt())).thenReturn(Optional.of(earnings));

		UserBookingDto userBookingDto = bookingService.cancelBooking(cancelDto);

		assertNotNull(userBookingDto);
		assertEquals("cancelled", userBookingDto.getBookingStatus());
	}

	@Test
	void testCancelBooking_BookingNotFound() {
		CancelDto cancelDto = createCancelDto();

		when(bookingRepository.findById(any())).thenReturn(Optional.empty());

		assertThrows(BookingNotFoundException.class, () -> bookingService.cancelBooking(cancelDto));
	}

	@Test
	void testCancelBooking_InvalidUser() {
		CancelDto cancelDto = createCancelDto();
		Booking booking = createBooking(createUser(), createShow());
		User user = createUser();
		user.setEmailId("anotherUser@example.com");
		booking.setUser(user);

		when(bookingRepository.findById(any())).thenReturn(Optional.of(booking));

		assertThrows(InvalidUserException.class, () -> bookingService.cancelBooking(cancelDto));
	}

	@Test
	void testCancelBooking_CancellationWithinTwoDays() {
		CancelDto cancelDto = createCancelDto();
		Booking booking = createBooking(createUser(), createShow());

		booking.setShowDate(LocalDate.now().plusDays(1));

		when(bookingRepository.findById(any())).thenReturn(Optional.of(booking));

		assertThrows(CancelBookingException.class, () -> bookingService.cancelBooking(cancelDto));
	}

	@Test
	void testBookTickets_NoSeatTypeSelected() {
		BookingDto bookingDTO = new BookingDto();
		bookingDTO.setUserEmail("user@example.com");
		bookingDTO.setShowId(1);
		bookingDTO.setBookingDate(LocalDate.now());

		Map<String, Integer> selectedSeatType = new HashMap<>();
		selectedSeatType.put("VIP", 0);
		selectedSeatType.put("Regular", 0);

		bookingDTO.setSelectedSeatType(selectedSeatType);

		when(userRepository.findByEmailIdIgnoreCase("user@example.com")).thenReturn(Optional.of(new User()));

		Show show = new Show();
		show.setFromDate(LocalDate.now().minusDays(1));
		show.setToDate(LocalDate.now().plusDays(1));
		when(showsRepository.findById(1)).thenReturn(Optional.of(show));

		when(seatTypeRepository.findAllBySeatTypeDescIgnoreCaseIn(any())).thenReturn(List.of(new SeatType()));

		when(showAvailabilityRepository.findByShowAndHallAndSeatTypeAndAvailabilityDate(any(), any(), any(), any()))
				.thenReturn(Optional.of(new ShowAvailability()));

		BookingException exception = assertThrows(BookingException.class, () -> bookingService.bookTickets(bookingDTO));
		assertEquals(AppConstants.NO_SEAT_TYPE, exception.getMessage());
	}

	@Test
	void testCancelBooking_ErrorOccured() {
		User user = new User();
		user.setEmailId("user@example.com");

		Movies movie = new Movies();
		movie.setMovieName("Sample Movie");

		Hall hall = new Hall();
		hall.setHallDesc("Sample Hall");

		Show show = new Show();
		show.setMovie(movie);
		show.setHall(hall);
		show.setSlotNo(1);
		show.setFromDate(LocalDate.now().plusDays(3));

		Booking canceledBooking = new Booking();
		canceledBooking.setBookingId(1);
		canceledBooking.setUser(user);
		canceledBooking.setShows(show);
		canceledBooking.setShowDate(LocalDate.now().plusDays(3));

		when(bookingRepository.findById(anyInt())).thenReturn(Optional.of(canceledBooking));
		when(bookingDetailsRepository.findByBooking(any(Booking.class))).thenReturn(new ArrayList<>());
		when(showAvailabilityRepository.findByShowAndHallAndSeatTypeAndAvailabilityDate(any(), any(), any(), any()))
				.thenReturn(Optional.of(new ShowAvailability()));

		doThrow(new RuntimeException("Simulated error")).when(earningsRepository).save(any(Earnings.class));

		CancelDto cancelDto = new CancelDto();
		cancelDto.setBookingId(1);
		cancelDto.setUserEmail("user@example.com");
		assertThrows(CancelBookingException.class, () -> bookingService.cancelBooking(cancelDto));
	}

	@Test
	void testCancelBooking_SuccessfulCancellationWithMockBookingId() {
		User user = new User();
		user.setEmailId("user@example.com");
		user.setUserName("John Doe");

		Movies movie = new Movies();
		movie.setMovieName("Sample Movie");

		Hall hall = new Hall();
		hall.setHallDesc("Sample Hall");

		Show show = new Show();
		show.setMovie(movie);
		show.setHall(hall);
		show.setSlotNo(1);
		show.setFromDate(LocalDate.now().plusDays(3));

		Booking canceledBooking = new Booking();
		canceledBooking.setBookingId(123);
		canceledBooking.setUser(user);
		canceledBooking.setShows(show);
		canceledBooking.setShowDate(LocalDate.now().plusDays(3));

		BookingDetails bookingDetails = new BookingDetails();
		bookingDetails.setNoOfSeats(2);
		bookingDetails.setSeatType(new SeatType(1, "normal", 100, new ArrayList<>(), new ArrayList<>()));

		List<BookingDetails> bookingDetailsList = new ArrayList<>();
		bookingDetailsList.add(bookingDetails);

		canceledBooking.setBookingDetails(bookingDetailsList);

		Earnings earnings = new Earnings();

		ShowAvailability showAvailability = new ShowAvailability();
		showAvailability.setRemainingSeats(10);

		when(bookingRepository.findById(anyInt())).thenReturn(Optional.of(canceledBooking));
		when(bookingDetailsRepository.findByBooking(any(Booking.class))).thenReturn(bookingDetailsList);
		when(showAvailabilityRepository.findByShowAndHallAndSeatTypeAndAvailabilityDate(any(), any(), any(), any()))
				.thenReturn(Optional.of(showAvailability));

		when(earningsRepository.findByBookingId(anyInt())).thenReturn(Optional.of(earnings));

		when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> {
			Booking savedBooking = invocation.getArgument(0);
			savedBooking.setBookingId(123);
			return savedBooking;
		});

		CancelDto cancelDto = new CancelDto();
		cancelDto.setBookingId(1);
		cancelDto.setUserEmail("user@example.com");
		UserBookingDto result = bookingService.cancelBooking(cancelDto);

		verify(bookingDetailsRepository, times(1)).deleteAll(bookingDetailsList);
		verify(bookingRepository, times(1)).delete(canceledBooking);
		verify(earningsRepository, times(1)).save(any(Earnings.class));
		verify(showAvailabilityRepository, times(1)).save(any(ShowAvailability.class));

		assertNotNull(result);
		assertEquals(123, result.getBookingId());
		assertEquals("John Doe", result.getUserName());
		assertEquals("Sample Movie", result.getMovieName());
		assertEquals("Sample Hall", result.getHallDesc());
		assertEquals(1, result.getSlotNo());
	}

	// Utility methods

	public User createUser() {
		User user = new User();
		user.setUserId(1);
		user.setUserName("TestUser");
		user.setEmailId("testuser@example.com");

		return user;
	}

	public Show createShow() {
		Show show = new Show();
		show.setShowId(1);
		show.setFromDate(LocalDate.of(2023, 12, 10));
		show.setToDate(LocalDate.of(2023, 12, 28));
		show.setMovie(new Movies(1, "inception", "sci-fi"));

		return show;
	}

	public SeatType createSeatType() {
		SeatType seatType = new SeatType();
		seatType.setSeatTypeId(1);
		seatType.setSeatTypeDesc("Regular");
		seatType.setSeatFare(10.0f);

		return seatType;
	}

	public Booking createBooking(User user, Show show) {
		Booking booking = new Booking();
		booking.setBookingId(1);
		booking.setBookedDate(LocalDateTime.now());
		booking.setShowDate(LocalDate.now());
		booking.setUser(user);
		booking.setShows(show);
		booking.setBookingDetails(new ArrayList<>());

		return booking;
	}

	public BookingDetails createBookingDetails(Booking booking, SeatType seatType) {
		BookingDetails bookingDetails = new BookingDetails();
		BookingDetailsId bookingDetailsId = new BookingDetailsId(booking.getBookingId(), seatType.getSeatTypeId());
		bookingDetails.setId(bookingDetailsId);
		bookingDetails.setNoOfSeats(2);
		bookingDetails.setBooking(booking);
		bookingDetails.setSeatType(seatType);

		return bookingDetails;
	}

	public BookingDto createBookingDto() {
		BookingDto bookingDto = new BookingDto();
		bookingDto.setUserEmail("testuser@example.com");
		bookingDto.setShowId(1);
		bookingDto.setBookingDate(LocalDate.now());
		Map<String, Integer> selectedSeatType = new HashMap<>();
		selectedSeatType.put("Regular", 2);
		bookingDto.setSelectedSeatType(selectedSeatType);

		return bookingDto;
	}

	public CancelDto createCancelDto() {
		CancelDto cancelDto = new CancelDto();
		cancelDto.setBookingId(1);
		cancelDto.setUserEmail("testuser@example.com");

		return cancelDto;
	}

	public UserBookingDto createUserBookingDto() {
		UserBookingDto userBookingDto = new UserBookingDto();
		userBookingDto.setBookingId(1);
		userBookingDto.setUserName("TestUser");
		userBookingDto.setMovieName("TestMovie");
		userBookingDto.setBookingDate(LocalDate.now().toString());
		userBookingDto.setHallDesc("TestHall");
		userBookingDto.setSlotNo(1);
		Map<String, Integer> selectedSeats = new HashMap<>();
		selectedSeats.put("Regular", 2);
		userBookingDto.setSelectedSeats(selectedSeats);
		userBookingDto.setShowDate(LocalDate.now());
		userBookingDto.setBookingTotal(20.0);
		userBookingDto.setCancellationCharges(0.0);
		userBookingDto.setBookingStatus("Booked");

		return userBookingDto;
	}

	public Earnings createEarnings() {
		Earnings earning = new Earnings();

		earning.setBookingDate(LocalDate.now());
		earning.setBookingId(1);
		earning.setId(1);
		earning.setSeatsBooked(10);
		earning.setStatus("booked");
		earning.setTotalBookingCost(1000);

		return earning;
	}

	private ShowAvailability createShowAvailability(SeatType seatType, Show show) {
		ShowAvailability showAvailability = new ShowAvailability();
		showAvailability.setRemainingSeats(10);
		showAvailability.setSeatType(seatType);
		showAvailability.setShow(show);

		return showAvailability;
	}

	private Booking createBooking() {
		Booking booking = new Booking();
		booking.setBookingId(1);
		booking.setBookedDate(LocalDateTime.now());
		booking.setShowDate(LocalDate.now());

		User user = new User();
		user.setUserId(1);
		user.setUserName("John Doe");

		Show show = new Show();
		show.setShowId(1);

		Hall hall = new Hall(1, "hall 1", 100);
		Movies movie = new Movies(1, "inception", "sci-fi");

		show.setHall(hall);
		show.setMovie(movie);
		booking.setUser(user);
		booking.setShows(show);

		return booking;
	}
}

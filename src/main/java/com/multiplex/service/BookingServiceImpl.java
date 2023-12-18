package com.multiplex.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.multiplex.dto.BookingDto;
import com.multiplex.dto.CancelDto;
import com.multiplex.dto.UserBookingDto;
import com.multiplex.embedded.BookingDetailsId;
import com.multiplex.entity.Booking;
import com.multiplex.entity.BookingDetails;
import com.multiplex.entity.Earnings;
import com.multiplex.entity.SeatType;
import com.multiplex.entity.ShowAvailability;
import com.multiplex.entity.Show;
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
import com.multiplex.util.AppConstants;

@Service
public class BookingServiceImpl implements BookingService {
	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private BookingDetailsRepository bookingDetailsRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ShowsRepository showsRepository;

	@Autowired
	private SeatTypeRepository seatTypeRepository;

	@Autowired
	private ShowAvailabilityRepository showAvailabilityRepository;

	@Autowired
	private EarningsRepository earningsRepository;

	@Transactional
	public UserBookingDto bookTickets(BookingDto bookingDTO) {
		
		// Checking if the user is a registered user or not
		User user = userRepository.findByEmailIdIgnoreCase(bookingDTO.getUserEmail())
				.orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_REGISTERED));

		// Find the show based on its Id
		Show show = showsRepository.findById(bookingDTO.getShowId())
				.orElseThrow(() -> new ShowNotFoundException(AppConstants.SHOW_ID_NOT_FOUND + bookingDTO.getShowId()));

		// The date user want to book his show
		LocalDate bookingDate = bookingDTO.getBookingDate();

		if (show.getFromDate() == null || bookingDate.isBefore(show.getFromDate()) || bookingDate.isAfter(show.getToDate()))
			throw new InvalidShowException(AppConstants.NO_SHOW_RUNNING.replace("date",
					bookingDTO.getBookingDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));

		// Seat types which are selected by the user
		Map<String, Integer> selectedSeatTypeCounts = bookingDTO.getSelectedSeatType();

		if (selectedSeatTypeCounts.values().stream().noneMatch(i -> i > 0))
			throw new BookingException(AppConstants.NO_SEAT_TYPE);
		
		Booking booking = new Booking();
		booking.setBookedDate(LocalDateTime.now());
		booking.setShowDate(bookingDate);
		booking.setUser(user);
		booking.setShows(show);

		// Save the booking to get the ID
		booking = bookingRepository.save(booking);
		double totalCost = 0;

		// Fetch all the valid seats 
		List<SeatType> selectedSeatTypes = seatTypeRepository
				.findAllBySeatTypeDescIgnoreCaseIn(selectedSeatTypeCounts.keySet());

		// For each seat type selected by the user book the seats and decrease the available seat count
		for (SeatType selectedSeatType : selectedSeatTypes) {
			
			// Here we are finding the seat availability chart by checking the remaining seats with show id, hall id, selected seat type, booking date which user wants
			ShowAvailability showAvailability =  showAvailabilityRepository
					.findByShowAndHallAndSeatTypeAndAvailabilityDate(show, show.getHall(), selectedSeatType, bookingDate)
					.orElseThrow(() -> new ShowNotFoundException(AppConstants.NO_SHOW_AVAILABILITY));
			int remainingSeats = showAvailability.getRemainingSeats();
			int numberOfSeats = selectedSeatTypeCounts.get(selectedSeatType.getSeatTypeDesc());

			// If the remaining seats are more than the required seats we book the ticket for the user
			if (remainingSeats >= numberOfSeats) {
				BookingDetails bookingDetails = new BookingDetails();
				BookingDetailsId bookingDetailsId = new BookingDetailsId(booking.getBookingId(),
						selectedSeatType.getSeatTypeId());
				bookingDetails.setId(bookingDetailsId);
				bookingDetails.setNoOfSeats(numberOfSeats);
				bookingDetails.setBooking(booking);
				bookingDetails.setSeatType(selectedSeatType);

				bookingDetailsRepository.save(bookingDetails);

				showAvailability.setRemainingSeats(remainingSeats - numberOfSeats);
				showAvailabilityRepository.save(showAvailability);

				totalCost += selectedSeatType.getSeatFare() * numberOfSeats;

			} else { 
				// As we are aborting the booking delete the booking record from the db
				bookingRepository.delete(booking);
				
				// If the remaining seats are less than required seats inform user if he/she wants to booking the available seats
				if (remainingSeats > 0) {
					throw new SeatNotAvailableException(AppConstants.ALL_SEATS_NOT_AVAILABLE.replace("#",
							Integer.toString(remainingSeats) + " " + selectedSeatType.getSeatTypeDesc()));
				} else { 
					// if the seats are not available send on seats available exception
//					bookingRepository.delete(booking);
					throw new SeatNotAvailableException(
							AppConstants.INSUFFICIENT_SEATS_FOR_SEAT_TYPE + selectedSeatType.getSeatTypeDesc());
				}
			}
		}

		// DTO class to send user all the required booking info
		int noOfTotalSeats = selectedSeatTypeCounts.values().stream().mapToInt(a -> a).sum();
		earningsRepository
				.save(new Earnings(booking.getBookingId(), "booked", totalCost, noOfTotalSeats, LocalDate.now()));
		UserBookingDto userBookingDto = new UserBookingDto();
		userBookingDto.setBookingId(booking.getBookingId());
		userBookingDto.setUserName(user.getUserName());
		userBookingDto.setMovieName(show.getMovie().getMovieName());
		userBookingDto.setBookingDate(LocalDate.now().toString());
		userBookingDto.setHallDesc(show.getHall().getHallDesc());
		userBookingDto.setSlotNo(show.getSlotNo());
		userBookingDto.setSelectedSeats(selectedSeatTypeCounts);
		userBookingDto.setShowDate(bookingDate);
		userBookingDto.setBookingTotal(totalCost);
		userBookingDto.setCancellationCharges(0D);
		userBookingDto.setBookingStatus("Booked");

		return userBookingDto;
	}

	@Transactional
	public UserBookingDto cancelBooking(CancelDto cancelDto) {
		Booking viewBooking = bookingRepository.findById(cancelDto.getBookingId()).orElseThrow(
				() -> new BookingNotFoundException(AppConstants.BOOKING_NOT_FOUND_WITH_ID + cancelDto.getBookingId()));

		if (!viewBooking.getUser().getEmailId().equalsIgnoreCase(cancelDto.getUserEmail())) {
			throw new InvalidUserException(AppConstants.NOT_VALID_USER);
		}

		LocalDate showDate = viewBooking.getShowDate();
		LocalDate currentDate = LocalDate.now();
		if (showDate.isBefore(currentDate.plusDays(2))) {
			throw new CancelBookingException("Cannot cancel booking within 2 days of the show");
		}

		List<BookingDetails> bookingDetailsList = bookingDetailsRepository.findByBooking(viewBooking);

		bookingDetailsRepository.deleteAll(bookingDetailsList);

		bookingRepository.delete(viewBooking);

		Optional<Earnings> opBookingEarnings = earningsRepository.findByBookingId(cancelDto.getBookingId());
		if (opBookingEarnings.isPresent()) {
			Earnings bookingEarnings = opBookingEarnings.get();
			bookingEarnings.setStatus("cancelled");
			earningsRepository.save(bookingEarnings);
		} else {
			throw new CancelBookingException("An error occured. Please try again!");
		}
		
		// ------- INCREASING THE SEAT COUNT ------------

		Map<String, Integer> seatCounts = new HashMap<>();
		double totalCost = 0;
		double cancellationCharges = 0;

		Show show = viewBooking.getShows();
		bookingDetailsList = viewBooking.getBookingDetails();

		for (BookingDetails bookingDetails : bookingDetailsList) {
			SeatType seatType = bookingDetails.getSeatType();

			ShowAvailability showAvailability = showAvailabilityRepository
					.findByShowAndHallAndSeatTypeAndAvailabilityDate(show, show.getHall(), seatType,
							viewBooking.getShowDate())
					.orElseThrow(() -> new ShowNotFoundException(AppConstants.NO_SHOW_AVAILABILITY));
			int remainingSeats = showAvailability.getRemainingSeats();
			int canceledSeats = bookingDetails.getNoOfSeats();
			showAvailability.setRemainingSeats(remainingSeats + canceledSeats);

			seatCounts.put(seatType.getSeatTypeDesc(), canceledSeats);
			totalCost += seatType.getSeatFare() * canceledSeats;
			cancellationCharges += seatType.getSeatFare() * canceledSeats * 0.2;

			showAvailabilityRepository.save(showAvailability);
		}

		UserBookingDto userBookingDto = new UserBookingDto();
		userBookingDto.setBookingId(viewBooking.getBookingId());
		userBookingDto.setUserName(viewBooking.getUser().getUserName());
		userBookingDto.setMovieName(viewBooking.getMovieName());
		userBookingDto.setBookingDate(LocalDate.now().toString());
		userBookingDto.setHallDesc(viewBooking.getHallDesc());
		userBookingDto.setSlotNo(viewBooking.getSlotNo());
		userBookingDto.setSelectedSeats(seatCounts);
		userBookingDto.setShowDate(viewBooking.getShowDate());
		userBookingDto.setBookingTotal(totalCost);
		userBookingDto.setCancellationCharges(cancellationCharges);
		userBookingDto.setBookingStatus("cancelled");

		return userBookingDto;
	}
	
	public UserBookingDto getBookingByBookingId(int bookingId, String email) {
		
		Booking viewBooking = bookingRepository.findById(bookingId).orElseThrow(
				() -> new BookingNotFoundException(AppConstants.BOOKING_NOT_FOUND_WITH_ID + bookingId));

		if (!viewBooking.getUser().getEmailId().equalsIgnoreCase(email)) {
			throw new InvalidUserException(AppConstants.NOT_VALID_USER);
		}
		
		Map<String, Integer> seatCounts = new HashMap<>();
		double totalCost = 0;

		List<BookingDetails> bookingDetailsList = viewBooking.getBookingDetails();

		
		for (BookingDetails bookingDetails : bookingDetailsList) {
			SeatType seatType = bookingDetails.getSeatType();

			int canceledSeats = bookingDetails.getNoOfSeats();
			
			seatCounts.put(seatType.getSeatTypeDesc(), canceledSeats);
			totalCost += seatType.getSeatFare() * canceledSeats;
		}
		
		UserBookingDto userBookingDto = new UserBookingDto();
		userBookingDto.setBookingId(viewBooking.getBookingId());
		userBookingDto.setUserName(viewBooking.getUser().getUserName());
		userBookingDto.setMovieName(viewBooking.getMovieName());
		userBookingDto.setBookingDate(LocalDate.now().toString());
		userBookingDto.setHallDesc(viewBooking.getHallDesc());
		userBookingDto.setSlotNo(viewBooking.getSlotNo());
		userBookingDto.setSelectedSeats(seatCounts);
		userBookingDto.setShowDate(viewBooking.getShowDate());
		userBookingDto.setBookingTotal(totalCost);
		userBookingDto.setCancellationCharges(0.0d);
		userBookingDto.setBookingStatus("booked");
		
		return userBookingDto;
	}
}

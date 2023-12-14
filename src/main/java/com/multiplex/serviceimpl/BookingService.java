package com.multiplex.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.multiplex.dto.BookingDto;
import com.multiplex.dto.CancelDto;
import com.multiplex.embedded.BookingDetailsId;
import com.multiplex.embedded.HallCapacityId;
import com.multiplex.entity.Booking;
import com.multiplex.entity.BookingDetails;
import com.multiplex.entity.Hall;
import com.multiplex.entity.HallCapacity;
import com.multiplex.entity.SeatType;
import com.multiplex.entity.ShowAvailability;
import com.multiplex.entity.Show;
import com.multiplex.entity.User;
import com.multiplex.exception.BookingException;
import com.multiplex.exception.BookingNotFoundException;
import com.multiplex.exception.InvalidShowException;
import com.multiplex.exception.InvalidUserException;
import com.multiplex.exception.SeatNotAvailableException;
import com.multiplex.exception.ShowNotFoundException;
import com.multiplex.exception.UserNotFoundException;
import com.multiplex.repository.BookingDetailsRepository;
import com.multiplex.repository.BookingRepository;
import com.multiplex.repository.HallCapacityRepository;
import com.multiplex.repository.SeatTypeRepository;
import com.multiplex.repository.ShowAvailabilityRepository;
import com.multiplex.repository.ShowsRepository;
import com.multiplex.repository.UserRepository;
import com.multiplex.util.AppConstants;

@Service
public class BookingService {
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

	@Transactional
	public Booking bookTickets(BookingDto bookingDTO) {
		// Checking if the user is a registered user or not
		User user = userRepository.findByEmailIdIgnoreCase(bookingDTO.getUserEmail())
				.orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_REGISTERED));

		// Find the show based on its Id
		Show show = showsRepository.findById(bookingDTO.getShowId())
				.orElseThrow(() -> new ShowNotFoundException(AppConstants.SHOW_ID_NOT_FOUND + bookingDTO.getShowId()));

		LocalDate bookingDate = bookingDTO.getBookingDate();

		if (bookingDate.isBefore(show.getFromDate()) || bookingDate.isAfter(show.getToDate())) {
			throw new InvalidShowException(AppConstants.NO_SHOW_RUNNING.replace("date",
					bookingDTO.getBookingDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
		}

		Map<String, Integer> selectedSeatTypeCounts = bookingDTO.getSelectedSeatType();

		if (selectedSeatTypeCounts.values().stream().noneMatch(i -> i > 0))
			throw new BookingException(AppConstants.NO_SEAT_TYPE);

		List<SeatType> selectedSeatTypes = seatTypeRepository.findAllBySeatTypeDescIgnoreCaseIn(selectedSeatTypeCounts.keySet());

		return bookTickets(user, show, selectedSeatTypes, selectedSeatTypeCounts, bookingDate);
	}

	@Transactional
	public Booking bookTickets(User user, Show show, List<SeatType> selectedSeatTypes,
			Map<String, Integer> selectedSeatTypeCounts, LocalDate bookingDate) {
		Booking booking = new Booking();
		booking.setBookedDate(LocalDateTime.now());
		booking.setShowDate(bookingDate);
		booking.setUser(user);
		booking.setShows(show);

		booking = bookingRepository.save(booking);

		for (SeatType selectedSeatType : selectedSeatTypes) {
			ShowAvailability showAvailability = getShowAvailability(show, show.getHall(), selectedSeatType,
					bookingDate);

			int remainingSeats = showAvailability.getRemainingSeats();
			int numberOfSeats = selectedSeatTypeCounts.get(selectedSeatType.getSeatTypeDesc());

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
			} else {
				throw new SeatNotAvailableException(
						"Insufficient seats available for seat type: " + selectedSeatType.getSeatTypeDesc());
			}
		}

		return booking;
	}

	@Transactional
	public void cancelBooking(CancelDto cancelDto) {
		Booking canceledBooking = bookingRepository.findById(cancelDto.getBookingId())
				.orElseThrow(() -> new BookingNotFoundException("Booking not found with ID: " + cancelDto.getBookingId()));

		if (!canceledBooking.getUser().getEmailId().equalsIgnoreCase(cancelDto.getUserEmail())) {
			throw new InvalidUserException("User does not have permission to cancel this booking");
		}

		// Check if the show is at least 2 days from now
		LocalDate showDate = canceledBooking.getShowDate();
		LocalDate currentDate = LocalDate.now();
		if (showDate.isBefore(currentDate.plusDays(2))) {
			throw new RuntimeException("Cannot cancel booking within 2 days of the show");
		}

		List<BookingDetails> bookingDetailsList = bookingDetailsRepository.findByBooking(canceledBooking);

		bookingDetailsRepository.deleteAll(bookingDetailsList);

		bookingRepository.delete(canceledBooking);

		increaseSeatAvailability(canceledBooking);
	}

	// helper methods

	private ShowAvailability getShowAvailability(Show show, Hall hall, SeatType seatType, LocalDate availabilityDate) {
		return showAvailabilityRepository
				.findByShowAndHallAndSeatTypeAndAvailabilityDate(show, hall, seatType, availabilityDate)
				.orElseThrow(() -> new RuntimeException(
						"No availability found for the specified show, hall, seat type, and date"));
	}
	
	private void increaseSeatAvailability(Booking canceledBooking) {
		Show show = canceledBooking.getShows();
		List<BookingDetails> bookingDetailsList = canceledBooking.getBookingDetails();
		
		for (BookingDetails bookingDetails : bookingDetailsList) {
			SeatType seatType = bookingDetails.getSeatType();
			System.out.println(seatType.getSeatTypeDesc());
			
			// Retrieve the corresponding show availability for the specific date, slot, and hall
			
			ShowAvailability showAvailability = showAvailabilityRepository
					.findByShowAndHallAndSeatTypeAndAvailabilityDate(show, show.getHall(), seatType, canceledBooking.getShowDate())
					.orElseThrow(() -> new RuntimeException(
							"No availability found for the specified show, hall, seat type, and date"));
			int remainingSeats = showAvailability.getRemainingSeats();
			int canceledSeats = bookingDetails.getNoOfSeats();
			showAvailability.setRemainingSeats(remainingSeats + canceledSeats);
			
			// Save the updated show availability
			showAvailabilityRepository.save(showAvailability);
		}
	}
}

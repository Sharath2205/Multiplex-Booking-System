package com.multiplex.service;

import com.multiplex.dto.BookingDto;
import com.multiplex.dto.CancelDto;
import com.multiplex.dto.UserBookingDto;

public interface BookingService {
	
	UserBookingDto bookTickets(BookingDto bookingDTO);
	
	UserBookingDto cancelBooking(CancelDto cancelDto);
	
	UserBookingDto getBookingByBookingId(int bookingId, String email);
}

package com.multiplex.exception;


@SuppressWarnings("serial")
public class BookingNotFoundException extends RuntimeException {
	public BookingNotFoundException(String message) {
		super(message);
	}
}

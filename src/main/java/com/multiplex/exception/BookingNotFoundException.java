package com.multiplex.exception;


@SuppressWarnings("serial")
public class BookingNotFoundException extends RuntimeException {
	private String message;

	public BookingNotFoundException() {
	}

	public BookingNotFoundException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "BookingException : " + message;
	}

}

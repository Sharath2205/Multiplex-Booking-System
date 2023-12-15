package com.multiplex.exception;

@SuppressWarnings("serial")
public class BookingException extends RuntimeException {
	private String message;

	public BookingException() {
	}

	public BookingException(String message) {
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

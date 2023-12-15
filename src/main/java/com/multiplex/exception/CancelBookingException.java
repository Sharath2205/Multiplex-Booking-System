package com.multiplex.exception;

@SuppressWarnings("serial")
public class CancelBookingException extends RuntimeException {
	private String message;

	public CancelBookingException() {
	}

	public CancelBookingException(String message) {
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

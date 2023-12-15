package com.multiplex.exception;

@SuppressWarnings("serial")
public class SeatNotAvailableException extends RuntimeException {
	private String message;

	public SeatNotAvailableException() {
	}

	public SeatNotAvailableException(String message) {
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

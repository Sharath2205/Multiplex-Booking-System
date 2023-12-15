package com.multiplex.exception;

@SuppressWarnings("serial")
public class HallNotFoundException extends RuntimeException {
	private String message;

	public HallNotFoundException() {
	}

	public HallNotFoundException(String message) {
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

package com.multiplex.exception;

@SuppressWarnings("serial")
public class MovieException extends RuntimeException {
	private String message;

	public MovieException() {
	}

	public MovieException(String message) {
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

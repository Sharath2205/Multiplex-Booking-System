package com.multiplex.exception;

@SuppressWarnings("serial")
public class MovieNotFoundException extends RuntimeException{
	private String message;

	public MovieNotFoundException() {
	}

	public MovieNotFoundException(String message) {
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

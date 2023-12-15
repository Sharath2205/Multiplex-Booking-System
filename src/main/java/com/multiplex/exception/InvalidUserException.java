package com.multiplex.exception;

@SuppressWarnings("serial")
public class InvalidUserException extends RuntimeException {
	private String message;

	public InvalidUserException() {
	}

	public InvalidUserException(String message) {
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

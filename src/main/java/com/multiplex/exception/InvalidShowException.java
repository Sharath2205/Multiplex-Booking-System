package com.multiplex.exception;


@SuppressWarnings("serial")
public class InvalidShowException extends RuntimeException {
	private String message;

	public InvalidShowException() {
	}

	public InvalidShowException(String message) {
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

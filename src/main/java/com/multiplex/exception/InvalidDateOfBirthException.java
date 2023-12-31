package com.multiplex.exception;


@SuppressWarnings("serial")
public class InvalidDateOfBirthException extends RuntimeException {
	private String message;

	public InvalidDateOfBirthException() {
	}

	public InvalidDateOfBirthException(String message) {
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

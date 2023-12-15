package com.multiplex.exception;

@SuppressWarnings("serial")
public class InvalidPasswordException extends RuntimeException {
	private String message;

	public InvalidPasswordException() {
	}

	public InvalidPasswordException(String message) {
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

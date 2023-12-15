package com.multiplex.exception;

@SuppressWarnings("serial")
public class InvalidEmailException extends RuntimeException {
	private String message;

	public InvalidEmailException() {
	}

	public InvalidEmailException(String message) {
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

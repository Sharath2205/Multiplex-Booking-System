package com.multiplex.exception;

@SuppressWarnings("serial")
public class UserCreationException extends RuntimeException {
	private String message;

	public UserCreationException() {
	}

	public UserCreationException(String message) {
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

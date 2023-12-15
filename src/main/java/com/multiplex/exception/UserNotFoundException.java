package com.multiplex.exception;

@SuppressWarnings("serial")
public class UserNotFoundException extends RuntimeException{
	private String message;

	public UserNotFoundException() {
	}

	public UserNotFoundException(String message) {
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

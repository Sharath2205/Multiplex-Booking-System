package com.multiplex.exception;

@SuppressWarnings("serial")
public class HallAlreadyExistsException extends RuntimeException {
	private String message;

	public HallAlreadyExistsException() {
	}

	public HallAlreadyExistsException(String message) {
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

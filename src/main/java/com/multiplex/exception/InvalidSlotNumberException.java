package com.multiplex.exception;

@SuppressWarnings("serial")
public class InvalidSlotNumberException extends RuntimeException {
	private String message;

	public InvalidSlotNumberException() {
	}

	public InvalidSlotNumberException(String message) {
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

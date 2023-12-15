package com.multiplex.exception;

@SuppressWarnings("serial")
public class InsufficentInformationException extends RuntimeException {
	private String message;

	public InsufficentInformationException() {
	}

	public InsufficentInformationException(String message) {
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

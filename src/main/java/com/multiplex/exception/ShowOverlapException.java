package com.multiplex.exception;

@SuppressWarnings("serial")
public class ShowOverlapException extends RuntimeException {
	private String message;

	public ShowOverlapException() {
	}

	public ShowOverlapException(String message) {
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

package com.multiplex.exception;

@SuppressWarnings("serial")
public class HallDeletionException extends RuntimeException {
	private String message;

	public HallDeletionException() {
	}

	public HallDeletionException(String message) {
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

package com.multiplex.exception;


@SuppressWarnings("serial")
public class ShowDeletionException extends RuntimeException {
	private String message;

	public ShowDeletionException() {
	}

	public ShowDeletionException(String message) {
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

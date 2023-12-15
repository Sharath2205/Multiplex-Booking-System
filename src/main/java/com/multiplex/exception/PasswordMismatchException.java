package com.multiplex.exception;

@SuppressWarnings("serial")
public class PasswordMismatchException extends RuntimeException {
	private String message;

	public PasswordMismatchException() {
		}

	public PasswordMismatchException(String message) {
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

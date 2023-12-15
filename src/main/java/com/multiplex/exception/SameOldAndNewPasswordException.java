package com.multiplex.exception;

@SuppressWarnings("serial")
public class SameOldAndNewPasswordException extends RuntimeException {
	private String message;

	public SameOldAndNewPasswordException() {
	}

	public SameOldAndNewPasswordException(String message) {
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

package com.multiplex.exception;

@SuppressWarnings("serial")
public class BookingException extends RuntimeException {
	public BookingException(String message) {
		super(message);
	}
}

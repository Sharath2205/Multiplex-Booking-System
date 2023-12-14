package com.multiplex.exception;

@SuppressWarnings("serial")
public class SeatNotAvailableException extends RuntimeException {
	public SeatNotAvailableException(String message) {
		super(message);
	}
}

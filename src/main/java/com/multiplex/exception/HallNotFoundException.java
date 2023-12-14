package com.multiplex.exception;

@SuppressWarnings("serial")
public class HallNotFoundException extends RuntimeException {
	public HallNotFoundException(String message) {
		super(message);
	}
}

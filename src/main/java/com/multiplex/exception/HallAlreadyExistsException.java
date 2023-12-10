package com.multiplex.exception;

@SuppressWarnings("serial")
public class HallAlreadyExistsException extends RuntimeException {
	public HallAlreadyExistsException(String message) {
		super(message);
	}
}

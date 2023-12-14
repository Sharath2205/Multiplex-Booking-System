package com.multiplex.exception;

@SuppressWarnings("serial")
public class InvalidUserException extends RuntimeException {
	public InvalidUserException(String message) {
		super(message);
	}
}

package com.multiplex.exception;

@SuppressWarnings("serial")
public class InvalidPasswordException extends RuntimeException {
	public InvalidPasswordException(String message) {
		super(message);
	}
}

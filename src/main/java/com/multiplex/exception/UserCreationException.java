package com.multiplex.exception;

@SuppressWarnings("serial")
public class UserCreationException extends RuntimeException {
	public UserCreationException(String message) {
		super(message);
	}
}

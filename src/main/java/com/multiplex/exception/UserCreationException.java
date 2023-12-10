package com.multiplex.exception;

public class UserCreationException extends RuntimeException {
	private static final long serialVersionUID = 2510264112560571768L;

	public UserCreationException(String message) {
		super(message);
	}
}

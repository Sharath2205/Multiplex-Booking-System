package com.multiplex.exception;

public class UserNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 4530275951541322161L;

	public UserNotFoundException(String message) {
		super(message);
	}
}

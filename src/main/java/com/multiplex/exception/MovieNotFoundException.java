package com.multiplex.exception;

public class MovieNotFoundException extends RuntimeException{
	private static final long serialVersionUID = -2973901767992566944L;

	public MovieNotFoundException(String message) {
		super(message);
	}
}

package com.multiplex.exception;

@SuppressWarnings("serial")
public class MovieNotFoundException extends RuntimeException{
	public MovieNotFoundException(String message) {
		super(message);
	}
}

package com.multiplex.exception;

@SuppressWarnings("serial")
public class ShowNotFoundException extends RuntimeException{
	public ShowNotFoundException(String message) {
		super(message);
	}
}

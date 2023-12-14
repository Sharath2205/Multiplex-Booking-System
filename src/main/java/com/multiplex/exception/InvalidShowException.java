package com.multiplex.exception;


@SuppressWarnings("serial")
public class InvalidShowException extends RuntimeException {
	public InvalidShowException(String message) {
		super(message);
	}
}

package com.multiplex.exception;


@SuppressWarnings("serial")
public class InvalidDateOfBirthException extends RuntimeException {
	public InvalidDateOfBirthException(String message) {
		super(message);
	}
}

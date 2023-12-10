package com.multiplex.exception;

@SuppressWarnings("serial")
public class InsufficentInformationException extends RuntimeException {
	public InsufficentInformationException(String message) {
		super(message);
	}
}

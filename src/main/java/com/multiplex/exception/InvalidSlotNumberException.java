package com.multiplex.exception;

@SuppressWarnings("serial")
public class InvalidSlotNumberException extends RuntimeException {
	public InvalidSlotNumberException(String message) {
		super(message);
	}
}

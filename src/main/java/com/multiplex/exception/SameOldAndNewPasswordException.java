package com.multiplex.exception;

@SuppressWarnings("serial")
public class SameOldAndNewPasswordException extends RuntimeException {
	public SameOldAndNewPasswordException(String message) {
		super(message);
	}
}

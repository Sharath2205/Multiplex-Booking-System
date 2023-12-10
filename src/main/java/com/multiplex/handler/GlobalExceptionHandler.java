/**
 * 
 */
package com.multiplex.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.multiplex.exception.InvalidEmailException;
import com.multiplex.exception.InvalidPasswordException;
import com.multiplex.exception.MovieNotFoundException;
import com.multiplex.exception.PasswordMismatchException;
import com.multiplex.exception.SameOldAndNewPasswordException;
import com.multiplex.exception.ShowNotFoundException;
import com.multiplex.exception.UserCreationException;
import com.multiplex.exception.UserNotFoundException;

@ControllerAdvice
public final class GlobalExceptionHandler {
	
	// REGION: USER EXCEPTION HANDLERS 
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> userNotFoundExceptionHandler(UserNotFoundException ex) {
		System.out.println(ex);
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidEmailException.class)
	public ResponseEntity<String> invalidEmailExceptionHandler(InvalidEmailException ex) {
		System.out.println(ex);
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<String> invalidPasswordExceptionHandler(InvalidPasswordException ex) {
		System.out.println(ex);
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(PasswordMismatchException.class)
	public ResponseEntity<String> passwordMismatchExceptionHandler(PasswordMismatchException ex) {
		System.out.println(ex);
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserCreationException.class)
	public ResponseEntity<String> userCreationExceptionHandler(UserCreationException ex) {
		System.out.println(ex);
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(SameOldAndNewPasswordException.class)
	public ResponseEntity<String> sameOldAndNewPasswordExceptionHandler(SameOldAndNewPasswordException ex) {
		System.out.println(ex);
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	// ----------------------- MOVIE EXCEPTION HANDLERS ------------------------
	@ExceptionHandler(MovieNotFoundException.class)
	public ResponseEntity<String> movieNotFoundExceptionHandler(MovieNotFoundException ex) {
		System.out.println(ex);
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	// ----------------------- SHOW EXCEPTION HANDLERS -------------------------=
	@ExceptionHandler(ShowNotFoundException.class)
	public ResponseEntity<String> showNotFoundExceptionHandler(ShowNotFoundException ex) {
		System.out.println(ex);
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
}
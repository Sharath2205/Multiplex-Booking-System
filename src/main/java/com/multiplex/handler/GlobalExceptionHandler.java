package com.multiplex.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.multiplex.exception.BookingException;
import com.multiplex.exception.BookingNotFoundException;
import com.multiplex.exception.CancelBookingException;
import com.multiplex.exception.HallNotFoundException;
import com.multiplex.exception.InsufficentInformationException;
import com.multiplex.exception.InvalidDateOfBirthException;
import com.multiplex.exception.InvalidEmailException;
import com.multiplex.exception.InvalidPasswordException;
import com.multiplex.exception.InvalidShowException;
import com.multiplex.exception.InvalidSlotNumberException;
import com.multiplex.exception.InvalidUserException;
import com.multiplex.exception.MovieNotFoundException;
import com.multiplex.exception.PasswordMismatchException;
import com.multiplex.exception.SameOldAndNewPasswordException;
import com.multiplex.exception.SeatNotAvailableException;
import com.multiplex.exception.ShowNotFoundException;
import com.multiplex.exception.ShowOverlapException;
import com.multiplex.exception.UserCreationException;
import com.multiplex.exception.UserNotFoundException;

@ControllerAdvice
public final class GlobalExceptionHandler {
	
	ApiError error = new ApiError();
	
	// REGION: USER EXCEPTION HANDLERS 
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ApiError> userNotFoundExceptionHandler(UserNotFoundException ex) {
		error.setStatus(HttpStatus.BAD_REQUEST);
    	error.setMessage(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now());
    	return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}
	
	@ExceptionHandler(InvalidEmailException.class)
	public ResponseEntity<ApiError> invalidEmailExceptionHandler(InvalidEmailException ex) {
		error.setStatus(HttpStatus.BAD_REQUEST);
    	error.setMessage(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now());
    	return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}
	
	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<ApiError> invalidPasswordExceptionHandler(InvalidPasswordException ex) {
		error.setStatus(HttpStatus.BAD_REQUEST);
    	error.setMessage(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now());
    	return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}
	
	@ExceptionHandler(PasswordMismatchException.class)
	public ResponseEntity<ApiError> passwordMismatchExceptionHandler(PasswordMismatchException ex) {
		error.setStatus(HttpStatus.BAD_REQUEST);
    	error.setMessage(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now());
    	return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}
	
	@ExceptionHandler(UserCreationException.class)
	public ResponseEntity<ApiError> userCreationExceptionHandler(UserCreationException ex) {
		error.setStatus(HttpStatus.BAD_REQUEST);
    	error.setMessage(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now());
    	return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}
	
	@ExceptionHandler(SameOldAndNewPasswordException.class)
	public ResponseEntity<ApiError> sameOldAndNewPasswordExceptionHandler(SameOldAndNewPasswordException ex) {
		error.setStatus(HttpStatus.BAD_REQUEST);
    	error.setMessage(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now());
    	return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}
	
	@ExceptionHandler(InsufficentInformationException.class)
	public ResponseEntity<ApiError> insufficentInformationExceptionHandler(InsufficentInformationException ex) {
		error.setStatus(HttpStatus.BAD_REQUEST);
    	error.setMessage(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now());
    	return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}
	
	@ExceptionHandler(InvalidDateOfBirthException.class)
	public ResponseEntity<ApiError> invalidDateOfBirthExceptionHandler(InvalidDateOfBirthException ex) {
		error.setStatus(HttpStatus.BAD_REQUEST);
    	error.setMessage(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now());
    	return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}
	
	@ExceptionHandler(InvalidUserException.class)
	public ResponseEntity<ApiError> invalidUserExceptionHandler(InvalidUserException ex) {
		error.setStatus(HttpStatus.BAD_REQUEST);
		error.setMessage(ex.getMessage());
		error.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}
	
	// ----------------------- MOVIE EXCEPTION HANDLERS ------------------------
	@ExceptionHandler(MovieNotFoundException.class)
	public ResponseEntity<ApiError> movieNotFoundExceptionHandler(MovieNotFoundException ex) {
		error.setStatus(HttpStatus.BAD_REQUEST);
    	error.setMessage(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now());
    	return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}
	
	// ----------------------- SHOW EXCEPTION HANDLERS -------------------------
	@ExceptionHandler(ShowNotFoundException.class)
	public ResponseEntity<ApiError> showNotFoundExceptionHandler(ShowNotFoundException ex) {
		error.setStatus(HttpStatus.BAD_REQUEST);
    	error.setMessage(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now());
    	return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}
	
	@ExceptionHandler(InvalidShowException.class)
	public ResponseEntity<ApiError> invalidShowExceptionHandler(InvalidShowException ex) {
		error.setStatus(HttpStatus.BAD_REQUEST);
    	error.setMessage(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now());
    	return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}

	@ExceptionHandler(ShowOverlapException.class)
	public ResponseEntity<ApiError> showOverlapExceptionHandler(ShowOverlapException ex) {
		error.setStatus(HttpStatus.BAD_REQUEST);
		error.setMessage(ex.getMessage());
		error.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}
	
	// ----------------------- HALL EXCEPTION HANDLERS ----------------------------
	
	@ExceptionHandler(HallNotFoundException.class)
	public ResponseEntity<ApiError> hallNotFoundExceptionHandler(HallNotFoundException ex) {
		error.setStatus(HttpStatus.BAD_REQUEST);
    	error.setMessage(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now());
    	return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}
	
	
	// ----------------------- BOOKING EXCEPTION HANDLERS ------------------------
	@ExceptionHandler(BookingException.class)
	public ResponseEntity<ApiError> bookingExceptionHandler(BookingException ex) {
		error.setStatus(HttpStatus.BAD_REQUEST);
    	error.setMessage(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now());
    	return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}
	
	@ExceptionHandler(BookingNotFoundException.class)
	public ResponseEntity<ApiError> bookingNotFoundExceptionHandler(BookingNotFoundException ex) {
		error.setStatus(HttpStatus.BAD_REQUEST);
    	error.setMessage(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now());
    	return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}
	
	@ExceptionHandler(CancelBookingException.class)
	public ResponseEntity<ApiError> cancelBookingExceptionHandler(CancelBookingException ex) {
		error.setStatus(HttpStatus.BAD_REQUEST);
    	error.setMessage(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now());
    	return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}
	
	@ExceptionHandler(SeatNotAvailableException.class)
	public ResponseEntity<ApiError> seatNotAvailableExceptionHandler(SeatNotAvailableException ex) {
		error.setStatus(HttpStatus.BAD_REQUEST);
    	error.setMessage(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now());
    	return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}
	
	@ExceptionHandler(InvalidSlotNumberException.class)
	public ResponseEntity<ApiError> invalidSlotNumberExceptionHandler(InvalidSlotNumberException ex) {
		error.setStatus(HttpStatus.BAD_REQUEST);
    	error.setMessage(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now());
    	return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> exceptionHandler(Exception ex) {
		error.setStatus(HttpStatus.BAD_REQUEST);
    	error.setMessage(ex.getMessage());
    	error.setTimestamp(LocalDateTime.now());
    	return new ResponseEntity<>(error, HttpStatus.valueOf(400));
	}
}
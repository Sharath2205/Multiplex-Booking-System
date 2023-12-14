package com.multiplex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multiplex.dto.BookingDto;
import com.multiplex.dto.CancelDto;
import com.multiplex.dto.UserDto;
import com.multiplex.dto.UserLoginDto;
import com.multiplex.dto.UserPasswordResetDto;
import com.multiplex.entity.Booking;
import com.multiplex.entity.User;
import com.multiplex.serviceimpl.BookingService;
import com.multiplex.serviceimpl.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	BookingService bookingService;

	@PostMapping(value = "/register", consumes = "application/json")
	public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
		return new ResponseEntity<>(userService.registerUser(userDto), HttpStatus.OK);
	}

	@PostMapping(value = "/login", consumes = "application/json")
	public ResponseEntity<String> loginUser(@RequestBody UserLoginDto userDto) {
		return new ResponseEntity<>(userService.loginUser(userDto), HttpStatus.OK);
	}

	@PostMapping(value = "/resetpassword", consumes = "application/json")
	public ResponseEntity<String> resetPassword(@RequestBody UserPasswordResetDto userDto) {
		return new ResponseEntity<>(userService.resetPassword(userDto), HttpStatus.OK);
	}

	@PutMapping(value = "/editprofile", consumes = "application/json")
	public ResponseEntity<User> updateUserByMobile(@RequestBody UserDto userDto) {
		
		return new ResponseEntity<>(userService.updateUser(userDto), HttpStatus.OK);
	}

	@DeleteMapping(value = "/deleteaccount", consumes = "application/json")
	public ResponseEntity<String> deleteUser(@RequestBody UserLoginDto userDto) {
		return new ResponseEntity<>(userService.deleteUser(userDto), HttpStatus.OK);
	}

	@PostMapping("/book")
	public ResponseEntity<String> bookTickets(@RequestBody BookingDto bookingDTO) {
		Booking booking = bookingService.bookTickets(bookingDTO);
		return ResponseEntity.ok("Booking successful! Booking ID: " + booking.getBookingId());
	}
	
	@PostMapping("/cancel")
    public ResponseEntity<String> cancelBooking(@RequestBody CancelDto cancelDto) {
        try {
            bookingService.cancelBooking(cancelDto);
            return new ResponseEntity<>("Booking cancelled successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

package com.multiplex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.multiplex.dto.BookingDto;
import com.multiplex.dto.CancelDto;
import com.multiplex.dto.UserBookingDto;
import com.multiplex.dto.UserDashboardDto;
import com.multiplex.dto.UserDto;
import com.multiplex.dto.UserLoginDto;
import com.multiplex.dto.UserPasswordResetDto;
import com.multiplex.dto.UserProfileDto;
import com.multiplex.service.BookingService;
import com.multiplex.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private BookingService bookingService;

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
	public ResponseEntity<UserProfileDto> updateUserByMobile(@RequestBody UserProfileDto userDto) {

		return new ResponseEntity<>(userService.updateUser(userDto), HttpStatus.OK);
	}

	@DeleteMapping(value = "/deleteaccount", consumes = "application/json")
	public ResponseEntity<String> deleteUser(@RequestBody UserLoginDto userDto) {
		return new ResponseEntity<>(userService.deleteUser(userDto), HttpStatus.OK);
	}

	@PostMapping("/bookticket")
	public ResponseEntity<UserBookingDto> bookTickets(@RequestBody BookingDto bookingDTO) {
		return new ResponseEntity<>(bookingService.bookTickets(bookingDTO), HttpStatus.OK);
	}

	@PostMapping("/cancelbooking")
	public ResponseEntity<UserBookingDto> cancelBooking(@RequestBody CancelDto cancelDto) {
			return new ResponseEntity<>(bookingService.cancelBooking(cancelDto), HttpStatus.OK);
	}
	
	@GetMapping("/viewbooking")
	public ResponseEntity<UserBookingDto> viewBooking(@RequestParam int bookingId, String email) {
		return new ResponseEntity<>(bookingService.getBookingByBookingId(bookingId, email), HttpStatus.OK);
	}
	
	@GetMapping("/dashboard")
	public ResponseEntity<UserDashboardDto> userDashboard(@RequestParam String email) {
		return new ResponseEntity<>(userService.userDashboard(email), HttpStatus.OK);
	}
}

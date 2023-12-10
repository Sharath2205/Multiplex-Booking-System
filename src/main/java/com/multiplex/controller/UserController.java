package com.multiplex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multiplex.dto.MoviesDto;
import com.multiplex.dto.UserDto;
import com.multiplex.dto.UserShowsDto;
import com.multiplex.entity.User;
import com.multiplex.serviceimpl.MovieService;
import com.multiplex.serviceimpl.ShowsService;
import com.multiplex.serviceimpl.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	ShowsService showService;

	@Autowired
	MovieService movieService;

	@PostMapping(value = "/register", consumes = "application/json")
	public ResponseEntity<User> addUser(@RequestBody User user) {
		return new ResponseEntity<>(userService.createUser(user), HttpStatus.OK);
	}

	@PostMapping(value = "/login", consumes = "application/json")
	public ResponseEntity<String> loginUser(@RequestBody User user) {
		return new ResponseEntity<>(userService.loginUser(user), HttpStatus.OK);
	}

	@PostMapping(value = "/resetPassword", consumes = "application/json")
	public ResponseEntity<String> resetPassword(@RequestBody UserDto userDto) {
		return new ResponseEntity<>(userService.resetPassword(userDto), HttpStatus.OK);
	}

	@PutMapping(value = "/editprofile", consumes = "application/json")
	public ResponseEntity<User> updateUserByMobile(@RequestBody User user) {
		userService.updateUser(user);
		return new ResponseEntity<>(userService.getUserByName(user.getUserName()), HttpStatus.OK);
	}

	@DeleteMapping(value = "/deleteAccount", consumes = "application/json")
	public ResponseEntity<String> deleteUser(@RequestBody UserDto userDto) {
		return new ResponseEntity<>(userService.deleteUser(userDto), HttpStatus.OK);
	}

	@GetMapping(value="/movies/{movieName}")
	public ResponseEntity<MoviesDto> getMovieByMovieName(@PathVariable String movieName) {
		return  new ResponseEntity<>(movieService.getMovieDetailsByName(movieName), HttpStatus.OK);
	}
	
	@GetMapping(value="/shows/{movieName}")
	public ResponseEntity<List<UserShowsDto>> getAllShowsByMovieName(@PathVariable String movieName) {
		return  new ResponseEntity<>(showService.getAllShowsByMovieName(movieName), HttpStatus.OK);
	}
}







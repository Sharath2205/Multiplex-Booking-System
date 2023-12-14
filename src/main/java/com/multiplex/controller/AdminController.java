package com.multiplex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multiplex.dto.HallDto;
import com.multiplex.dto.HallPublishedDto;
import com.multiplex.dto.PublishMovieDto;
import com.multiplex.dto.PublishShowDto;
import com.multiplex.dto.ShowDto;
import com.multiplex.entity.Hall;
import com.multiplex.entity.Show;
import com.multiplex.entity.User;
import com.multiplex.serviceimpl.HallService;
import com.multiplex.serviceimpl.MovieService;
import com.multiplex.serviceimpl.ShowsService;
import com.multiplex.serviceimpl.UserService;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

	@Autowired
	UserService userService;

	@Autowired
	ShowsService showsService;

	@Autowired
	HallService hallService;
	
	@Autowired
	MovieService movieService;

	@GetMapping("/getAllUsers")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUsers();
		return new ResponseEntity<>(users.stream().filter(a -> Character.toLowerCase(a.getUserType()) == 'u').toList(),
				HttpStatus.OK);
	}

	@PostMapping(value = "/publishshow", consumes = "application/json")
	public ResponseEntity<String> addShow(@RequestBody PublishShowDto show) {
		if (showsService.addShow(show)) {
			return new ResponseEntity<>("Show added successfully", HttpStatus.OK);
		} else	
			return new ResponseEntity<>("Oops, Please try again!", HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping(value = "/removeshow/{showId}")
	public ResponseEntity<String> deleteShow(@PathVariable int showId) {
		return showsService.deleteShowById(showId) ? new ResponseEntity<>("Show deleted successfully", HttpStatus.OK) : new ResponseEntity<>("Show with show id " + showId + " not found!!", HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/publishhall")
	public ResponseEntity<HallPublishedDto> addHall(@RequestBody HallDto hall) {
		return new ResponseEntity<>(hallService.addHallWithSeatTypes(hall), HttpStatus.CREATED);
	}
	
	@GetMapping("/hall/{hallId}")
    public ResponseEntity<Hall> getHallById(@PathVariable int hallId) {
        return new ResponseEntity<>(hallService.getHallById(hallId), HttpStatus.OK);
    }
    
    @DeleteMapping("/deletehall/{hallId}")
    public ResponseEntity<Boolean> deleteHallById(@PathVariable int hallId) {
        return new ResponseEntity<>(hallService.deleteHallById(hallId),HttpStatus.OK);
    }
    
    @PostMapping("/addmovie")
    public ResponseEntity<String> addMovie(@RequestBody PublishMovieDto publishMovieDto) {
    	return new ResponseEntity<>(movieService.addMovie(publishMovieDto),HttpStatus.OK);
    }
}

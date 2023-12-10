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
import com.multiplex.dto.HallWithSeatTypesDTO;
import com.multiplex.entity.Hall;
import com.multiplex.entity.Shows;
import com.multiplex.entity.User;
import com.multiplex.serviceimpl.HallService;
import com.multiplex.serviceimpl.ShowsService;
import com.multiplex.serviceimpl.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	UserService userService;

	@Autowired
	ShowsService showsService;

	@Autowired
	HallService hallService;

	@GetMapping("/getAllUsers")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUsers();
		return new ResponseEntity<>(users.stream().filter(a -> Character.toLowerCase(a.getUserType()) == 'u').toList(),
				HttpStatus.OK);
	}

	@GetMapping("/getUserByUserName/{userName}")
	public ResponseEntity<User> getUserByUserName(@PathVariable String userName) {
		return new ResponseEntity<>(userService.getUserByName(userName), HttpStatus.OK);
	}

	@PostMapping(value = "/addShow", consumes = "application/json")
	public ResponseEntity<String> addShow(@RequestBody Shows show) {
		if (showsService.addShow(show)) {
			return new ResponseEntity<>("Show added successfully", HttpStatus.OK);
		} else
			return new ResponseEntity<>("Oops, Please try again!", HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@DeleteMapping(value = "/removeShow/{showId}")
	public ResponseEntity<String> deleteShow(@PathVariable int showId) {
		showsService.deleteShowById(showId);
		return new ResponseEntity<>("Show deleted successfully", HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<Hall> addHall(@RequestBody HallDto hall) {
		Hall addedHall = hallService.addHallWithSeatTypes(hall);
		return new ResponseEntity<>(addedHall, HttpStatus.CREATED);
	}
	
	@GetMapping("/{hallId}/withSeatTypes")
    public ResponseEntity<HallWithSeatTypesDTO> getHallWithSeatTypes(@PathVariable int hallId) {
        HallWithSeatTypesDTO hallWithSeatTypesDTO = hallService.getHallWithSeatTypes(hallId);

        if (hallWithSeatTypesDTO != null) {
            return ResponseEntity.ok(hallWithSeatTypesDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

package com.multiplex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multiplex.dto.UserShowsDto;
import com.multiplex.service.ShowsServiceImpl;

@RestController
@RequestMapping("/api/v1/shows")
public class ShowsController {
	
	@Autowired
	private ShowsServiceImpl showService;
	
	@GetMapping(value = "/{showId}")
	public ResponseEntity<UserShowsDto> getShowById(@PathVariable int showId) {
		return new ResponseEntity<>(showService.getShowById(showId), HttpStatus.OK);
	}
}

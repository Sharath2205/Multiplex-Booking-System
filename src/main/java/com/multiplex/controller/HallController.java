package com.multiplex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.multiplex.entity.Hall;
import com.multiplex.service.HallService;

@RestController
@RequestMapping("/api/v1/hall")
public class HallController {
	
	@Autowired
	private HallService hallService;
	
	
	@GetMapping("/id/{hallId}")
	public ResponseEntity<Hall> getShowByHallId(@PathVariable(name = "hallId") int hallId){
		return new ResponseEntity<>(hallService.getHallById(hallId), HttpStatus.OK);
	}
	
	@GetMapping("/{hallName}")
	public ResponseEntity<Hall> getShowByHallDesc(@RequestParam(name = "hallName") String hallName) {
		return new ResponseEntity<>(hallService.getHallByHallDesc(hallName), HttpStatus.OK);
	}
}

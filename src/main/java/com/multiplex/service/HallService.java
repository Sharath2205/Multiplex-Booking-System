package com.multiplex.service;

import java.util.List;

import com.multiplex.dto.HallDto;
import com.multiplex.dto.HallPublishedDto;
import com.multiplex.entity.Hall;

public interface HallService {
	HallPublishedDto addHallWithSeatTypes(HallDto hallDto);
	
	List<Hall> getAllHalls();
	
	Hall getHallById(int hallId);
	
	boolean deleteHallById(int hallId);
	
	Hall getHallByHallDesc(String hallDesc);
}

package com.multiplex.service;

import java.util.List;

import com.multiplex.dto.PublishShowDto;
import com.multiplex.dto.UserShowsDto;

public interface ShowsService {
	
	boolean addShow(PublishShowDto showDto);
	
	boolean deleteShowById(int id);
	
	List<UserShowsDto> getAllShowsByMovieName(String movieName);
	
	UserShowsDto getShowById(int showId);
}

package com.multiplex.iservice;

import java.util.List;

import com.multiplex.dto.PublishShowDto;
import com.multiplex.dto.UserShowsDto;

public interface IShowsService {
	
	boolean addShow(PublishShowDto showDto);
	
	boolean deleteShowById(int id);
	
	List<UserShowsDto> getAllShowsByMovieName(String movieName);
}

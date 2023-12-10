package com.multiplex.iservice;

import java.util.List;

import com.multiplex.dto.UserShowsDto;
import com.multiplex.entity.Shows;

public interface IShowsService {
	
	boolean addShow(Shows show);
	
	boolean deleteShowById(int id);
	
	List<UserShowsDto> getAllShowsByMovieName(String movieName);
}

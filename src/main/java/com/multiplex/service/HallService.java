package com.multiplex.service;

import com.multiplex.dto.HallDto;
import com.multiplex.dto.HallPublishedDto;

public interface HallService {
	HallPublishedDto addHallWithSeatTypes(HallDto hallDto);
}

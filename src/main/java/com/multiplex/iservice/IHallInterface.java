package com.multiplex.iservice;

import com.multiplex.dto.HallDto;
import com.multiplex.dto.HallPublishedDto;

public interface IHallInterface {
	HallPublishedDto addHallWithSeatTypes(HallDto hallDto);
}

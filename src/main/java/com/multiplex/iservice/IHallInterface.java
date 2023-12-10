package com.multiplex.iservice;

import com.multiplex.dto.HallDto;
import com.multiplex.entity.Hall;

public interface IHallInterface {
	Hall addHallWithSeatTypes(HallDto hallDto);
}

package com.multiplex.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class HallDto {
	@JsonProperty(access = Access.READ_ONLY)
	private int hallId;
	private String hallDesc;
	private List<SeatTypeDto> seatTypes;

	public HallDto() {
		super();
	}

	public HallDto(int hallId, String hallDesc, List<SeatTypeDto> seatTypes) {
		super();
		this.hallId = hallId;
		this.hallDesc = hallDesc;
		this.seatTypes = seatTypes;
	}

	public int getHallId() {
		return hallId;
	}

	public void setHallId(int hallId) {
		this.hallId = hallId;
	}

	public String getHallDesc() {
		return hallDesc;
	}

	public void setHallDesc(String hallDesc) {
		this.hallDesc = hallDesc;
	}

	public List<SeatTypeDto> getSeatTypes() {
		return seatTypes;
	}

	public void setSeatTypes(List<SeatTypeDto> seatTypes) {
		this.seatTypes = seatTypes;
	}

}
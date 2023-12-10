package com.multiplex.dto;

import java.util.List;

public class HallWithSeatTypesDTO {
	private int hallId;
	private String hallDesc;
	private int totalCapacity;
	private List<SeatTypeWithCapacityDTO> seatTypes;

	public HallWithSeatTypesDTO() {
		super();
	}

	public HallWithSeatTypesDTO(int hallId, String hallDesc, int totalCapacity, List<SeatTypeWithCapacityDTO> seatTypes) {
		super();
		this.hallId = hallId;
		this.hallDesc = hallDesc;
		this.totalCapacity = totalCapacity;
		this.seatTypes = seatTypes;
	}
	
	

	public int getTotalCapacity() {
		return totalCapacity;
	}

	public void setTotalCapacity(int totalCapacity) {
		this.totalCapacity = totalCapacity;
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

	public List<SeatTypeWithCapacityDTO> getSeatTypes() {
		return seatTypes;
	}

	public void setSeatTypes(List<SeatTypeWithCapacityDTO> seatTypes) {
		this.seatTypes = seatTypes;
	}

}

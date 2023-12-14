package com.multiplex.dto;

import java.util.List;

import com.multiplex.entity.HallCapacity;

public class HallPublishedDto {
	private int hallId;
	private String hallDesc;
	private int totalCapacity;
	private List<HallCapacity> hallCapacities;

	public HallPublishedDto() {
		super();
	}

	public HallPublishedDto(int hallId, String hallDesc, int totalCapacity, List<HallCapacity> hallCapacities) {
		super();
		this.hallId = hallId;
		this.hallDesc = hallDesc;
		this.totalCapacity = totalCapacity;
		this.hallCapacities = hallCapacities;
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

	public int getTotalCapacity() {
		return totalCapacity;
	}

	public void setTotalCapacity(int totalCapacity) {
		this.totalCapacity = totalCapacity;
	}

	public List<HallCapacity> getHallCapacities() {
		return hallCapacities;
	}

	public void setHallCapacities(List<HallCapacity> hallCapacities) {
		this.hallCapacities = hallCapacities;
	}

}

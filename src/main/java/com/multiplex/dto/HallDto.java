package com.multiplex.dto;

import java.util.List;

public class HallDto {

    private String hallDesc;
    private int totalCapacity;
    private List<Integer> seatTypeIds;
    private List<Integer> seatCounts;

    public HallDto() {
    }

    public HallDto(String hallDesc, int totalCapaacity, List<Integer> seatTypeIds, List<Integer> seatCounts) {
        this.hallDesc = hallDesc;
        this.totalCapacity = totalCapaacity;
        this.seatTypeIds = seatTypeIds;
        this.seatCounts = seatCounts;
    }
    
    

    public int getTotalCapacity() {
		return totalCapacity;
	}

	public void setTotalCapacity(int totalCapacity) {
		this.totalCapacity = totalCapacity;
	}

	public String getHallDesc() {
        return hallDesc;
    }

    public void setHallDesc(String hallDesc) {
        this.hallDesc = hallDesc;
    }

    public List<Integer> getSeatTypeIds() {
        return seatTypeIds;
    }

    public void setSeatTypeIds(List<Integer> seatTypeIds) {
        this.seatTypeIds = seatTypeIds;
    }

    public List<Integer> getSeatCounts() {
        return seatCounts;
    }

    public void setSeatCounts(List<Integer> seatCounts) {
        this.seatCounts = seatCounts;
    }
}
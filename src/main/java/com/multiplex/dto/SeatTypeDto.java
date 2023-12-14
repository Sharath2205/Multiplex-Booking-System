package com.multiplex.dto;

public class SeatTypeDto {
	private String seatTypeDesc;
	private float seatFare;
	private int seatCount;

	public SeatTypeDto() {
		super();
	}

	public SeatTypeDto(String seatTypeDesc, float seatFare, int seatCount) {
		super();
		this.seatTypeDesc = seatTypeDesc;
		this.seatFare = seatFare;
		this.seatCount = seatCount;
	}

	public String getSeatTypeDesc() {
		return seatTypeDesc;
	}

	public void setSeatTypeDesc(String seatTypeDesc) {
		this.seatTypeDesc = seatTypeDesc;
	}

	public float getSeatFare() {
		return seatFare;
	}

	public void setSeatFare(float seatFare) {
		this.seatFare = seatFare;
	}

	public int getSeatCount() {
		return seatCount;
	}

	public void setSeatCount(int seatCount) {
		this.seatCount = seatCount;
	}

}
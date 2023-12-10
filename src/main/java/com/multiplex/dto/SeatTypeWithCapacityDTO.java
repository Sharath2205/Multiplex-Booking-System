package com.multiplex.dto;

public class SeatTypeWithCapacityDTO {
	private int seatTypeId;
	private String seatTypeDesc;
	private float seatFare;
	private int seatCount;

	public SeatTypeWithCapacityDTO(int seatTypeId, String seatTypeDesc, float seatFare, int seatCount) {
		super();
		this.seatTypeId = seatTypeId;
		this.seatTypeDesc = seatTypeDesc;
		this.seatFare = seatFare;
		this.seatCount = seatCount;
	}

	public SeatTypeWithCapacityDTO() {
		super();
	}

	public int getSeatTypeId() {
		return seatTypeId;
	}

	public void setSeatTypeId(int seatTypeId) {
		this.seatTypeId = seatTypeId;
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

	@Override
	public String toString() {
		return "SeatTypeWithCapacityDTO [seatTypeId=" + seatTypeId + ", seatTypeDesc=" + seatTypeDesc + ", seatFare="
				+ seatFare + ", seatCount=" + seatCount + "]";
	}

}

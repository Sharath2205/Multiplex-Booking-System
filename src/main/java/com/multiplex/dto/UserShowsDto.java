package com.multiplex.dto;

import java.time.LocalDate;

public class UserShowsDto {
	private String hallDesc;
	private String movieName;
	private int slotNo;
	private LocalDate fromDate;
	private LocalDate toDate;

	public UserShowsDto() {
		super();
	}

	public UserShowsDto(String hallDesc, String movieName, int slotNo, LocalDate fromDate, LocalDate toDate) {
		super();
		this.hallDesc = hallDesc;
		this.movieName = movieName;
		this.slotNo = slotNo;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	public String getHallDesc() {
		return hallDesc;
	}

	public void setHallDesc(String hallDesc) {
		this.hallDesc = hallDesc;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public int getSlotNo() {
		return slotNo;
	}

	public void setSlotNo(int slotNo) {
		this.slotNo = slotNo;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	@Override
	public String toString() {
		return "UserShowsDto [hallDesc =" + hallDesc + ", movieName=" + movieName + ", slotNo=" + slotNo + ", fromDate="
				+ fromDate + ", toDate=" + toDate + "]";
	}

	// Getters and setters
}
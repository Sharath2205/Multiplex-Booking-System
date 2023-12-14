package com.multiplex.dto;

import java.time.LocalDate;

public class PublishShowDto {
	private int adminId;
	private String movieName;
	private String hallName;
	private int slotNo;
	private LocalDate fromDate;
	private LocalDate toDate;

	public PublishShowDto() {
		super();
	}

	public PublishShowDto(int adminId, String movieName, String hallName, int slotNo, LocalDate fromDate, LocalDate toDate) {
		super();
		this.adminId = adminId;
		this.movieName = movieName;
		this.hallName = hallName;
		this.slotNo = slotNo;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getHallName() {
		return hallName;
	}

	public void setHallName(String hallName) {
		this.hallName = hallName;
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

}

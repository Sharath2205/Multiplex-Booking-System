package com.multiplex.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.multiplex.entity.Hall;
import com.multiplex.entity.Movies;

public class ShowDto {
	@JsonProperty(access = Access.WRITE_ONLY)
	private int adminId;
	
	
	private Hall hall;
	
	private Movies movie;
	
	private int slotNo;
	
	private LocalDate fromDate;
	
	private LocalDate toDate;

	public ShowDto() {
		
	}
	
	public ShowDto(int adminId, Hall hall, Movies movie, int slotNo, LocalDate fromDate, LocalDate toDate) {
		super();
		this.adminId = adminId;
		this.hall = hall;
		this.movie = movie;
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

	public Hall getHall() {
		return hall;
	}

	public void setHall(Hall hall) {
		this.hall = hall;
	}

	public Movies getMovie() {
		return movie;
	}

	public void setMovie(Movies movie) {
		this.movie = movie;
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

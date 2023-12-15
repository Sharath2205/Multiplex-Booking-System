package com.multiplex.dto;

import java.time.LocalDate;

public class EarningsInputDto {
	private LocalDate fromDate;
	private LocalDate toDate;

	public EarningsInputDto() {
		super();
	}

	public EarningsInputDto(LocalDate fromDate, LocalDate toDate) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
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

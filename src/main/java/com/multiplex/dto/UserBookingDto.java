package com.multiplex.dto;

import java.time.LocalDate;
import java.util.Map;

public class UserBookingDto {
	private int bookingId;
	private String userName;
	private String movieName;
	private String bookingDate;
	private String hallDesc;
	private int slotNo;
	private Map<String, Integer> selectedSeats;
	private LocalDate showDate;
	private Double bookingTotal;
	private Double cancellationCharges;
	private String bookingStatus;

	public UserBookingDto() {
		super();
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getHallDesc() {
		return hallDesc;
	}

	public void setHallDesc(String hallDesc) {
		this.hallDesc = hallDesc;
	}

	public int getSlotNo() {
		return slotNo;
	}

	public void setSlotNo(int slotNo) {
		this.slotNo = slotNo;
	}

	public Map<String, Integer> getSelectedSeats() {
		return selectedSeats;
	}

	public void setSelectedSeats(Map<String, Integer> selectedSeats) {
		this.selectedSeats = selectedSeats;
	}

	public LocalDate getShowDate() {
		return showDate;
	}

	public void setShowDate(LocalDate showDate) {
		this.showDate = showDate;
	}

	public Double getBookingTotal() {
		return bookingTotal;
	}

	public void setBookingTotal(Double bookingTotal) {
		this.bookingTotal = bookingTotal;
	}

	public String getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public Double getCancellationCharges() {
		return cancellationCharges;
	}

	public void setCancellationCharges(Double cancellationCharges) {
		this.cancellationCharges = cancellationCharges;
	}
}

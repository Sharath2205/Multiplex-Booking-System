package com.multiplex.dto;

import java.time.LocalDate;
import java.util.Map;

public class UserBookingDto {
	private String bookingId;
	private String userName;
	private String movieName;
	private LocalDate bookingDate;
	private String hallDesc;
	private String slotNo;
	private Map<String, Integer> selectedSeats;
	private LocalDate showDate;
	private Double bookingTotal;
	private String bookingStatus;

	public UserBookingDto() {
		super();
	}

	public UserBookingDto(String bookingId, String userName, String movieName, LocalDate bookingDate, String hallDesc,
			String slotNo, Map<String, Integer> selectedSeats, LocalDate showDate, Double bookingTotal,
			String bookingStatus) {
		super();
		this.bookingId = bookingId;
		this.userName = userName;
		this.movieName = movieName;
		this.bookingDate = bookingDate;
		this.hallDesc = hallDesc;
		this.slotNo = slotNo;
		this.selectedSeats = selectedSeats;
		this.showDate = showDate;
		this.bookingTotal = bookingTotal;
		this.bookingStatus = bookingStatus;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
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

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getHallDesc() {
		return hallDesc;
	}

	public void setHallDesc(String hallDesc) {
		this.hallDesc = hallDesc;
	}

	public String getSlotNo() {
		return slotNo;
	}

	public void setSlotNo(String slotNo) {
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

}

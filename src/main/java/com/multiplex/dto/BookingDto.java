package com.multiplex.dto;

import java.time.LocalDate;
import java.util.Map;

public class BookingDto {
	private String userEmail;
	private int showId;
	private LocalDate bookingDate;
	private Map<String, Integer> selectedSeatType;

	public BookingDto() {
		super();
	}

	public BookingDto(String userEmail, int showId, LocalDate bookingDate, Map<String, Integer> selectedSeatType) {
		super();
		this.userEmail = userEmail;
		this.showId = showId;
		this.bookingDate = bookingDate;
		this.selectedSeatType = selectedSeatType;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public int getShowId() {
		return showId;
	}

	public void setShowId(int showId) {
		this.showId = showId;
	}

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Map<String, Integer> getSelectedSeatType() {
		return selectedSeatType;
	}

	public void setSelectedSeatType(Map<String, Integer> selectedSeatType) {
		this.selectedSeatType = selectedSeatType;
	}

	@Override
	public String toString() {
		return "BookingDto [userEmail=" + userEmail + ", showId=" + showId + ", bookingDate=" + bookingDate
				+ ", selectedSeatType=" + selectedSeatType + "]";
	}
}

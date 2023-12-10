package com.multiplex.embedded;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class BookingDetailsId implements Serializable {
	private int bookingId;
	private int seatTypeId;
	
	
	
	public BookingDetailsId() {
		super();
	}
	public BookingDetailsId(int bookingId, int seatTypeId) {
		super();
		this.bookingId = bookingId;
		this.seatTypeId = seatTypeId;
	}
	
	
	
	public int getBookingId() {
		return bookingId;
	}
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
	public int getSeatTypeId() {
		return seatTypeId;
	}
	public void setSeatTypeId(int seatTypeId) {
		this.seatTypeId = seatTypeId;
	}
	@Override
	public int hashCode() {
		return Objects.hash(bookingId, seatTypeId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookingDetailsId other = (BookingDetailsId) obj;
		return bookingId == other.bookingId && seatTypeId == other.seatTypeId;
	}
	
	
}
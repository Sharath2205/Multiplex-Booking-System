package com.multiplex.entity;

import com.multiplex.embedded.BookingDetailsId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity
public class BookingDetails {
	@EmbeddedId
	private BookingDetailsId id;
	
	@Column(name = "no_of_seats")
	private int noOfSeats;
	
	@ManyToOne
    @MapsId("bookingId")
    @JoinColumn(name = "booking_id")
	private Booking booking;
	
	@ManyToOne
    @MapsId("seatTypeId")
    @JoinColumn(name = "seat_type_id")
	private SeatType seatType;

	public BookingDetails(BookingDetailsId id, int noOfSeats, Booking booking, SeatType seatType) {
		super();
		this.id = id;
		this.noOfSeats = noOfSeats;
		this.booking = booking;
		this.seatType = seatType;
	}

	public BookingDetailsId getId() {
		return id;
	}

	public void setId(BookingDetailsId id) {
		this.id = id;
	}

	public int getNoOfSeats() {
		return noOfSeats;
	}

	public void setNoOfSeats(int noOfSeats) {
		this.noOfSeats = noOfSeats;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public SeatType getSeatType() {
		return seatType;
	}

	public void setSeatType(SeatType seatType) {
		this.seatType = seatType;
	}

	@Override
	public String toString() {
		return "BookingDetails [id=" + id + ", noOfSeats=" + noOfSeats + ", booking=" + booking + ", seatType="
				+ seatType + "]";
	}
	
	
}

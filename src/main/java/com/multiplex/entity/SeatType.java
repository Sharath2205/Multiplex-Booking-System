package com.multiplex.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "seatTypeId")
public class SeatType {
	@Id
	@Column(name = "seat_type_id")
	private int seatTypeId;
	
	@Column(name = "seat_type_desc", length = 225)
	private String seatTypeDesc;
	
	@Column(name = "seat_fare")
	private float seatFare;
	
	@OneToMany(mappedBy = "seatType")
	private List<BookingDetails> bookingDetails;
	
	@OneToMany(mappedBy = "seatType")
	private List<HallCapacity> hallCapacities;
	
	

	public SeatType() {
		super();
	}

	public SeatType(int seatTypeId, String seatTypeDesc, float seatFare, List<BookingDetails> bookingDetails,
			List<HallCapacity> hallCapacities) {
		super();
		this.seatTypeId = seatTypeId;
		this.seatTypeDesc = seatTypeDesc;
		this.seatFare = seatFare;
		this.bookingDetails = bookingDetails;
		this.hallCapacities = hallCapacities;
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

	public List<BookingDetails> getBookingDetails() {
		return bookingDetails;
	}

	public void setBookingDetails(List<BookingDetails> bookingDetails) {
		this.bookingDetails = bookingDetails;
	}

	public List<HallCapacity> getHallCapacities() {
		return hallCapacities;
	}

	public void setHallCapacities(List<HallCapacity> hallCapacities) {
		this.hallCapacities = hallCapacities;
	}

	@Override
	public String toString() {
		return "SeatType [seatTypeId=" + seatTypeId + ", seatTypeDesc=" + seatTypeDesc + ", seatFare=" + seatFare
				+ ", bookingDetails=" + bookingDetails + ", hallCapacities=" + hallCapacities + "]";
	}
	
	
}

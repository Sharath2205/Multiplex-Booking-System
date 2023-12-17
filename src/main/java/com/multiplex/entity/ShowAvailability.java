package com.multiplex.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "availabilityId")
public class ShowAvailability {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "availability_id")
	private int availabilityId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "show_id")
	private Show show;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hall_id")
	private Hall hall;

	@ManyToOne
	@JoinColumn(name = "seat_type_id")
	private SeatType seatType;

	@Column(name = "availability_date")
	private LocalDate availabilityDate;

	@Column(name = "total_seats")
	private int totalSeats;

	@Column(name = "remaining_seats")
	private int remainingSeats;

	public ShowAvailability() {
		super();
	}
	
	

	public ShowAvailability(int availabilityId, Show show, Hall hall, SeatType seatType, LocalDate availabilityDate,
			int totalSeats, int remainingSeats) {
		super();
		this.availabilityId = availabilityId;
		this.show = show;
		this.hall = hall;
		this.seatType = seatType;
		this.availabilityDate = availabilityDate;
		this.totalSeats = totalSeats;
		this.remainingSeats = remainingSeats;
	}



	public int getAvailabilityId() {
		return availabilityId;
	}

	public void setAvailabilityId(int availabilityId) {
		this.availabilityId = availabilityId;
	}

	public Show getShow() {
		return show;
	}

	public void setShow(Show show) {
		this.show = show;
	}

	public Hall getHall() {
		return hall;
	}

	public void setHall(Hall hall) {
		this.hall = hall;
	}

	public SeatType getSeatType() {
		return seatType;
	}

	public void setSeatType(SeatType seatType) {
		this.seatType = seatType;
	}

	public LocalDate getAvailabilityDate() {
		return availabilityDate;
	}

	public void setAvailabilityDate(LocalDate availabilityDate) {
		this.availabilityDate = availabilityDate;
	}

	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}

	public int getRemainingSeats() {
		return remainingSeats;
	}

	public void setRemainingSeats(int remainingSeats) {
		this.remainingSeats = remainingSeats;
	}
}

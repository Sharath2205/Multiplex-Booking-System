package com.multiplex.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Earnings {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "booking_id")
	private int bookingId;

	@Column(name = "status")
	private String status;

	@Column(name = "booking_cost")
	private double totalBookingCost;

	@Column(name = "seats_booked")
	private int seatsBooked;
	
	@Column(name = "booking_date")
	private LocalDate bookingDate;

	public Earnings() {
		super();
	}

	public Earnings(int bookingId, String status, double totalBookingCost, int seatsBooked, LocalDate bookingDate) {
		super();
		this.bookingId = bookingId;
		this.status = status;
		this.totalBookingCost = totalBookingCost;
		this.seatsBooked = seatsBooked;
		this.bookingDate = bookingDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getTotalBookingCost() {
		return totalBookingCost;
	}

	public void setTotalBookingCost(double totalBookingCost) {
		this.totalBookingCost = totalBookingCost;
	}

	public int getSeatsBooked() {
		return seatsBooked;
	}

	public void setSeatsBooked(int seatsBooked) {
		this.seatsBooked = seatsBooked;
	}

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}

	@Override
	public String toString() {
		return "Earnings [id=" + id + ", bookingId=" + bookingId + ", status=" + status + ", totalBookingCost="
				+ totalBookingCost + ", seatsBooked=" + seatsBooked + ", bookingDate=" + bookingDate + "]";
	}
	
	
	
}

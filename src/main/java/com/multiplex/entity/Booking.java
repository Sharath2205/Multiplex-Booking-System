package com.multiplex.entity;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "booking")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "hallId")
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "booking_id")
	private int bookingId;

	@Column(name = "booked_date")
	private Date bookedDate;

	@Column(name = "show_date")
	private Date showDate;

	@ManyToOne
	@JoinColumn(name = "show_id")
	private Shows shows;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public Date getBookedDate() {
		return bookedDate;
	}

	public void setBookedDate(Date bookedDate) {
		this.bookedDate = bookedDate;
	}

	public Date getShowDate() {
		return showDate;
	}

	public void setShowDate(Date showDate) {
		this.showDate = showDate;
	}

	public Shows getShows() {
		return shows;
	}

	public void setShows(Shows shows) {
		this.shows = shows;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<BookingDetails> getBookingDetails() {
		return bookingDetails;
	}

	public void setBookingDetails(List<BookingDetails> bookingDetails) {
		this.bookingDetails = bookingDetails;
	}

	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", bookedDate=" + bookedDate + ", showDate=" + showDate + ", shows="
				+ shows + ", user=" + user + ", bookingDetails=" + bookingDetails + "]";
	}

	public Booking(int bookingId, Date bookedDate, Date showDate, Shows shows, User user,
			List<BookingDetails> bookingDetails) {
		super();
		this.bookingId = bookingId;
		this.bookedDate = bookedDate;
		this.showDate = showDate;
		this.shows = shows;
		this.user = user;
		this.bookingDetails = bookingDetails;
	}

	@OneToMany(mappedBy = "booking")
	private List<BookingDetails> bookingDetails;
}

package com.multiplex.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "booking")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "bookingId")
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "booking_id")
	private int bookingId;

	@Column(name = "booked_date")
	private LocalDateTime bookedDate;

	@Column(name = "show_date")
	private LocalDate showDate;

	@ManyToOne
	@JoinColumn(name = "show_id")
	@JsonManagedReference(value = "show_id")
	private Show shows;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
	private List<BookingDetails> bookingDetails;

	public Booking() {
	}

	public Booking(int bookingId, LocalDateTime bookedDate, LocalDate showDate, Show shows, User user,
			List<BookingDetails> bookingDetails) {
		super();
		this.bookingId = bookingId;
		this.bookedDate = bookedDate;
		this.showDate = showDate;
		this.shows = shows;
		this.user = user;
		this.bookingDetails = bookingDetails;
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public LocalDateTime getBookedDate() {
		return bookedDate;
	}

	public void setBookedDate(LocalDateTime bookedDate) {
		this.bookedDate = bookedDate;
	}

	public LocalDate getShowDate() {
		return showDate;
	}

	public void setShowDate(LocalDate showDate) {
		this.showDate = showDate;
	}

	public Show getShows() {
		return shows;
	}

	public void setShows(Show shows) {
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
	
	@Transient
    public String getMovieName() {
        return shows.getMovie().getMovieName();
    }

    @Transient
    public String getHallDesc() {
        return shows.getHall().getHallDesc();
    }

    @Transient
    public int getSlotNo() {
        return shows.getSlotNo();
    }

    @Transient
    public LocalDate getBookingDate() {
        return shows.getFromDate();
    }

	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", bookedDate=" + bookedDate + ", showDate=" + showDate + ", shows="
				+ shows.getShowId() + ", user=" + user.getEmailId() + ", bookingDetails=" + bookingDetails.isEmpty() + "]";
	}
}

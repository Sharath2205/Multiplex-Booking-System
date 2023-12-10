package com.multiplex.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "showId")
public class Shows {
	@Id
	@Column(name = "show_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int showId;
	
	@ManyToOne
	@JoinColumn(name = "hall_id")
	private Hall hall;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "movie_id")
	private Movies movie;
	
	@Column(name = "slot_no")
	private int slotNo;
	
	@Column(name = "from_date")
	private LocalDate fromDate;
	
	@Column(name = "to_date")
	private LocalDate toDate;
	
	
	@OneToMany(mappedBy = "shows")
	private List<Booking> booking;

	public Shows() {
		super();
	}

	public Shows(int showId, int slotNo, LocalDate fromDate, LocalDate toDate) {
		super();
		this.showId = showId;
//		this.hall = hall;
		this.slotNo = slotNo;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	public Shows(int showId, Hall hall, Movies movie, int slotNo, LocalDate fromDate, LocalDate toDate) {
		super();
		this.showId = showId;
		this.hall = hall;
		this.movie = movie;
		this.slotNo = slotNo;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	public int getShowId() {
		return showId;
	}

	public void setShowId(int showId) {
		this.showId = showId;
	}

	public Hall getHall() {
		return hall;
	}

	public void setHall(Hall hall) {
		this.hall = hall;
	}

	public Movies getMovie() {
		return movie;
	}

	public void setMovie(Movies movie) {
		this.movie = movie;
	}

	public int getSlotNo() {
		return slotNo;
	}

	public void setSlotNo(int slotNo) {
		this.slotNo = slotNo;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public List<Booking> getBooking() {
		return booking;
	}

	public void setBooking(List<Booking> booking) {
		this.booking = booking;
	}

	@Override
	public String toString() {
		return "Shows [showId=" + showId + ", slotNo=" + slotNo + ", fromDate="
				+ fromDate + ", toDate=" + toDate + "]";
	}
}

package com.multiplex.entity;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.multiplex.embedded.HallCapacityId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Data;

@Entity
@Data
public class HallCapacity {
	@EmbeddedId
	@JsonIgnore
	private HallCapacityId hallCapacityId;
	
	@ManyToOne
	@MapsId("hallId")
	@JoinColumn(name = "hall_id")
	@JsonIdentityReference(alwaysAsId = true)
	private Hall hall;
	
	@ManyToOne
	@MapsId("seatTypeId")
	@JoinColumn(name = "seat_type_id")
	@JsonIdentityReference(alwaysAsId = true)
	private SeatType seatType;
	
	@Column(name = "seat_count")
	private int seatCount;
	
	

	public HallCapacityId getHallCapacityId() {
		return hallCapacityId;
	}

	public void setHallCapacityId(HallCapacityId hallCapacityId) {
		this.hallCapacityId = hallCapacityId;
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

	public int getSeatCount() {
		return seatCount;
	}

	public void setSeatCount(int seatCount) {
		this.seatCount = seatCount;
	}

	public HallCapacity(HallCapacityId hallCapacityId, Hall hall, SeatType seatType, int seatCount) {
		super();
		this.hallCapacityId = hallCapacityId;
		this.hall = hall;
		this.seatType = seatType;
		this.seatCount = seatCount;
	}

	public HallCapacity(Hall hall, SeatType seatType, int seatCount) {
		super();
		this.hall = hall;
		this.seatType = seatType;
		this.seatCount = seatCount;
	}

	public HallCapacity() {
	}

	@Override
	public String toString() {
		return "HallCapacity [hallCapacityId=" + hallCapacityId + ", hall=" + hall + ", seatType=" + seatType
				+ ", seatCount=" + seatCount + "]";
	}
	
	
}

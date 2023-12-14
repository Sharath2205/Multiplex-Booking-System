package com.multiplex.embedded;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.multiplex.entity.Hall;
import com.multiplex.entity.SeatType;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@SuppressWarnings("unused")
@JsonIdentityReference(alwaysAsId = true)
public class HallCapacityId implements Serializable {
	private static final long serialVersionUID = 1L;
	private int hallId;
	private int seatTypeId;

	public HallCapacityId() {
		super();
	}

	public HallCapacityId(int hallId, int seatTypeId) {
		super();
		this.hallId = hallId;
		this.seatTypeId = seatTypeId;
	}

	public int getHallId() {
		return hallId;
	}

	public void setHallId(int hallId) {
		this.hallId = hallId;
	}

	public int getSeatTypeId() {
		return seatTypeId;
	}

	public void setSeatTypeId(int seatTypeId) {
		this.seatTypeId = seatTypeId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(hallId, seatTypeId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HallCapacityId other = (HallCapacityId) obj;
		return hallId == other.hallId && seatTypeId == other.seatTypeId;
	}
	
	

}

package com.multiplex.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "hallId")
public class Hall {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int hallId;
	
	@Column(name = "hall_desc", length = 255)
	private String hallDesc;
	
	@Column(name = "total_capacity")
	private int totalCapacity;
	
	@OneToMany(mappedBy = "hall", cascade = CascadeType.ALL)
	private List<HallCapacity> hallCapacities;
	
	@OneToMany(mappedBy = "hall", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private List<Show> shows;
	
	@OneToMany(mappedBy = "hall", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<ShowAvailability> showAvailabilities;
	
	public List<ShowAvailability> getShowAvailabilities() {
		return showAvailabilities;
	}

	public void setShowAvailabilities(List<ShowAvailability> showAvailabilities) {
		this.showAvailabilities = showAvailabilities;
	}

	public Hall() {
		super();
	}

	public Hall(int hallId, String hallDesc, int totalCapacity) {
		super();
		this.hallId = hallId;
		this.hallDesc = hallDesc;
		this.totalCapacity = totalCapacity;
	}
	
	public Hall(int hallId, String hallDesc, int totalCapacity, List<HallCapacity> hallCapacities, List<Show> shows) {
		super();
		this.hallId = hallId;
		this.hallDesc = hallDesc;
		this.totalCapacity = totalCapacity;
		this.hallCapacities = hallCapacities;
		this.shows = shows;
	}


	public int getHallId() {
		return hallId;
	}

	public void setHallId(int hallId) {
		this.hallId = hallId;
	}

	public String getHallDesc() {
		return hallDesc;
	}

	public void setHallDesc(String hallDesc) {
		this.hallDesc = hallDesc;
	}

	public int getTotalCapacity() {
		return totalCapacity;
	}

	public void setTotalCapacity(int totalCapacity) {
		this.totalCapacity = totalCapacity;
	}

	public List<HallCapacity> getHallCapacities() {
		return hallCapacities;
	}

	public void setHallCapacities(List<HallCapacity> hallCapacities) {
		this.hallCapacities = hallCapacities;
	}

	public List<Show> getShows() {
		return shows;
	}

	public void setShows(List<Show> shows) {
		this.shows = shows;
	}

	@Override
	public String toString() {
		return "Hall [hallId=" + hallId + ", hallDesc=" + hallDesc + ", totalCapacity=" + totalCapacity
				+ "]";
	}
	
	
}

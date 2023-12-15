package com.multiplex.dto;

import java.time.LocalDate;
import java.util.Map;

public class EarningsOutputDto {
	private Map<LocalDate, Integer> seatsBookedPerDay;
	private Map<LocalDate, Double> totalEarningsPerDay;
	private Map<LocalDate, Double> cancellationChargesPerDay;
	private double totalBookingCharges;
	private double totalCancellationCharges;
	private double totalEarnings;
	public EarningsOutputDto() {
		super();
	}
	public EarningsOutputDto(Map<LocalDate, Integer> seatsBookedPerDay, Map<LocalDate, Double> totalEarningsPerDay,
			Map<LocalDate, Double> cancellationChargesPerDay, double totalBookingCharges,
			double totalCancellationCharges, double totalEarnings) {
		super();
		this.seatsBookedPerDay = seatsBookedPerDay;
		this.totalEarningsPerDay = totalEarningsPerDay;
		this.cancellationChargesPerDay = cancellationChargesPerDay;
		this.totalBookingCharges = totalBookingCharges;
		this.totalCancellationCharges = totalCancellationCharges;
		this.totalEarnings = totalEarnings;
	}
	public Map<LocalDate, Integer> getSeatsBookedPerDay() {
		return seatsBookedPerDay;
	}
	public void setSeatsBookedPerDay(Map<LocalDate, Integer> seatsBookedPerDay) {
		this.seatsBookedPerDay = seatsBookedPerDay;
	}
	public Map<LocalDate, Double> getTotalEarningsPerDay() {
		return totalEarningsPerDay;
	}
	public void setTotalEarningsPerDay(Map<LocalDate, Double> totalEarningsPerDay) {
		this.totalEarningsPerDay = totalEarningsPerDay;
	}
	public Map<LocalDate, Double> getCancellationChargesPerDay() {
		return cancellationChargesPerDay;
	}
	public void setCancellationChargesPerDay(Map<LocalDate, Double> cancellationChargesPerDay) {
		this.cancellationChargesPerDay = cancellationChargesPerDay;
	}
	public double getTotalBookingCharges() {
		return totalBookingCharges;
	}
	public void setTotalBookingCharges(double totalBookingCharges) {
		this.totalBookingCharges = totalBookingCharges;
	}
	public double getTotalCancellationCharges() {
		return totalCancellationCharges;
	}
	public void setTotalCancellationCharges(double totalCancellationCharges) {
		this.totalCancellationCharges = totalCancellationCharges;
	}
	public double getTotalEarnings() {
		return totalEarnings;
	}
	public void setTotalEarnings(double totalEarnings) {
		this.totalEarnings = totalEarnings;
	}

	
	
}

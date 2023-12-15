package com.multiplex.dto;

import java.time.LocalDate;
import java.util.Map;

public class EarningsOutputDto {
	private Map<LocalDate, Integer> seatsBookedPerDate;
	private Map<LocalDate, Double> totalFaresPerDate;
	private Map<LocalDate, Double> cancellationChargesPerDate;
	private double totalBookingCharges;
	private double totalCancellationCharges;
	private double totalEarnings;

	public EarningsOutputDto() {
		super();
	}

	public EarningsOutputDto(Map<LocalDate, Integer> seatsBookedPerDate, Map<LocalDate, Double> totalFaresPerDate,
			Map<LocalDate, Double> cancellationChargesPerDate, double totalBookingCharges, double totalCancellationCharges,
			double totalEarnings) {
		super();
		this.seatsBookedPerDate = seatsBookedPerDate;
		this.totalFaresPerDate = totalFaresPerDate;
		this.cancellationChargesPerDate = cancellationChargesPerDate;
		this.totalBookingCharges = totalBookingCharges;
		this.totalCancellationCharges = totalCancellationCharges;
		this.totalEarnings = totalEarnings;
	}

	public Map<LocalDate, Integer> getSeatsBookedPerDate() {
		return seatsBookedPerDate;
	}

	public void setSeatsBookedPerDate(Map<LocalDate, Integer> seatsBookedPerDate) {
		this.seatsBookedPerDate = seatsBookedPerDate;
	}

	public Map<LocalDate, Double> getTotalFaresPerDate() {
		return totalFaresPerDate;
	}

	public void setTotalFaresPerDate(Map<LocalDate, Double> totalFaresPerDate) {
		this.totalFaresPerDate = totalFaresPerDate;
	}

	public Map<LocalDate, Double> getCancellationChargesPerDate() {
		return cancellationChargesPerDate;
	}

	public void setCancellationChargesPerDate(Map<LocalDate, Double> cancellationChargesPerDate) {
		this.cancellationChargesPerDate = cancellationChargesPerDate;
	}

	public double getTotalEarnings() {
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

	public double getNetEarnings() {
		return totalEarnings;
	}

	public void setTotalEarnings(double totalEarnings) {
		this.totalEarnings = totalEarnings;
	}

	@Override
	public String toString() {
		return "EarningsOutputDto [seatsBookedPerDate=" + seatsBookedPerDate + ", totalFaresPerDate="
				+ totalFaresPerDate + ", cancellationChargesPerDate=" + cancellationChargesPerDate
				+ ", totalBookingCharges=" + totalBookingCharges + ", totalCancellationCharges="
				+ totalCancellationCharges + ", totalEarnings=" + totalEarnings + "]";
	}

}

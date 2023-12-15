package com.multiplex.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multiplex.dto.EarningsInputDto;
import com.multiplex.dto.EarningsOutputDto;
import com.multiplex.entity.Earnings;
import com.multiplex.repository.EarningsRepository;

@Service
public class EarningsServiceImpl {
	
	@Autowired
	private EarningsRepository earningsRepository;
	
	public EarningsOutputDto generateEarningsReport(EarningsInputDto earningsInputDto) {
		List<Earnings> earningsList = earningsRepository.findByBookingDateBetween(earningsInputDto.getFromDate(), earningsInputDto.getToDate());
		
		Map<LocalDate, Integer> seatsBookedPerDay = new HashMap<>();
		Map<LocalDate, Double> totalEarningsPerDay = new HashMap<>();
		Map<LocalDate, Double> cancellationChargesPerDay = new HashMap<>();
		
		for(Earnings earning: earningsList) {
			seatsBookedPerDay.put(earning.getBookingDate(), seatsBookedPerDay.getOrDefault(earning.getBookingDate(), 0) + earning.getSeatsBooked());
			if(earning.getStatus().equals("booked")) 
				totalEarningsPerDay.put(earning.getBookingDate(), totalEarningsPerDay.getOrDefault(earning.getBookingDate(), 0.0) + earning.getTotalBookingCost());
				
			if(earning.getStatus().equals("cancelled"))
				cancellationChargesPerDay.put(earning.getBookingDate(), cancellationChargesPerDay.getOrDefault(earning.getBookingDate(), 0.0) + earning.getTotalBookingCost() * 0.2);
		}
		
		double totalBookingCharges = totalEarningsPerDay.values().stream().mapToDouble(a -> a).sum();
		double totalCancellationCharges = cancellationChargesPerDay.values().stream().mapToDouble(a -> a).sum();
		double totalEarnings = totalBookingCharges + totalCancellationCharges;
		
		EarningsOutputDto earningsOutputDto = new EarningsOutputDto();
		earningsOutputDto.setSeatsBookedPerDay(seatsBookedPerDay);
		earningsOutputDto.setTotalEarningsPerDay(totalEarningsPerDay);
		earningsOutputDto.setCancellationChargesPerDay(cancellationChargesPerDay);
		
		earningsOutputDto.setTotalBookingCharges(totalBookingCharges);
		earningsOutputDto.setTotalCancellationCharges(totalCancellationCharges);
		earningsOutputDto.setTotalEarnings(totalEarnings);
		return earningsOutputDto;
	}
	
}

package com.multiplex.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.multiplex.dto.EarningsInputDto;
import com.multiplex.dto.EarningsOutputDto;
import com.multiplex.entity.Earnings;
import com.multiplex.repository.EarningsRepository;
import com.multiplex.service.EarningsServiceImpl;

@SpringBootTest
class EarningsServiceTest {
	@Mock
	private EarningsRepository earningsRepository;

	@InjectMocks
	private EarningsServiceImpl earningsService;

	@Test
	void testGenerateEarningsReport() {
		EarningsInputDto inputDto = new EarningsInputDto();
		inputDto.setFromDate(LocalDate.of(2023, 1, 1));
		inputDto.setToDate(LocalDate.of(2023, 1, 31));

		List<Earnings> earningsList = createMockEarningsList();
		when(earningsRepository.findByBookingDateBetween(inputDto.getFromDate(), inputDto.getToDate()))
				.thenReturn(earningsList);

		EarningsOutputDto result = earningsService.generateEarningsReport(inputDto);

		assertNotNull(result);
		assertEquals(3, result.getSeatsBookedPerDay().size());
		assertEquals(2, result.getTotalEarningsPerDay().size());
		assertEquals(1, result.getCancellationChargesPerDay().size());
		assertEquals(130.0, result.getTotalBookingCharges());
		assertEquals(30.0, result.getTotalCancellationCharges());
		assertEquals(160.0, result.getTotalEarnings());
	}

	private List<Earnings> createMockEarningsList() {
		List<Earnings> earningsList = new ArrayList<>();

		earningsList.add(createEarnings("booked", LocalDate.of(2023, 1, 1), 50.0, 5));
		earningsList.add(createEarnings("booked", LocalDate.of(2023, 1, 2), 80.0, 8));

		earningsList.add(createEarnings("cancelled", LocalDate.of(2023, 1, 3), 100.0, 10));
		earningsList.add(createEarnings("cancelled", LocalDate.of(2023, 1, 3), 50.0, 5));

		return earningsList;
	}

	private Earnings createEarnings(String status, LocalDate bookingDate, double totalBookingCost, int seatsBooked) {
		Earnings earnings = new Earnings();
		earnings.setStatus(status);
		earnings.setBookingDate(bookingDate);
		earnings.setTotalBookingCost(totalBookingCost);
		earnings.setSeatsBooked(seatsBooked);
		return earnings;
	}
}

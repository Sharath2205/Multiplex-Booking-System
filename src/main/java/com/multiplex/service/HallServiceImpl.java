package com.multiplex.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.multiplex.dto.HallDto;
import com.multiplex.dto.HallPublishedDto;
import com.multiplex.dto.SeatTypeDto;
import com.multiplex.embedded.HallCapacityId;
import com.multiplex.entity.Hall;
import com.multiplex.entity.HallCapacity;
import com.multiplex.entity.SeatType;
import com.multiplex.exception.HallAlreadyExistsException;
import com.multiplex.exception.HallDeletionException;
import com.multiplex.exception.HallNotFoundException;
import com.multiplex.repository.HallCapacityRepository;
import com.multiplex.repository.HallRepository;
import com.multiplex.repository.SeatTypeRepository;
import com.multiplex.util.AppConstants;

@Service
public class HallServiceImpl implements HallService {

	@Autowired
	HallCapacityRepository hallCapacityRepository;

	@Autowired
	private HallRepository hallRepository;

	@Autowired
	private SeatTypeRepository seatTypeRepository;

	public HallPublishedDto addHallWithSeatTypes(HallDto hallDto) {
		if (hallDto.getHallDesc() != null && !hallDto.getHallDesc().isBlank()) {
			Optional<Hall> existingHall = hallRepository.findByHallDescIgnoreCase(hallDto.getHallDesc());
			if (existingHall.isEmpty()) {
				Hall hall = new Hall();
				hall.setHallDesc(hallDto.getHallDesc());
				hall.setTotalCapacity(hallDto.getSeatTypes().stream().mapToInt(i -> i.getSeatCount()).sum());

				hall = hallRepository.save(hall);

				List<HallCapacity> hallCapacities = new ArrayList<>();

				for (SeatTypeDto seatTypeDto : hallDto.getSeatTypes()) {
					SeatType existingSeatType = seatTypeRepository
							.findBySeatTypeDescIgnoreCase(seatTypeDto.getSeatTypeDesc());

					if (existingSeatType == null) {
						SeatType newSeatType = new SeatType();
						newSeatType.setSeatTypeDesc(seatTypeDto.getSeatTypeDesc());
						newSeatType.setSeatFare(seatTypeDto.getSeatFare());
						seatTypeRepository.save(newSeatType);

						existingSeatType = newSeatType;
					}

					HallCapacityId hallCapacityId = new HallCapacityId(hall.getHallId(),
							existingSeatType.getSeatTypeId());
					HallCapacity existingHallCapacity = hallCapacityRepository.findById(hallCapacityId).orElse(null);

					if (existingHallCapacity == null) {
						HallCapacity hallCapacity = new HallCapacity(hallCapacityId, hall, existingSeatType,
								seatTypeDto.getSeatCount());
						hallCapacityRepository.save(hallCapacity);
						hallCapacities.add(hallCapacity);
					} else {
						existingHallCapacity.setSeatCount(seatTypeDto.getSeatCount());
						hallCapacities.add(existingHallCapacity);
					}
				}

				hall.setHallCapacities(hallCapacities);
				hall = hallRepository.save(hall);
				
				return new HallPublishedDto(hall.getHallId(), hall.getHallDesc(), hall.getTotalCapacity(), hall.getHallCapacities());
			}
			throw new HallAlreadyExistsException(
					AppConstants.HALL_WITH_DESC_ALREADY_EXISTS.replace("#", hallDto.getHallDesc()));
		}
		throw new HallNotFoundException(AppConstants.HALL_DESC_NOT_FOUND);
	}

	public List<Hall> getAllHalls() {
		List<Hall> allHalls = hallRepository.findAll();
		if (allHalls.isEmpty())
			throw new HallNotFoundException(AppConstants.NO_HALLS_FOUND);
		return allHalls;
	}

	public Hall getHallById(int hallId) {
		return hallRepository.findById(hallId).orElseThrow(() -> new HallNotFoundException(
				AppConstants.HALL_WITH_ID_NOT_FOUND.replace("#", Integer.toString(hallId))));
	}

	public boolean deleteHallById(int hallId) {
		Optional<Hall> opHall = hallRepository.findById(hallId);
		if (opHall.isEmpty())
			throw new HallNotFoundException(AppConstants.HALL_WITH_ID_NOT_FOUND.replace("#", Integer.toString(hallId)));
		
		Hall hall = opHall.get();
		
		if(!hall.getShows().isEmpty()) throw new HallDeletionException(AppConstants.HALL_DELECTION_ERROR);
		
		long count = hallRepository.count();
		hallRepository.deleteById(hallId);
		return count > hallRepository.count();
	}
	
	public Hall getHallByHallDesc(String hallDesc) {
		return hallRepository.findByHallDescIgnoreCase(hallDesc).orElseThrow(() -> new HallNotFoundException(
				AppConstants.HALL_DESC_NOT_FOUND));
	}
}

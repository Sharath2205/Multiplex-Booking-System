package com.multiplex.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.multiplex.dto.HallDto;
import com.multiplex.dto.HallWithSeatTypesDTO;
import com.multiplex.dto.SeatTypeWithCapacityDTO;
import com.multiplex.embedded.HallCapacityId;
import com.multiplex.entity.Hall;
import com.multiplex.entity.HallCapacity;
import com.multiplex.entity.SeatType;
import com.multiplex.exception.HallAlreadyExistsException;
import com.multiplex.iservice.IHallInterface;
import com.multiplex.repository.HallCapacityRepository;
import com.multiplex.repository.HallRepository;
import com.multiplex.repository.SeatTypeRepository;

@Service
public class HallService implements IHallInterface {

	@Autowired
	HallRepository hallRepository;

	@Autowired
	SeatTypeRepository seatTypeRepository;

	@Autowired
	HallCapacityRepository hallCapacityRepository;

	public Hall addHallWithSeatTypes(HallDto hallDto) {
		Optional<Hall> existingHall = hallRepository.findByHallDescIgnoreCase(hallDto.getHallDesc());
		if (existingHall.isEmpty()) {
			List<Integer> seatTypeIds = hallDto.getSeatTypeIds();
			List<Integer> seatCounts = hallDto.getSeatCounts();
			Hall hall = new Hall();
			hall.setHallDesc(hallDto.getHallDesc());
			hall.setTotalCapacity(hallDto.getTotalCapacity());

			hall = hallRepository.save(hall);

			for (int i = 0; i < seatTypeIds.size(); i++) {
				SeatType seatType = seatTypeRepository.findById(seatTypeIds.get(i)).get();

				HallCapacity hallCapacity = new HallCapacity();
				HallCapacityId hallCapacityId = new HallCapacityId();
				hallCapacityId.setHallId(hall.getHallId());
				hallCapacityId.setSeatTypeId(seatType.getSeatTypeId());

				hallCapacity.setHallCapacityId(hallCapacityId);
				hallCapacity.setHall(hall);
				hallCapacity.setSeatType(seatType);
				hallCapacity.setSeatCount(seatCounts.get(i));

				hallCapacityRepository.save(hallCapacity);
			}

			hall.setHallCapacities(hallCapacityRepository.findByHall(hall));

			return hall;
		}
		throw new HallAlreadyExistsException("Hall " + hallDto.getHallDesc() + " already exists");
	}
	
	 @Transactional
	    public HallWithSeatTypesDTO getHallWithSeatTypes(int hallId) {
	        Hall hall = hallRepository.findById(hallId).orElse(null);

	        if (hall != null) {
	            List<HallCapacity> hallCapacities = hallCapacityRepository.findByHall(hall);
	            List<SeatTypeWithCapacityDTO> seatTypes = hallCapacities.stream()
	                    .map(this::convertToSeatTypeWithCapacityDTO)
	                    .toList();

	            HallWithSeatTypesDTO hallWithSeatTypesDTO = new HallWithSeatTypesDTO();
	            hallWithSeatTypesDTO.setHallId(hall.getHallId());
	            hallWithSeatTypesDTO.setHallDesc(hall.getHallDesc());
	            hallWithSeatTypesDTO.setTotalCapacity(hall.getTotalCapacity());
	            hallWithSeatTypesDTO.setSeatTypes(seatTypes);

	            return hallWithSeatTypesDTO;
	        }

	        return null;
	    }

	    private SeatTypeWithCapacityDTO convertToSeatTypeWithCapacityDTO(HallCapacity hallCapacity) {
	        SeatTypeWithCapacityDTO seatTypeDTO = new SeatTypeWithCapacityDTO();
	        seatTypeDTO.setSeatTypeId(hallCapacity.getSeatType().getSeatTypeId());
	        seatTypeDTO.setSeatTypeDesc(hallCapacity.getSeatType().getSeatTypeDesc());
	        seatTypeDTO.setSeatFare(hallCapacity.getSeatType().getSeatFare());
	        seatTypeDTO.setSeatCount(hallCapacity.getSeatCount());
	        return seatTypeDTO;
	    }
}

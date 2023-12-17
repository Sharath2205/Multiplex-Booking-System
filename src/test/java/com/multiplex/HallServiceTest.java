package com.multiplex;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.multiplex.dto.HallDto;
import com.multiplex.dto.HallPublishedDto;
import com.multiplex.dto.SeatTypeDto;
import com.multiplex.embedded.HallCapacityId;
import com.multiplex.entity.Hall;
import com.multiplex.entity.HallCapacity;
import com.multiplex.entity.Movies;
import com.multiplex.entity.SeatType;
import com.multiplex.entity.Show;
import com.multiplex.exception.HallAlreadyExistsException;
import com.multiplex.exception.HallDeletionException;
import com.multiplex.exception.HallNotFoundException;
import com.multiplex.repository.HallCapacityRepository;
import com.multiplex.repository.HallRepository;
import com.multiplex.repository.SeatTypeRepository;
import com.multiplex.service.HallServiceImpl;

@SpringBootTest
class HallServiceTest {

    @Mock
    private HallRepository hallRepository;

    @Mock
    private HallCapacityRepository hallCapacityRepository;

    @Mock
    private SeatTypeRepository seatTypeRepository;

    @InjectMocks
    private HallServiceImpl hallService;

    @Test
    void addHallWithSeatTypesTest() {
        HallDto hallDto = createHallDto();
        when(hallRepository.findByHallDescIgnoreCase(hallDto.getHallDesc())).thenReturn(Optional.empty());
        when(hallRepository.save(any())).thenReturn(createHall());

        HallPublishedDto result = hallService.addHallWithSeatTypes(hallDto);

        assertNotNull(result);
        assertEquals(hallDto.getHallDesc(), result.getHallDesc());
    }

    @Test
    void addHallWithSeatTypesHallAlreadyExistsExceptionTest() {
        HallDto hallDto = createHallDto();
        when(hallRepository.findByHallDescIgnoreCase(hallDto.getHallDesc())).thenReturn(Optional.of(new Hall()));

        assertThrows(HallAlreadyExistsException.class, () -> hallService.addHallWithSeatTypes(hallDto));
    }

    @Test
    void addHallWithSeatTypesHallDescNotFoundTest() {
        HallDto hallDto = new HallDto();

        assertThrows(HallNotFoundException.class, () -> hallService.addHallWithSeatTypes(hallDto));
    }

    @Test
    void getAllHallsTest() {
        List<Hall> hallList = createHallList();
        when(hallRepository.findAll()).thenReturn(hallList);

        List<Hall> result = hallService.getAllHalls();

        assertNotNull(result);
        assertEquals(hallList.size(), result.size());
    }

    @Test
    void getAllHallsHallNotFoundExceptionTest() {
        when(hallRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(HallNotFoundException.class, () -> hallService.getAllHalls());
    }

    @Test
    void getHallByIdTest() {
        int hallId = 1;
        Hall hall = createHall();
        when(hallRepository.findById(hallId)).thenReturn(Optional.of(hall));

        Hall result = hallService.getHallById(hallId);

        assertNotNull(result);
        assertEquals(hallId, result.getHallId());
    }

    @Test
    void getHallByIdHallNotFoundExceptionTest() {
        int hallId = 1;
        when(hallRepository.findById(hallId)).thenReturn(Optional.empty());

        assertThrows(HallNotFoundException.class, () -> hallService.getHallById(hallId));
    }

    @Test
    void deleteHallByIdTest() {
        int hallId = 1;
        Hall hall = createHall();
        when(hallRepository.findById(hallId)).thenReturn(Optional.of(hall));
        when(hallRepository.count()).thenReturn(1L, 0L);

        boolean result = hallService.deleteHallById(hallId);

        assertTrue(result);
    }

    @Test
    void deleteHallByIdHallNotFoundExceptionTest() {
        int hallId = 1;
        when(hallRepository.findById(hallId)).thenReturn(Optional.empty());

        assertThrows(HallNotFoundException.class, () -> hallService.deleteHallById(hallId));
    }

    @Test
    void deleteHallByIdHallDeletionException() {
        int hallId = 1;
        Hall hall = createHall();
        hall.getShows().add(createShow(hall, createMovie()));
        when(hallRepository.findById(hallId)).thenReturn(Optional.of(hall));

        assertThrows(HallDeletionException.class, () -> hallService.deleteHallById(hallId));
    }

    @Test
    void getHallByHallDescTest() {
        String hallDesc = "TestHall";
        Hall hall = createHall();
        when(hallRepository.findByHallDescIgnoreCase(hallDesc)).thenReturn(Optional.of(hall));

        Hall result = hallService.getHallByHallDesc(hallDesc);

        assertNotNull(result);
        assertEquals(hallDesc, result.getHallDesc());
    }

    @Test
    void getHallByHallDescHallNotFoundExceptionTest() {
        String hallDesc = "NonExistentHall";
        when(hallRepository.findByHallDescIgnoreCase(hallDesc)).thenReturn(Optional.empty());

        assertThrows(HallNotFoundException.class, () -> hallService.getHallByHallDesc(hallDesc));
    }
    
    @Test
    void addHallWithSeatTypesUpdateExistingSeatTypeTest() {
        HallDto hallDto = createHallDto();
        SeatTypeDto existingSeatTypeDto = new SeatTypeDto("ExistingType", 10.0f, 50);
        hallDto.getSeatTypes().add(existingSeatTypeDto);

        SeatType existingSeatType = new SeatType();
        existingSeatType.setSeatTypeDesc(existingSeatTypeDto.getSeatTypeDesc());
        existingSeatType.setSeatFare(existingSeatTypeDto.getSeatFare());
        when(seatTypeRepository.findBySeatTypeDescIgnoreCase(existingSeatTypeDto.getSeatTypeDesc()))
                .thenReturn(existingSeatType);

        when(hallRepository.findByHallDescIgnoreCase(hallDto.getHallDesc())).thenReturn(Optional.empty());
        when(hallRepository.save(any())).thenReturn(createHall());

        HallPublishedDto result = hallService.addHallWithSeatTypes(hallDto);

        assertNotNull(result);
        assertEquals(1, result.getHallCapacities().size());

        HallCapacity publishedHallCapacity = result.getHallCapacities().get(0);
        assertEquals(existingSeatType.getSeatTypeId(), publishedHallCapacity.getHallCapacityId().getSeatTypeId());
        assertEquals(existingSeatTypeDto.getSeatCount(), publishedHallCapacity.getSeatCount());
    }
    
    @Test
    void addHallWithSeatTypesCreateNewSeatTypeTest() {
        HallDto hallDto = createHallDto();
        SeatTypeDto newSeatTypeDto = new SeatTypeDto("NewType", 12.0f, 30);
        hallDto.getSeatTypes().add(newSeatTypeDto);

        when(seatTypeRepository.findBySeatTypeDescIgnoreCase(newSeatTypeDto.getSeatTypeDesc()))
                .thenReturn(null);

        when(hallRepository.findByHallDescIgnoreCase(hallDto.getHallDesc())).thenReturn(Optional.empty());
        when(hallRepository.save(any())).thenReturn(createHall());

        HallPublishedDto result = hallService.addHallWithSeatTypes(hallDto);

        assertNotNull(result);
        assertEquals(1, result.getHallCapacities().size());

        HallCapacity publishedHallCapacity = result.getHallCapacities().get(0);
        assertNotEquals(0, publishedHallCapacity.getHallCapacityId().getSeatTypeId()); // Ensure SeatTypeId is set
        assertEquals(newSeatTypeDto.getSeatCount(), publishedHallCapacity.getSeatCount());
    }
    
    @Test
    void addHallWithSeatTypesUpdateExistingHallCapacityTest() {
        HallDto hallDto = createHallDto();
        SeatTypeDto existingSeatTypeDto = new SeatTypeDto("ExistingType", 10.0f, 50);
        hallDto.getSeatTypes().add(existingSeatTypeDto);

        SeatType existingSeatType = new SeatType();
        existingSeatType.setSeatTypeDesc(existingSeatTypeDto.getSeatTypeDesc());
        existingSeatType.setSeatFare(existingSeatTypeDto.getSeatFare());
        when(seatTypeRepository.findBySeatTypeDescIgnoreCase(existingSeatTypeDto.getSeatTypeDesc()))
                .thenReturn(existingSeatType);

        HallCapacityId existingHallCapacityId = new HallCapacityId(1, existingSeatType.getSeatTypeId());
        HallCapacity existingHallCapacity = new HallCapacity(existingHallCapacityId, createHall(), existingSeatType, 20);
        when(hallCapacityRepository.findById(existingHallCapacityId)).thenReturn(Optional.of(existingHallCapacity));

        when(hallRepository.findByHallDescIgnoreCase(hallDto.getHallDesc())).thenReturn(Optional.empty());
        when(hallRepository.save(any())).thenReturn(createHall());

        HallPublishedDto result = hallService.addHallWithSeatTypes(hallDto);

        assertNotNull(result);
        assertEquals(1, result.getHallCapacities().size());

        HallCapacity publishedHallCapacity = result.getHallCapacities().get(0);
        assertEquals(existingHallCapacityId, publishedHallCapacity.getHallCapacityId());
        assertEquals(existingSeatTypeDto.getSeatCount(), publishedHallCapacity.getSeatCount());
    }




    private HallDto createHallDto() {
        HallDto hallDto = new HallDto();
        hallDto.setHallDesc("TestHall");
        hallDto.setSeatTypes(new ArrayList<>());
        return hallDto;
    }

    private List<Hall> createHallList() {
        List<Hall> hallList = new ArrayList<>();
        hallList.add(createHall());

        return hallList;
    }

    private Hall createHall() {
        Hall hall = new Hall();
        hall.setHallId(1);
        hall.setHallDesc("TestHall");
        hall.setShows(new ArrayList<>()); 

        return hall;
    }
    

    public static Show createShow(Hall hall, Movies movie) {
        Show show = new Show();
        show.setHall(hall);
        show.setMovie(movie);
        show.setSlotNo(1);
        show.setFromDate(LocalDate.now());
        show.setToDate(LocalDate.now().plusDays(7));
        
        return show;
    }
    
    private Movies createMovie() {
        Movies movie = new Movies();
        movie.setMovieId(1);
        movie.setMovieName("TestMovie");
        movie.setGenre("Action");
        return movie;
    }
}

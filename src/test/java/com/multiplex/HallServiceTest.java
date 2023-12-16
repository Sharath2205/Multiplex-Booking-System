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
        // Arrange
        HallDto hallDto = createHallDto();
        when(hallRepository.findByHallDescIgnoreCase(hallDto.getHallDesc())).thenReturn(Optional.empty());
        when(hallRepository.save(any())).thenReturn(createHall());
        // Act
        HallPublishedDto result = hallService.addHallWithSeatTypes(hallDto);

        // Assert
        assertNotNull(result);
        assertEquals(hallDto.getHallDesc(), result.getHallDesc());
        // Add more assertions as needed
    }

    @Test
    void addHallWithSeatTypesHallAlreadyExistsExceptionTest() {
        // Arrange
        HallDto hallDto = createHallDto();
        when(hallRepository.findByHallDescIgnoreCase(hallDto.getHallDesc())).thenReturn(Optional.of(new Hall()));

        // Act and Assert
        assertThrows(HallAlreadyExistsException.class, () -> hallService.addHallWithSeatTypes(hallDto));
    }

    @Test
    void addHallWithSeatTypesHallDescNotFoundTest() {
        // Arrange
        HallDto hallDto = new HallDto();

        // Act and Assert
        assertThrows(HallNotFoundException.class, () -> hallService.addHallWithSeatTypes(hallDto));
    }

    @Test
    void getAllHallsTest() {
        // Arrange
        List<Hall> hallList = createHallList();
        when(hallRepository.findAll()).thenReturn(hallList);

        // Act
        List<Hall> result = hallService.getAllHalls();

        // Assert
        assertNotNull(result);
        assertEquals(hallList.size(), result.size());
        // Add more assertions as needed
    }

    @Test
    void getAllHallsHallNotFoundExceptionTest() {
        // Arrange
        when(hallRepository.findAll()).thenReturn(new ArrayList<>());

        // Act and Assert
        assertThrows(HallNotFoundException.class, () -> hallService.getAllHalls());
    }

    @Test
    void getHallByIdTest() {
        // Arrange
        int hallId = 1;
        Hall hall = createHall();
        when(hallRepository.findById(hallId)).thenReturn(Optional.of(hall));

        // Act
        Hall result = hallService.getHallById(hallId);

        // Assert
        assertNotNull(result);
        assertEquals(hallId, result.getHallId());
        // Add more assertions as needed
    }

    @Test
    void getHallByIdHallNotFoundExceptionTest() {
        // Arrange
        int hallId = 1;
        when(hallRepository.findById(hallId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(HallNotFoundException.class, () -> hallService.getHallById(hallId));
    }

    @Test
    void deleteHallByIdTest() {
        // Arrange
        int hallId = 1;
        Hall hall = createHall();
        when(hallRepository.findById(hallId)).thenReturn(Optional.of(hall));
        when(hallRepository.count()).thenReturn(1L, 0L);

        // Act
        boolean result = hallService.deleteHallById(hallId);

        // Assert
        assertTrue(result);
    }

    @Test
    void deleteHallByIdHallNotFoundExceptionTest() {
        // Arrange
        int hallId = 1;
        when(hallRepository.findById(hallId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(HallNotFoundException.class, () -> hallService.deleteHallById(hallId));
    }

    @Test
    void deleteHallByIdHallDeletionException() {
        // Arrange
        int hallId = 1;
        Hall hall = createHall();
        hall.getShows().add(createShow(hall, createMovie()));
        when(hallRepository.findById(hallId)).thenReturn(Optional.of(hall));

        // Act and Assert
        assertThrows(HallDeletionException.class, () -> hallService.deleteHallById(hallId));
    }

    @Test
    void getHallByHallDescTest() {
        // Arrange
        String hallDesc = "TestHall";
        Hall hall = createHall();
        when(hallRepository.findByHallDescIgnoreCase(hallDesc)).thenReturn(Optional.of(hall));

        // Act
        Hall result = hallService.getHallByHallDesc(hallDesc);

        // Assert
        assertNotNull(result);
        assertEquals(hallDesc, result.getHallDesc());
        // Add more assertions as needed
    }

    @Test
    void getHallByHallDescHallNotFoundExceptionTest() {
        // Arrange
        String hallDesc = "NonExistentHall";
        when(hallRepository.findByHallDescIgnoreCase(hallDesc)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(HallNotFoundException.class, () -> hallService.getHallByHallDesc(hallDesc));
    }
    
    @Test
    void addHallWithSeatTypesUpdateExistingSeatTypeTest() {
        // Arrange
        HallDto hallDto = createHallDto();
        SeatTypeDto existingSeatTypeDto = new SeatTypeDto("ExistingType", 10.0f, 50);
        hallDto.getSeatTypes().add(existingSeatTypeDto);

        // Mock the SeatTypeRepository to return an existing SeatType
        SeatType existingSeatType = new SeatType();
        existingSeatType.setSeatTypeDesc(existingSeatTypeDto.getSeatTypeDesc());
        existingSeatType.setSeatFare(existingSeatTypeDto.getSeatFare());
        when(seatTypeRepository.findBySeatTypeDescIgnoreCase(existingSeatTypeDto.getSeatTypeDesc()))
                .thenReturn(existingSeatType);

//        Hall hall = createHall();
        when(hallRepository.findByHallDescIgnoreCase(hallDto.getHallDesc())).thenReturn(Optional.empty());
        when(hallRepository.save(any())).thenReturn(createHall());
        // Act
        HallPublishedDto result = hallService.addHallWithSeatTypes(hallDto);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getHallCapacities().size());

        HallCapacity publishedHallCapacity = result.getHallCapacities().get(0);
        assertEquals(existingSeatType.getSeatTypeId(), publishedHallCapacity.getHallCapacityId().getSeatTypeId());
        assertEquals(existingSeatTypeDto.getSeatCount(), publishedHallCapacity.getSeatCount());
    }
    
    @Test
    void addHallWithSeatTypesCreateNewSeatTypeTest() {
        // Arrange
        HallDto hallDto = createHallDto();
        SeatTypeDto newSeatTypeDto = new SeatTypeDto("NewType", 12.0f, 30);
        hallDto.getSeatTypes().add(newSeatTypeDto);

        // Mock the SeatTypeRepository to return null, indicating that the SeatType doesn't exist
        when(seatTypeRepository.findBySeatTypeDescIgnoreCase(newSeatTypeDto.getSeatTypeDesc()))
                .thenReturn(null);

        when(hallRepository.findByHallDescIgnoreCase(hallDto.getHallDesc())).thenReturn(Optional.empty());
        when(hallRepository.save(any())).thenReturn(createHall());

        // Act
        HallPublishedDto result = hallService.addHallWithSeatTypes(hallDto);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getHallCapacities().size());

        HallCapacity publishedHallCapacity = result.getHallCapacities().get(0);
        assertNotNull(publishedHallCapacity.getHallCapacityId().getSeatTypeId()); // Ensure SeatTypeId is set
        assertEquals(newSeatTypeDto.getSeatCount(), publishedHallCapacity.getSeatCount());
    }
    
    @Test
    void addHallWithSeatTypesUpdateExistingHallCapacityTest() {
        // Arrange
        HallDto hallDto = createHallDto();
        SeatTypeDto existingSeatTypeDto = new SeatTypeDto("ExistingType", 10.0f, 50);
        hallDto.getSeatTypes().add(existingSeatTypeDto);

        // Mock the SeatTypeRepository to return an existing SeatType
        SeatType existingSeatType = new SeatType();
        existingSeatType.setSeatTypeDesc(existingSeatTypeDto.getSeatTypeDesc());
        existingSeatType.setSeatFare(existingSeatTypeDto.getSeatFare());
        when(seatTypeRepository.findBySeatTypeDescIgnoreCase(existingSeatTypeDto.getSeatTypeDesc()))
                .thenReturn(existingSeatType);

        // Mock the HallCapacityRepository to return an existing HallCapacity
        HallCapacityId existingHallCapacityId = new HallCapacityId(1, existingSeatType.getSeatTypeId());
        HallCapacity existingHallCapacity = new HallCapacity(existingHallCapacityId, createHall(), existingSeatType, 20);
        when(hallCapacityRepository.findById(existingHallCapacityId)).thenReturn(Optional.of(existingHallCapacity));

        when(hallRepository.findByHallDescIgnoreCase(hallDto.getHallDesc())).thenReturn(Optional.empty());
        when(hallRepository.save(any())).thenReturn(createHall());

        // Act
        HallPublishedDto result = hallService.addHallWithSeatTypes(hallDto);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getHallCapacities().size());

        HallCapacity publishedHallCapacity = result.getHallCapacities().get(0);
        assertEquals(existingHallCapacityId, publishedHallCapacity.getHallCapacityId());
        assertEquals(existingSeatTypeDto.getSeatCount(), publishedHallCapacity.getSeatCount());
    }




    // Utility methods for creating test data
    private HallDto createHallDto() {
        HallDto hallDto = new HallDto();
        hallDto.setHallDesc("TestHall");
        hallDto.setSeatTypes(new ArrayList<>());        // Add more attributes as needed
        return hallDto;
    }

    private List<Hall> createHallList() {
        List<Hall> hallList = new ArrayList<>();
        hallList.add(createHall());
        // Add more halls as needed
        return hallList;
    }

    private Hall createHall() {
        Hall hall = new Hall();
        hall.setHallId(1);
        hall.setHallDesc("TestHall");
        hall.setShows(new ArrayList<>()); 
        // Add more attributes as needed
        return hall;
    }
    

    public static Show createShow(Hall hall, Movies movie) {
        Show show = new Show();
        show.setHall(hall);
        show.setMovie(movie);
        show.setSlotNo(1);
        show.setFromDate(LocalDate.now());
        show.setToDate(LocalDate.now().plusDays(7));
        // Add more attributes as needed
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

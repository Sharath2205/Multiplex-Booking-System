package com.multiplex.servicetest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.multiplex.dto.MoviesDto;
import com.multiplex.dto.PublishMovieDto;
import com.multiplex.entity.Hall;
import com.multiplex.entity.Movies;
import com.multiplex.entity.Show;
import com.multiplex.exception.MovieNotFoundException;
import com.multiplex.repository.MovieRepository;
import com.multiplex.service.MovieServiceImpl;

@SpringBootTest
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    @Test
    void getMovieDetailsByNameSuccess() {
        Movies movie = createTestMovie();

        when(movieRepository.findByMovieNameIgnoreCase("TestMovie")).thenReturn(Optional.of(movie));

        MoviesDto result = movieService.getMovieDetailsByName("TestMovie");

        assertNotNull(result);
        assertEquals(1, result.getShows().size());
        assertEquals("TestMovie", result.getMovieName());
        assertEquals("Main Hall", result.getShows().get(0).getHallDesc());
    }

    @Test
    void testGetMovieDetailsByName_Success_NoShows() {
        Movies movie = createTestMovie();
        when(movieRepository.findByMovieNameIgnoreCase("TestMovie")).thenReturn(Optional.of(movie));

        MoviesDto result = movieService.getMovieDetailsByName("TestMovie");

        assertNotNull(result);
        assertEquals(1, result.getShows().size());
        assertEquals("TestMovie", result.getMovieName());
    }

    @Test
    void testGetMovieDetailsByName_Failure_MovieNotFound() {
        when(movieRepository.findByMovieNameIgnoreCase("NonExistentMovie")).thenReturn(Optional.empty());

        assertThrows(MovieNotFoundException.class, () -> movieService.getMovieDetailsByName("NonExistentMovie"));
    }
    
    @Test
    void addMovieTest() {
    	when(movieRepository.save(any())).thenReturn(createTestMovie());
    	
    	PublishMovieDto movieDto = new PublishMovieDto("Testing movie", "Action");
    	
    	assertEquals("Movie added successfully", movieService.addMovie(movieDto));
    }
    
    @Test
    void testGetByMovieId_Success() {
        Movies expectedMovie = new Movies();
        expectedMovie.setMovieId(1);
        expectedMovie.setMovieName("TestMovie");
        expectedMovie.setGenre("Action");

        when(movieRepository.findById(1)).thenReturn(Optional.of(expectedMovie));

        Movies result = movieService.getByMovieId(1);

        assertNotNull(result);
        assertEquals(expectedMovie, result);
    }

    @Test
    void testGetByMovieId_MovieNotFound() {
        when(movieRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(MovieNotFoundException.class, () -> movieService.getByMovieId(1));
    }

    private Movies createTestMovie() {
        Movies movie = new Movies();
        movie.setMovieId(1);
        movie.setMovieName("TestMovie");
        movie.setGenre("Action");
        movie.setShows(Collections.singletonList(createTestShow(movie, createTestHall())));
        return movie;
    }

    private Hall createTestHall() {
        Hall hall = new Hall();
        hall.setHallId(1);
        hall.setHallDesc("Main Hall");
        hall.setTotalCapacity(100);
        return hall;
    }

    private Show createTestShow(Movies movie, Hall hall) {
        Show show = new Show();
        show.setShowId(1);
        show.setHall(hall);
        show.setMovie(movie);
        show.setSlotNo(1);
        show.setFromDate(LocalDate.now());
        show.setToDate(LocalDate.now().plusDays(7));
        movie.setShows(Collections.singletonList(show));
        hall.setShows(Collections.singletonList(show));
        return show;
    }

}

package com.multiplex.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.multiplex.dto.UserBookingDto;
import com.multiplex.dto.UserDashboardDto;
import com.multiplex.dto.UserDto;
import com.multiplex.dto.UserLoginDto;
import com.multiplex.dto.UserPasswordResetDto;
import com.multiplex.dto.UserProfileDto;
import com.multiplex.entity.Booking;
import com.multiplex.entity.Hall;
import com.multiplex.entity.Movies;
import com.multiplex.entity.Show;
import com.multiplex.entity.User;
import com.multiplex.exception.InsufficentInformationException;
import com.multiplex.exception.InvalidDateOfBirthException;
import com.multiplex.exception.InvalidEmailException;
import com.multiplex.exception.InvalidPasswordException;
import com.multiplex.exception.PasswordMismatchException;
import com.multiplex.exception.SameOldAndNewPasswordException;
import com.multiplex.exception.UserCreationException;
import com.multiplex.exception.UserNotFoundException;
import com.multiplex.repository.BookingRepository;
import com.multiplex.repository.ShowsRepository;
import com.multiplex.repository.UserRepository;
import com.multiplex.service.BookingService;
import com.multiplex.service.BookingServiceImpl;
import com.multiplex.service.UserServiceImpl;

@SpringBootTest
class UserServiceTest {
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private ShowsRepository showsRepository;
	
	@Mock
	private BookingRepository bookingRepository;
	
	@Mock
	private BookingServiceImpl bookingService;

	@InjectMocks
	private UserServiceImpl userService;

	@Test
	void registerUserTestSuccess() {
		UserDto userDto = createUserDto();
		when(userRepository.findByEmailIdIgnoreCase(any())).thenReturn(Optional.empty());
		when(userRepository.save(any(User.class))).thenReturn(createUser());

		UserDto result = userService.registerUser(userDto);

		assertEquals(true, result.getUserId() != 0);
	}

	@Test
	void registerUserUserAlreadyExistsExceptionTest() {
		UserDto userDto = createUserDto();
		when(userRepository.findByEmailIdIgnoreCase(any())).thenReturn(Optional.of(createUser()));
		assertThrows(UserCreationException.class, () -> userService.registerUser(userDto));
	}

	@Test
	void registerUserInvalidUserNameExceptionTest() {
		UserDto userDto = createUserDto();
		userDto.setUserName("1234");
		assertThrows(UserCreationException.class, () -> userService.registerUser(userDto));
	}

	@Test
	void registerUserInvalidPasswordExceptionTest() {
		UserDto userDto = createUserDto();
		userDto.setPassword("invalid_password");

		assertThrows(InvalidPasswordException.class, () -> userService.registerUser(userDto));
	}

	@Test
	void registerUserPasswordMismatchExceptionTest() {
		// Arrange
		UserDto userDto = createUserDto();
		userDto.setConfirmPassword("MismatchedPassword");

		// Act & Assert
		assertThrows(PasswordMismatchException.class, () -> userService.registerUser(userDto));
	}

	@Test
	void registerUserInvalidEmailExceptionTest() {
		// Arrange
		UserDto userDto = createUserDto();
		userDto.setEmailId("invalid_email");

		// Act & Assert
		assertThrows(InvalidEmailException.class, () -> userService.registerUser(userDto));
	}

	@Test
	void registerUserInvalidMobileNumberExceptionTest() {
		// Arrange
		UserDto userDto = createUserDto();
		userDto.setMobileNumber(123); // Invalid mobile number

		// Act & Assert
		assertThrows(UserCreationException.class, () -> userService.registerUser(userDto));
	}

	@Test
	void registerUserInvalidDateOfBirthExceptionTest() {
		// Arrange
		UserDto userDto = createUserDto();
		userDto.setDateOfBirth(LocalDate.now().plusDays(1)); // Future date

		// Act & Assert
		assertThrows(InvalidDateOfBirthException.class, () -> userService.registerUser(userDto));
	}

	@Test
	void updateUserInvalidMobileNumberTest() {
		UserProfileDto userProfileDto = createUserProfileDto();
		userProfileDto.setMobileNumber(123);
		when(userRepository.findByEmailIdIgnoreCase(any())).thenReturn(Optional.of(createUser()));
		assertThrows(InvalidPasswordException.class, () -> userService.updateUser(userProfileDto));
	}

	@Test
	void loginUserTestSuccess() {
		UserLoginDto loginDto = createUserLoginDto();
		when(userRepository.findByEmailIdIgnoreCase(any())).thenReturn(Optional.of(createUser()));

		String result = userService.loginUser(loginDto);

		assertEquals("Logged in successfully. Welcome TestingUser", result);
	}

	@Test
	void loginUserUserNotFoundExceptionTest() {
		UserLoginDto loginDto = createUserLoginDto();
		when(userRepository.findByEmailIdIgnoreCase(any())).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> userService.loginUser(loginDto));
	}

	@Test
	void loginUserInvalidPasswordExceptionTest() {
		UserLoginDto loginDto = createUserLoginDto();
		when(userRepository.findByEmailIdIgnoreCase(any())).thenReturn(Optional.of(createUser()));
		loginDto.setPassword("InvalidPassword");

		assertThrows(InvalidPasswordException.class, () -> userService.loginUser(loginDto));
	}

	@Test
	void resetPasswordSuccessTest() {
		UserPasswordResetDto resetDto = createUserPasswordResetDto();
		when(userRepository.findByEmailIdIgnoreCase(any())).thenReturn(Optional.of(createUser()));

		String result = userService.resetPassword(resetDto);

		assertEquals("Password Reset Successfully", result);
	}

	@Test
	void resetPasswordUserNotFoundExceptionTest() {
		UserPasswordResetDto resetDto = createUserPasswordResetDto();
		when(userRepository.findByEmailIdIgnoreCase(any())).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> userService.resetPassword(resetDto));
	}

	@Test
	void resetPasswordPasswordMismatchTest() {
		// Arrange
		UserPasswordResetDto resetDto = createUserPasswordResetDto();
		resetDto.setConfirmPassword("NotCorrectPass");
		when(userRepository.findByEmailIdIgnoreCase(any())).thenReturn(Optional.of(createUser()));

		// Act & Assert
		assertThrows(PasswordMismatchException.class, () -> userService.resetPassword(resetDto));
	}

	@Test
	void resetPasswordSameOldAndNewPasswordExceptionTest() {
		UserPasswordResetDto resetDto = createUserPasswordResetDto();
		resetDto.setPassword("Password123");
		resetDto.setConfirmPassword("Password123");
		when(userRepository.findByEmailIdIgnoreCase(any())).thenReturn(Optional.of(createUser()));

		assertThrows(SameOldAndNewPasswordException.class, () -> userService.resetPassword(resetDto));
	}

	@Test
	void resetPasswordInvalidPasswordExceptionTest() {
		UserPasswordResetDto resetDto = createUserPasswordResetDto();
		resetDto.setPassword("invalid_password");
		when(userRepository.findByEmailIdIgnoreCase(any())).thenReturn(Optional.of(createUser()));

		assertThrows(InvalidPasswordException.class, () -> userService.resetPassword(resetDto));
	}

	@Test
	void resetPasswordEnterNewAndConfirmPasswordExceptionTest() {
		UserPasswordResetDto resetDto = createUserPasswordResetDto();
		resetDto.setPassword(null);
		when(userRepository.findByEmailIdIgnoreCase(any())).thenReturn(Optional.of(createUser()));
		assertThrows(InvalidPasswordException.class, () -> userService.resetPassword(resetDto));
	}

	@Test
	void resetPasswordInvalidEmailExceptionTest() {
		UserPasswordResetDto resetDto = createUserPasswordResetDto();
		resetDto.setEmailId(null);
		when(userRepository.findByEmailIdIgnoreCase(any())).thenReturn(Optional.empty());
		assertThrows(InvalidEmailException.class, () -> userService.resetPassword(resetDto));
	}

	@Test
	void deleteUserSuccessTest() {
		UserLoginDto userDto = createUserLoginDto();
		when(userRepository.findByEmailIdIgnoreCase(any())).thenReturn(Optional.of(createUser()));

		String result = userService.deleteUser(userDto);

		assertEquals("Account deleted successfully", result);
	}

	@Test
	void deleteUserUserNotFoundExceptionTest() {
		// Arrange
		UserLoginDto userDto = createUserLoginDto();
		when(userRepository.findByEmailIdIgnoreCase(any())).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(UserNotFoundException.class, () -> userService.deleteUser(userDto));
	}

	@Test
	void deleteUserInvalidPasswordExceptionTest() {
		// Arrange
		UserLoginDto userDto = createUserLoginDto();
		when(userRepository.findByEmailIdIgnoreCase(any())).thenReturn(Optional.of(createUser()));
		userDto.setPassword("IncorrectPassword");

		// Act & Assert
		assertThrows(InvalidPasswordException.class, () -> userService.deleteUser(userDto));
	}

	@Test
	void deleteUserMissingEmailExceptionTest() {
		// Arrange
		UserLoginDto userDto = createUserLoginDto();
		userDto.setEmailId(null);

		// Act & Assert
		assertThrows(InsufficentInformationException.class, () -> userService.deleteUser(userDto));
	}

	@Test
	void deleteUserMissingPasswordExceptionTest() {
		// Arrange
		UserLoginDto userDto = createUserLoginDto();
		userDto.setPassword(null);

		// Act & Assert
		assertThrows(InsufficentInformationException.class, () -> userService.deleteUser(userDto));
	}

	@Test
	void updateUserSuccessTest() {
		// Arrange
		UserProfileDto userDto = createUserProfileDto();
		userDto.setUserName("updated user");

		when(userRepository.findByEmailIdIgnoreCase(any())).thenReturn(Optional.of(createUser()));
		when(userRepository.save(any())).thenReturn(createUser());

		// Act
		UserProfileDto result = userService.updateUser(userDto);

		// Assert
		assertNotNull(result);
		// Add additional assertions based on your specific requirements
	}

	@Test
	void updateUserUserNotFoundExceptionTest() {
		// Arrange
		UserProfileDto userDto = createUserProfileDto();
		when(userRepository.findByEmailIdIgnoreCase(any())).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(UserNotFoundException.class, () -> userService.updateUser(userDto));
	}

	@Test
	void updateUserInvalidUserNameExceptionTest() {
		// Arrange
		UserProfileDto userDto = createUserProfileDto();
		userDto.setUserName("123123123");
		when(userRepository.findByEmailIdIgnoreCase(any())).thenReturn(Optional.of(createUser()));

		// Act & Assert
		assertThrows(UserCreationException.class, () -> userService.updateUser(userDto));
	}

	@Test
	void updateUserInvalidMobileNumberExceptionTest() {
		UserProfileDto userDto = createUserProfileDto();
		userDto.setMobileNumber(123); // Invalid mobile number
		when(userRepository.findByEmailIdIgnoreCase(any())).thenReturn(Optional.ofNullable(createUser()));
		
		assertThrows(InvalidPasswordException.class, () -> userService.updateUser(userDto));
	}

	@Test
	void updateUserMobileNumberUpdateTest() {
		UserProfileDto userDto = createUserProfileDto();
		userDto.setUserName(null); // Exclude user name update
		when(userRepository.findByEmailIdIgnoreCase(any())).thenReturn(Optional.ofNullable(createUser()));
		when(userRepository.save(any())).thenReturn(createUser());

		UserProfileDto result = userService.updateUser(userDto);

		assertNotNull(result);
	}
	
	@Test
	void updateUserInsufficientExceptionTest() {
		UserProfileDto userDto = createUserProfileDto();
		userDto.setEmailId(null); 
		assertThrows(InsufficentInformationException.class, () -> userService.updateUser(userDto));
	}
	
	@Test
    void getAllUsersTest() {
        User user1 = new User("User1", "user1@example.com", 1234567890L);
        User user2 = new User("User2", "user2@example.com", 9876543210L);

        List<User> userList = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        assertEquals(userList, result);
    }
	
	@Test
	void userDashboardSuccessTest() {
	    String emailId = "testinguser@example.com";
	    User user = createUser();
	    when(userRepository.findByEmailIdIgnoreCase(emailId)).thenReturn(Optional.of(user));

	    List<Show> allShows = Arrays.asList(createShow(), createShow());
	    when(showsRepository.findAll()).thenReturn(allShows);

	    List<Booking> allBookings = Arrays.asList(createBooking(), createBooking());
	    when(bookingRepository.findAllByUser(user)).thenReturn(allBookings);
	    when(bookingService.getBookingByBookingId(user.getUserId(), user.getEmailId())).thenReturn(createSampleUserBookingDto());
	    
	    UserDashboardDto result = userService.userDashboard(emailId);

	    assertNotNull(result);
	    assertEquals(emailId, result.getEmail());
	    assertEquals(user.getUserName(), result.getName());
	    assertEquals(user.getMobileNumber(), result.getMobileNumber());
	}

	@Test
	void userDashboardUserNotFoundExceptionTest() {
	    // Arrange
	    String emailId = "nonexistentuser@example.com";
	    when(userRepository.findByEmailIdIgnoreCase(emailId)).thenReturn(Optional.empty());

	    // Act & Assert
	    assertThrows(InvalidEmailException.class, () -> userService.userDashboard(emailId));
	}
	
	@Test
	void userDashboardInvalidEmailExceptionTest() {
	    // Act & Assert
	    assertThrows(InvalidEmailException.class, () -> userService.userDashboard(null));
	    assertThrows(InvalidEmailException.class, () -> userService.userDashboard(""));
	}

	@Test
	void userDashboardPastShowDateTest() {
	    // Arrange
	    String emailId = "testinguser@example.com";
	    User user = createUser();
	    when(userRepository.findByEmailIdIgnoreCase(emailId)).thenReturn(Optional.of(user));

	    List<Show> allShows = Collections.singletonList(createPastShow());
	    when(showsRepository.findAll()).thenReturn(allShows);

	    // Act
	    UserDashboardDto result = userService.userDashboard(emailId);

	    // Assert
	    assertNotNull(result);
	    assertEquals(0, result.getLatestShows().size()); // No past shows should be included
	}


	// helper methods to create user and userdto obj

	private User createUser() {
		User user = new User();
		user.setUserId(1);
		user.setUserName("TestingUser");
		user.setEmailId("testinguser@example.com");
		user.setMobileNumber(9999911111L);
		user.setDateOfBirth(LocalDate.of(1990, 1, 1));
		user.setPassword("Password123");
		return user;
	}

	private UserDto createUserDto() {
		UserDto userDto = new UserDto();
		userDto.setUserName("TestingUser");
		userDto.setEmailId("testinguser@example.com");
		userDto.setMobileNumber(9999911111L);
		userDto.setDateOfBirth(LocalDate.of(1990, 1, 1));
		userDto.setPassword("Password123");
		userDto.setConfirmPassword("Password123");
		return userDto;
	}

	private UserProfileDto createUserProfileDto() {
		UserProfileDto userProfileDto = new UserProfileDto();
		userProfileDto.setUserId(1);
		userProfileDto.setUserName("TestingUser");
		userProfileDto.setEmailId("testinguser@example.com");
		userProfileDto.setMobileNumber(9999911111L);
		return userProfileDto;
	}

	private UserLoginDto createUserLoginDto() {
		UserLoginDto loginDto = new UserLoginDto();
		loginDto.setEmailId("testinguser@example.com");
		loginDto.setPassword("Password123");
		return loginDto;
	}

	private UserPasswordResetDto createUserPasswordResetDto() {
		UserPasswordResetDto resetDto = new UserPasswordResetDto();
		resetDto.setEmailId("testinguser@example.com");
		resetDto.setPassword("NewPassword123");
		resetDto.setConfirmPassword("NewPassword123");
		return resetDto;
	}
	
	private Show createShow() {
	    Show show = new Show();
	    show.setMovie(createMovie());
	    show.setHall(createHall());
	    show.setSlotNo(1);
	    show.setFromDate(LocalDate.now().plusDays(1));
	    show.setToDate(LocalDate.now().plusDays(3));
	    return show;
	}

	// Helper method to create a past Show object
	private Show createPastShow() {
	    Show show = new Show();
	    show.setMovie(createMovie());
	    show.setHall(createHall());
	    show.setSlotNo(1);
	    show.setFromDate(LocalDate.now().minusDays(3));
	    show.setToDate(LocalDate.now().minusDays(1));
	    return show;
	}

	// Helper method to create a Booking object
	private Booking createBooking() {
	    Booking booking = new Booking();
	    booking.setBookingId(1);
	    booking.setUser(createUser());
	    booking.setShows(createShow());
	    booking.setBookedDate(LocalDateTime.now());
	    // Add more properties as needed
	    return booking;
	}

	// Helper method to create a Movie object
	private Movies createMovie() {
	    Movies movie = new Movies();
	    movie.setMovieName("Test Movie");
	    // Add more properties as needed
	    return movie;
	}

	// Helper method to create a Hall object
	private Hall createHall() {
	    Hall hall = new Hall();
	    hall.setHallDesc("Hall A");
	    // Add more properties as needed
	    return hall;
	}
	private UserBookingDto createSampleUserBookingDto() {
        UserBookingDto userBookingDto = new UserBookingDto();
        userBookingDto.setBookingId(1);
        userBookingDto.setUserName("John Doe");
        userBookingDto.setMovieName("Sample Movie");
        userBookingDto.setBookingDate(LocalDate.now().toString());
        userBookingDto.setHallDesc("Hall A");
        userBookingDto.setSlotNo(1);

        // Create a sample map of selected seats
        Map<String, Integer> selectedSeats = new HashMap<>();
        selectedSeats.put("VIP", 2);
        selectedSeats.put("Regular", 3);
        userBookingDto.setSelectedSeats(selectedSeats);

        userBookingDto.setShowDate(LocalDate.now().plusDays(1));
        userBookingDto.setBookingTotal(150.0);
        userBookingDto.setCancellationCharges(0.0);
        userBookingDto.setBookingStatus("booked");

        return userBookingDto;
    }
}

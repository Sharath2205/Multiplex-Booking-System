package com.multiplex.util;

public class AppConstants {
	
	// --------------	REGEX	--------------------
	public static final String EMAIL_REGEX = "^[a-zA-Z][a-zA-Z0-9._%+-]*@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"; 
	public static final String MOBILE_NUMBER_REGEX = "^[6-9]\\d{9}$";
	public static final String PASSWORD_REGEX = "^[a-zA-Z0-9_@]{6,14}$";
	public static final String USER_NAME_REGEX = "^[a-zA-Z]+(\\s[a-zA-Z]+)?$";
	
	// --------------	USER EXCEPTION MESSAGES	-----------------
	public static final String USER_ALREADY_EXISTS = "User already exists!";
	public static final String INVALID_USER_NAME = "User name should contain only words and space.";
	public static final String INVALID_PASSWORD = "password should be of length 6-14 characters consisting of uppercase letters,lowercase letters,digits and ['@','_'] symbols only.";
	public static final String INVALID_EMAIL = "Invalid email. Example: user.email@example.com.";
	public static final String INVALID_MOBILE_NUMBER = "Enter a valid 10 digit mobile number.";
	public static final String INVALID_CREDENTIALS = "Invalid email or password. Please double-check your credentials and try again";
	public static final String USER_NOT_FOUND = "User with email not found";
	public static final String USER_NOT_REGISTERED = "Please register yourself to book tickets";
	public static final String USER_NAME_NOT_FOUND = "User with user name \"#\" not found";
	public static final String SAME_OLD_AND_NEW_PASSWORD = "Old password and new password should not be same";
	public static final String PASSWORD_MISMATCH = "New password and confirm password does not match";
	public static final String ENTER_NEW_AND_CONFIRM_PASSWORD = "Please enter both new password and confirm password";
	public static final String INSUFFICENT_INFORMATION = "Please provide all the required information";
	public static final String ADMIN_NOT_FOUND = "Invalid admin credentials";
	public static final String INVALID_DATE_OF_BIRTH = "Please enter valid date of birth";
	
	// ---------------- MOVIE EXCEPTION MESSAGES -----------------
	public static final String MOVIE_NOT_FOUND = " not found";
	
	
	// ---------------- SHOW EXCEPTION MESSAGES ------------------
	public static final String SHOWS_NOT_FOUND = "No Shows available for movie \"#\"";
	public static final String SHOW_ID_NOT_FOUND = "No show found with id: ";
	public static final String INVALID_SLOT_NUMBER = "Slot number should be between 1 and 4";
	public static final String SHOW_ALREADY_RUNNING = "A show is already running in the hall 1 with in 2 slot till 3";
	public static final String NO_SHOW_RUNNING = "Show is not available on the selected Date: date";
	public static final String NO_SHOW_AVAILABILITY = "No availability found for the specified show, hall, seat type, and date";
	public static final String SHOW_DELECTION_ERROR = "Unable to delete show. Please cancel all the bookings before deleting the show";

	
	// ---------------- HALL EXCEPTION MESSAGES ------------------
	public static final String HALL_DESC_NOT_FOUND = "Please provide a valid Hall Name";
	public static final String HALL_WITH_DESC_ALREADY_EXISTS = "Hall with name # already exists";
	public static final String NO_HALLS_FOUND = "No Halls available at this moment";
	public static final String HALL_WITH_ID_NOT_FOUND = "Hall with ID # not found";
	public static final String HALL_DELECTION_ERROR = "Unable to delete hall. Please cancel all the Shows before deleting the hall";
	
	// ---------------- BOOKING EXCEPTION MESSAGES
	public static final String NO_SEAT_TYPE = "Please select any seat to book a ticket";
	public static final String INSUFFICIENT_SEATS_FOR_SEAT_TYPE = "Insufficient seats available for seat type: ";
	public static final String BOOKING_NOT_FOUND_WITH_ID = "Booking not found with ID: ";
	public static final String NOT_VALID_USER = "User does not have permission to this booking";
	public static final String ALL_SEATS_NOT_AVAILABLE = "Only # seats remaining";
	
	
	private AppConstants() {}
}

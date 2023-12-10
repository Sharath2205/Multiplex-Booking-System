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
	public static final String USER_NOT_FOUND = "User with email # not found";
	public static final String SAME_OLD_AND_NEW_PASSWORD = "Old password and new password should not be same";
	public static final String PASSWORD_MISMATCH = "New password and confirm password does not match";
	public static final String ENTER_NEW_AND_CONFIRM_PASSWORD = "Please enter both new password and confirm password";
	public static final String INSUFFICENT_INFORMATION = "Please provide all the required information";
	
	// ---------------- MOVIE EXCEPTION MESSAGES -----------------
	public static final String MOVIE_NOT_FOUND = " not found";
	
	
	// ---------------- SHOW EXCEPTION MESSAGES ------------------
	public static final String SHOWS_NOT_FOUND = "No Shows available for movie \"#\"";
	
	private AppConstants() {}
}

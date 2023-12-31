package com.multiplex.service;

import java.util.List;

import com.multiplex.dto.UserDashboardDto;
import com.multiplex.dto.UserDto;
import com.multiplex.dto.UserLoginDto;
import com.multiplex.dto.UserPasswordResetDto;
import com.multiplex.dto.UserProfileDto;
import com.multiplex.entity.User;

public interface UserService {
	
	UserDto registerUser(UserDto userDto);
	
	String loginUser(UserLoginDto user);
	
	List<User> getAllUsers();
	
	String deleteUser(UserLoginDto user);
	
	String resetPassword(UserPasswordResetDto user);

	UserProfileDto updateUser(UserProfileDto user);
	
	UserDashboardDto userDashboard(String emailId);
}

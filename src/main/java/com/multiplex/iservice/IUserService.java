package com.multiplex.iservice;

import java.util.List;

import org.springframework.stereotype.Service;

import com.multiplex.dto.UserDto;
import com.multiplex.dto.UserLoginDto;
import com.multiplex.dto.UserPasswordResetDto;
import com.multiplex.entity.User;

@Service
public interface IUserService {
	
	UserDto registerUser(UserDto userDto);
	
	String loginUser(UserLoginDto user);
	
	List<User> getAllUsers();
	
	String deleteUser(UserLoginDto user);
	
	String resetPassword(UserPasswordResetDto user);

	User updateUser(UserDto user);
}

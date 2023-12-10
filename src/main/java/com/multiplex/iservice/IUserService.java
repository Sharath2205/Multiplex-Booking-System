package com.multiplex.iservice;

import java.util.List;

import org.springframework.stereotype.Service;

import com.multiplex.dto.UserDto;
import com.multiplex.entity.User;

@Service
public interface IUserService {
	
	User createUser(User user);
	
	String loginUser(User user);
	
	List<User> getAllUsers();
	
	String updateUser(User user);
	
	String deleteUser(UserDto user);
	
	String resetPassword(UserDto user);
}

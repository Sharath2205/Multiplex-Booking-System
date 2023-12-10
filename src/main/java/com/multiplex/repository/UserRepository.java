package com.multiplex.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multiplex.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findByUserName(String userName);
	
	Optional<User> findByEmailIdIgnoreCase(String emailId);
}

package com.multiplex.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.multiplex.dto.UserDto;
import com.multiplex.dto.UserLoginDto;
import com.multiplex.dto.UserPasswordResetDto;
import com.multiplex.dto.UserProfileDto;
import com.multiplex.entity.User;
import com.multiplex.exception.InsufficentInformationException;
import com.multiplex.exception.InvalidDateOfBirthException;
import com.multiplex.exception.InvalidEmailException;
import com.multiplex.exception.InvalidPasswordException;
import com.multiplex.exception.PasswordMismatchException;
import com.multiplex.exception.SameOldAndNewPasswordException;
import com.multiplex.exception.UserCreationException;
import com.multiplex.exception.UserNotFoundException;
import com.multiplex.repository.UserRepository;
import com.multiplex.util.AppConstants;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Transactional(readOnly = false)
	public UserDto registerUser(UserDto userDto) {
		System.out.println(userDto.getConfirmPassword());
		Optional<User> optionalUser = userRepository.findByEmailIdIgnoreCase(userDto.getEmailId());

		if (optionalUser.isPresent()) {
			throw new UserCreationException(AppConstants.USER_ALREADY_EXISTS);
		} else {

			if (userDto.getUserName() == null || !userDto.getUserName().matches(AppConstants.USER_NAME_REGEX)) {
				throw new UserCreationException(AppConstants.INVALID_USER_NAME);
			}
			if (userDto.getPassword() == null || !userDto.getPassword().matches(AppConstants.PASSWORD_REGEX)) {
				throw new InvalidPasswordException(AppConstants.INVALID_PASSWORD);
			} else if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
				throw new PasswordMismatchException(AppConstants.PASSWORD_MISMATCH);
			}
			if (userDto.getEmailId() == null || !userDto.getEmailId().matches(AppConstants.EMAIL_REGEX)) {
				throw new InvalidEmailException(AppConstants.INVALID_EMAIL);
			}
			if (!(Long.toString(userDto.getMobileNumber()).matches(AppConstants.MOBILE_NUMBER_REGEX))) {
				throw new UserCreationException(AppConstants.INVALID_MOBILE_NUMBER);
			}
			if(!userDto.getDateOfBirth().isBefore(LocalDate.now())) {
				throw new InvalidDateOfBirthException(AppConstants.INVALID_DATE_OF_BIRTH);
			}
		}
		User user = new User();
		user.setUserName(userDto.getUserName());
		user.setEmailId(userDto.getEmailId());
		user.setMobileNumber(userDto.getMobileNumber());
		user.setDateOfBirth(userDto.getDateOfBirth());
		user.setPassword(userDto.getPassword());
		user = userRepository.save(user);
		userDto.setUserId(user.getUserId());
		return userDto;
	}

	@Transactional(readOnly = true)
	public String loginUser(UserLoginDto userDto) {
		Optional<User> registeredUser = userRepository.findByEmailIdIgnoreCase(userDto.getEmailId());

		if (registeredUser.isPresent()) {
			if (registeredUser.get().getPassword().equals(userDto.getPassword())) {
				return "Logged in successfully. Welcome " + registeredUser.get().getUserName();
			}
			throw new InvalidEmailException(AppConstants.INVALID_CREDENTIALS);
		}
		throw new UserNotFoundException(AppConstants.USER_NOT_FOUND.replace("#", userDto.getEmailId()));
	}

	@Transactional(readOnly = false)
	public String resetPassword(UserPasswordResetDto userDto) {
		if (userDto.getEmailId() != null) {
			Optional<User> user = userRepository.findByEmailIdIgnoreCase(userDto.getEmailId().toLowerCase());
			if (user.isPresent()) {
				User dbUser = user.get();
				if (userDto.getPassword() != null && userDto.getConfirmPassword() != null) {
					if (!userDto.getPassword().matches(AppConstants.PASSWORD_REGEX)
							|| !userDto.getConfirmPassword().matches(AppConstants.PASSWORD_REGEX)) {
						throw new InvalidPasswordException(AppConstants.INVALID_PASSWORD);
					}

					if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
						throw new PasswordMismatchException(AppConstants.PASSWORD_MISMATCH);
					}

					if (userDto.getPassword().equals(user.get().getPassword())) {
						throw new SameOldAndNewPasswordException(AppConstants.SAME_OLD_AND_NEW_PASSWORD);
					}

					dbUser.setPassword(userDto.getPassword());
					userRepository.save(dbUser);
					return "Password Reset Successfull";
				} else {
					throw new InvalidPasswordException(AppConstants.ENTER_NEW_AND_CONFIRM_PASSWORD);
				}
			}
			throw new UserNotFoundException(AppConstants.USER_NOT_FOUND.replace("#", userDto.getEmailId()));
		}
		throw new InvalidEmailException(AppConstants.INVALID_CREDENTIALS);
	}

	@Transactional(readOnly = true)
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Transactional(readOnly = true)
	public User getUserByName(String name) {
		if (name == null) {
			Optional<User> optionalUser = userRepository.findByUserName(name);
			if (optionalUser.isPresent())
				return optionalUser.get();
			throw new UserNotFoundException(AppConstants.USER_NAME_NOT_FOUND.replace("#", name));
		}
		throw new InsufficentInformationException(AppConstants.INSUFFICENT_INFORMATION);
	}

	@Transactional(readOnly = false)
	public String deleteUser(UserLoginDto userDto) {
		if (userDto.getEmailId() != null && userDto.getPassword() != null) {
			Optional<User> optionalUser = userRepository.findByEmailIdIgnoreCase(userDto.getEmailId().toLowerCase());
			if (optionalUser.isPresent()) {
				if (userDto.getPassword().equals(optionalUser.get().getPassword())) {
					userRepository.deleteById(optionalUser.get().getUserId());
					return "Account deleted successfully";
				} else {
					throw new InvalidPasswordException(AppConstants.INVALID_CREDENTIALS);
				}
			}
			throw new UserNotFoundException(AppConstants.USER_NOT_FOUND.replace("#", userDto.getEmailId()));
		}
		throw new InsufficentInformationException(AppConstants.INSUFFICENT_INFORMATION);
	}

	@Override
	public UserProfileDto updateUser(UserProfileDto user) {
		if (user.getEmailId() != null) {
			Optional<User> optionalUser = userRepository.findByEmailIdIgnoreCase(user.getEmailId());
			if (optionalUser.isPresent()) {
				User dbUser = optionalUser.get();

				if (user.getUserName() != null && user.getUserName().matches(AppConstants.USER_NAME_REGEX)) {
					dbUser.setUserName(user.getUserName());
				} else if (user.getUserName() != null && !user.getUserName().matches(AppConstants.USER_NAME_REGEX)) {
					throw new UserCreationException(AppConstants.INVALID_USER_NAME);
				}

				if (user.getMobileNumber() != 0
						&& (Long.toString(user.getMobileNumber()).matches(AppConstants.MOBILE_NUMBER_REGEX))) {
					dbUser.setMobileNumber(user.getMobileNumber());
				} else if (user.getMobileNumber() != 0
						&& !Long.toString(user.getMobileNumber()).matches(AppConstants.MOBILE_NUMBER_REGEX)) {
					throw new UserCreationException(AppConstants.INVALID_MOBILE_NUMBER);
				}

				dbUser = userRepository.save(dbUser);
				UserProfileDto userProfileDto = new UserProfileDto();
				userProfileDto.setUserId(dbUser.getUserId());
				userProfileDto.setDateOfBirth(dbUser.getDateOfBirth());
				userProfileDto.setEmailId(dbUser.getEmailId());
				userProfileDto.setMobileNumber(dbUser.getMobileNumber());
				userProfileDto.setUserName(dbUser.getUserName());
				
				return userProfileDto;
			}
			throw new UserNotFoundException(AppConstants.USER_NOT_FOUND.replace("#", user.getEmailId()));
		}
		throw new InsufficentInformationException(AppConstants.INSUFFICENT_INFORMATION);
	}
}

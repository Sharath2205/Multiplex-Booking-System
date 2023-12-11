package com.multiplex.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.multiplex.dto.UserDto;
import com.multiplex.entity.User;
import com.multiplex.exception.InsufficentInformationException;
import com.multiplex.exception.InvalidEmailException;
import com.multiplex.exception.InvalidPasswordException;
import com.multiplex.exception.PasswordMismatchException;
import com.multiplex.exception.SameOldAndNewPasswordException;
import com.multiplex.exception.UserCreationException;
import com.multiplex.exception.UserNotFoundException;
import com.multiplex.iservice.IUserService;
import com.multiplex.repository.UserRepository;
import com.multiplex.util.AppConstants;

@Service
public class UserService implements IUserService {
	@Autowired
	UserRepository userRepository;

	@Transactional(readOnly = false)
	public User createUser(User user) {
		user.setUserType('U');
		Optional<User> optionalUser = userRepository.findByEmailIdIgnoreCase(user.getEmailId());

		if (optionalUser.isPresent()) {
			throw new UserCreationException(AppConstants.USER_ALREADY_EXISTS);
		} else {
			if (user.getUserName() == null || !user.getUserName().matches(AppConstants.USER_NAME_REGEX)) {
				throw new UserCreationException(AppConstants.INVALID_USER_NAME);
			}
			if (user.getPassword() == null || !user.getPassword().matches(AppConstants.PASSWORD_REGEX)) {
				throw new InvalidPasswordException(AppConstants.INVALID_PASSWORD);
			}
			if (user.getEmailId() == null || !user.getEmailId().matches(AppConstants.EMAIL_REGEX)) {
				throw new InvalidEmailException(AppConstants.INVALID_EMAIL);
			}
			if (!(Long.toString(user.getMobileNumber()).matches(AppConstants.MOBILE_NUMBER_REGEX))) {
				throw new UserCreationException(AppConstants.INVALID_MOBILE_NUMBER);
			}
		}
		return userRepository.save(user);
	}

	@Transactional(readOnly = true)
	public String loginUser(User user) {
		Optional<User> registeredUser = userRepository.findByEmailIdIgnoreCase(user.getEmailId());

		if (registeredUser.isPresent() && (registeredUser.get().getPassword().equals(user.getPassword()))) {
			return "Logged in successfully. Welcome " + registeredUser.get().getUserName();
		}
		throw new InvalidEmailException(AppConstants.INVALID_CREDENTIALS);
	}

	@Transactional(readOnly = false)
	public String resetPassword(UserDto userDto) {
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
	public String deleteUser(UserDto userDto) {
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
	public String updateUser(User user) {
		return null;
	}
}

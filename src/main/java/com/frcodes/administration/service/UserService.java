package com.frcodes.administration.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frcodes.administration.dto.UserAccountDTO;
import com.frcodes.administration.model.User;
import com.frcodes.administration.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * Returns user list of application
	 * 
	 * @return List<User> List of users
	 */
	public List<User> listUser() {
		return (List<User>) userRepository.findAll();
	}

	/**
	 * Returns user information by userId.
	 * 
	 * @param userId Id of user
	 * @return Complete user information
	 */
	public User getUserByUserId(Long userId) {
		Optional<User> optional = userRepository.findById(userId);
		return optional != null && optional.isPresent() ? optional.get() : null;
	}

	/**
	 * Creates a new user in application
	 * 
	 * @param userAccount Transfer object with user information
	 * @return New user created
	 */
	public User createUser(UserAccountDTO userAccount) {

		User user = new User();
		user.setName(userAccount.getFistName());
		user.setSurname(userAccount.getLastName());
		return this.createUser(user);
	}

	public User createUser(User user) {
		return userRepository.save(user);
	}
	
	public User validUser(Long userId, String userName) {

		User user = null;

		if (userId == null) {
			throw new IllegalArgumentException("User ID is not correct");
		}
		
		if (userName == null || userName.trim().isEmpty()) {
			throw new IllegalArgumentException("User name is not correct");
		}

		user = this.getUserByUserId(userId);
		if (user == null || !user.getName().toUpperCase().equals(userName.toUpperCase())) {
			user = null;
		}
		return user;
	}

}

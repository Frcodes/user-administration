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
	public User getUserByUserId(String userId) {
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
		user = userRepository.save(user);
		return user;
	}

	public void updateUser(User account) {
		// TODO: version 1.2
	}

	public void deleteUser(User account) {
		// TODO: version 1.2
	}
}

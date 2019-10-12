package com.frcodes.administration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frcodes.administration.model.User;
import com.frcodes.administration.model.UserAccountDTO;
import com.frcodes.administration.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> listUser() {
		return (List<User>) userRepository.findAll();
	}

	public User createUser(UserAccountDTO userAccount) {

		User user = new User();
		user.setName(userAccount.getFistName());
		user.setSurname(userAccount.getLastName());
		user = userRepository.save(user);
		return user;
	}

	public void updateUser(User account) {

	}

	public void deleteUser(User account) {

	}
}

package com.frcodes.administration.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.frcodes.administration.dto.UserAccountDTO;
import com.frcodes.administration.model.User;
import com.frcodes.administration.repository.UserRepository;

/**
 * Unit Test
 * 
 * UserRepository is Mock with Mockito tools.
 * 
 * @author frcodes
 *
 */
@RunWith(SpringRunner.class)
public class UserServiceUnitTest {

	@TestConfiguration
	static class UserServiceConfiguration {

		@Bean
		public UserService userService() {
			return new UserService();
		}
	}

	@Autowired
	private UserService userService;

	@MockBean
	private UserRepository userRepository;

	@Before
	public void setUp() {

		List<User> list = new ArrayList<User>();
		User user = new User();
		user.setName("Mock1");
		list.add(user);

		user = new User();
		user.setName("Mock2");
		list.add(user);

		user = new User();
		user.setName("Mock3");
		list.add(user);

		Mockito.when(userRepository.findAll()).thenReturn(list);

		User userSaved = new User();
		userSaved.setUserId(0L);
		userSaved.setName("MOCK");

		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(userSaved);

		Mockito.when(userRepository.findById(0L)).thenReturn(Optional.of(userSaved));
		Mockito.when(userRepository.findById(null)).thenReturn(null);
	}

	@Test
	public void createUser() {

		UserAccountDTO userAccount = new UserAccountDTO();
		userAccount.setFistName("Jhon");
		userAccount.setLastName("Smith");
		User user = userService.createUser(userAccount);

		assertNotNull(user.getUserId());
		assertEquals(user.getUserId(), new Long(0));
		assertEquals(user.getName(), "MOCK");
		assertNull(user.getSurname());
	}

	@Test
	public void listUser() {

		List<User> list = userService.listUser();
		assertEquals(list.size(), 3L);
	}

	@Test
	public void getUser() {

		User user = userService.getUserByUserId(0L);
		assertEquals(user.getName(), "MOCK");
	}

	@Test
	public void getUserNotFound() {

		User user = userService.getUserByUserId(null);
		assertNull(user);
	}

	@Test(expected = IllegalArgumentException.class)
	public void validUserEmpty() {
		userService.validUser(null, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void validUserParameterIncorrect() {
		userService.validUser(1L, null);
	}

	@Test
	public void validUserNotFound() {
		User user = userService.validUser(1L, "aaa");
		assertNull(user);
	}
}

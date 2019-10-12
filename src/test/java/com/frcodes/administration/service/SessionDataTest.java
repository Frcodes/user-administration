package com.frcodes.administration.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.frcodes.administration.model.User;

@RunWith(SpringRunner.class)
public class SessionDataTest {

	@Test
	public void getSingletonInstance() {

		SessionData data = SessionData.getSingletonInstance();
		assertNotNull(data);
	}

	@Test
	public void setUserSession() {
		
		User userSaved = new User();
		userSaved.setUserId(0L);
		userSaved.setName("MOCK");
		
		SessionData.setUser(userSaved);
		assertEquals(SessionData.getUser(), userSaved);
	}
}

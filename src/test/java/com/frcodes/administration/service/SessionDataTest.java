package com.frcodes.administration.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class SessionDataTest {

	@Test
	public void getSingletonInstance() {

		SessionData data = SessionData.getSingletonInstance();
		assertNotNull(data);
	}

	@Test
	public void setUserSession() {
		SessionData.setUserSession("user");
		assertEquals(SessionData.getUserSession(), "user");
	}
}

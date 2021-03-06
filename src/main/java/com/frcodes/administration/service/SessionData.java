package com.frcodes.administration.service;

import org.springframework.stereotype.Component;

import com.frcodes.administration.model.User;

/**
 * Singleton class for load session data.
 * This class is similar to AppContext of Spring framework.
 * 
 * @author frCodes
 *
 */
@Component
public class SessionData {

	private static User user;
	

	private static SessionData instance;

	public SessionData() {

	}

	public static SessionData getSingletonInstance() {
		if (instance == null) {
			instance = new SessionData();
		}

		return instance;
	}

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		SessionData.user = user;
	}
	
	
	public static boolean isAdministrator() {
		boolean userAdministrator = Boolean.FALSE;
		if (SessionData.getUser().getType().equals(User.UserType.ADMIN.name())) {
			userAdministrator = Boolean.TRUE;
		}
		return userAdministrator;
	}

}

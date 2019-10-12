package com.frcodes.administration.service;

import org.springframework.stereotype.Component;

/**
 * Singleton class for load session data.
 * 
 * This class is similar to AppContext of Spring framework.
 * 
 * @author frCodes
 *
 */
@Component
public class SessionData {

	private static String userSession;

	private static SessionData instance;

	public SessionData() {

	}

	public static SessionData getSingletonInstance() {
		if (instance == null) {
			instance = new SessionData();
		}

		return instance;
	}

	public static String getUserSession() {
		return userSession;
	}

	public static void setUserSession(String userSession) {
		SessionData.userSession = userSession;
	}

}

package com.tbs.server.factories;

import com.tbs.server.model.User;


public class UserFactory extends EntityFactory {

	private static UserFactory instance = null;

	public static UserFactory getInstance() {
		if (instance == null) {
			instance = new UserFactory();
		}

		return instance;
	}

	public boolean insertOrUpdateUser() {
		return false;
	}
	public User getUser() {
		return null;
	}
	public boolean deleteUser() {
		return false;
	}
}


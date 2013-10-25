package com.tbs.server.factories;




public class UserFactory extends EntityFactory {
	
	private static UserFactory instance = null;
	
	public static UserFactory getInstance() {
		if (instance == null) {
			instance = new UserFactory();
		}

		return instance;
	}
}


package com.tbs.server.factories;

import com.tbs.server.model.User;

public class CategoryFactory extends EntityFactory {
	
	private static CategoryFactory instance = null;
	
	public static CategoryFactory getInstance() {
		if (instance == null) {
			instance = new CategoryFactory();
		}
		return instance;
	}
	
	public boolean insertOrUpdateCategory() {
		return false;
	}
	public User getCategory() {
		return null;
	}
	public boolean deleteCategory() {
		return false;
	}
}


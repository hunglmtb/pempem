package com.tbs.server.factories;

import com.tbs.server.model.User;



public class MediaFactory extends EntityFactory {
	
	private static MediaFactory instance = null;
	
	public static MediaFactory getInstance() {
		if (instance == null) {
			instance = new MediaFactory();
		}

		return instance;
	}
	
	public boolean insertOrUpdateMedia() {
		return false;
	}
	public User getMedia() {
		return null;
	}
	public boolean deleteMedia() {
		return false;
	}
}


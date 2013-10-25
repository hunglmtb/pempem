package com.tbs.server.factories;



public class MediaFactory extends EntityFactory {
	
	private static MediaFactory instance = null;
	
	public static MediaFactory getInstance() {
		if (instance == null) {
			instance = new MediaFactory();
		}

		return instance;
	}
}


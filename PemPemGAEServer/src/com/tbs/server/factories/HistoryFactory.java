package com.tbs.server.factories;




public class HistoryFactory extends EntityFactory {
	
	private static HistoryFactory instance = null;
	
	public static HistoryFactory getInstance() {
		if (instance == null) {
			instance = new HistoryFactory();
		}

		return instance;
	}
}


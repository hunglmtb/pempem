package com.tbs.server.factories;

import com.tbs.server.model.User;




public class HistoryFactory extends EntityFactory {
	
	private static HistoryFactory instance = null;
	
	public static HistoryFactory getInstance() {
		if (instance == null) {
			instance = new HistoryFactory();
		}

		return instance;
	}
	
	public boolean insertOrUpdateHistory() {
		return false;
	}
	public User getHistory() {
		return null;
	}
	public boolean deleteHistory() {
		return false;
	}
}


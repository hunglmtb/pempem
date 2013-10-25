package com.tbs.server.model;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.InverseModelListRef;
import org.slim3.datastore.Model;

import com.google.appengine.api.datastore.Key;
import com.tbs.server.meta.HistoryMeta;

@Model
public class User {
	/////////////////// Attributes ///////////////////////////////
	@Attribute(primaryKey = true)
	private Key key;
	
	/////////////////// Relationship ///////////////////////////////
	@Attribute(persistent = false)
    private InverseModelListRef<History, User> historyListRef = new  InverseModelListRef<History,User>
																				(History.class,
																				HistoryMeta.get().userRef.getName(),
																				this);
	
	private String macAddress;

    /////////////////// Attribute getters/setters //////////////////
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public InverseModelListRef<History, User> getHistoryListRef() {
		return historyListRef;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

}

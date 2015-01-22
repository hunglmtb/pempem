package com.tbs.server.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.slim3.datastore.Attribute;

import com.google.appengine.api.datastore.Key;
import com.tbs.server.util.Common;

public class JsonRespond {
	
	/////////////////// Attributes ///////////////////////////////
	
	@JsonIgnore
	@Attribute(primaryKey = true)
	private Key key;
	private String errorMessage;

	
    public Key getKey() {
    	return key;
    }

	public void setKey(Key key) {
		this.key = key;
	}
	public String getKeyString() {
		return Common.getKeyString(this.key);
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}

package com.tbs.server.model;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

import com.google.appengine.api.datastore.Key;

@Model
public class History {
	/////////////////// Attributes ///////////////////////////////
	@Attribute(primaryKey = true)
	private Key key;
	
	private String seenTime;

	/////////////////// Relationship ///////////////////////////////
    private ModelRef<User> userRef = new ModelRef<User>(User.class);
    private ModelRef<Media> mediaRef = new ModelRef<Media>(Media.class);
    
    
    /////////////////// Attribute getters/setters //////////////////
    public Key getKey() {
    	return key;
    }
    
	public ModelRef<User> getUserRef() {
		return userRef;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public ModelRef<Media> getMediaRef() {
		return mediaRef;
	}

	public String getSeenTime() {
		return seenTime;
	}

	public void setSeenTime(String seenTime) {
		this.seenTime = seenTime;
	}
}

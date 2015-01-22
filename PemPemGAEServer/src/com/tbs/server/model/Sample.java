package com.tbs.server.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

@Model
public class Sample extends JsonRespond{


	private Date lastSeenTime;
	private int donePercent;
	private int viewCount;
	private String currentSeenTime;
	private boolean done;


	public int getDonePercent() {
		return donePercent;
	}

	public void setDonePercent(int aDonePercent) {
		this.donePercent = aDonePercent;
	}

	/////////////////// Relationship ///////////////////////////////
	@JsonIgnore
	@Attribute(persistent = false)
	private ModelRef<Media> mediaRef = new ModelRef<Media>(Media.class);


	public ModelRef<Media> getMediaRef() {
		return mediaRef;
	}

	public Date getLastSeenTime() {
		return lastSeenTime;
	}

	public void setLastSeenTime(Date lastSeenTime) {
		this.lastSeenTime = lastSeenTime;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public String getCurrentSeenTime() {
		return currentSeenTime;
	}

	public void setCurrentSeenTime(String currentSeenTime) {
		this.currentSeenTime = currentSeenTime;
	}
}

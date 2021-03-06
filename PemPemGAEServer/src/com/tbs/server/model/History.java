package com.tbs.server.model;

import java.util.Date;

import org.slim3.datastore.Model;

@Model
public class History extends JsonRespond {
	/////////////////// Attributes ///////////////////////////////
	
	private Date lastSeenTime;
	private int donePercent;
	private int viewCount;
	private String currentSeenTime;
	private boolean done;
	private String mediaKeyString;
	
	public int getDonePercent() {
		return donePercent;
	}

	public void setDonePercent(int aDonePercent) {
		this.donePercent = aDonePercent;
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


	public String getMediaKeyString() {
		return mediaKeyString;
	}


	public void setMediaKeyString(String mediaKeyString) {
		this.mediaKeyString = mediaKeyString;
	}

	public void updateViewCount(boolean done, int percent) {
		if (done) {
			setViewCount(viewCount+1);
			this.done = done;
			donePercent = 100;
		}
		else{
			donePercent = percent<=100&&percent>=0?percent:0;
		}
	}
}

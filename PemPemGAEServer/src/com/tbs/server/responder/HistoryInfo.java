package com.tbs.server.responder;


public class HistoryInfo {

	private String lastSeenTime;
	private int donePercent;
	private int viewCount;
	private String seenTime;
	private boolean done;
	private String macAddress;
	public String getLastSeenTime() {
		return lastSeenTime;
	}
	public void setLastSeenTime(String lastSeenTime) {
		this.lastSeenTime = lastSeenTime;
	}
	public int getDonePercent() {
		return donePercent;
	}
	public void setDonePercent(int donePercent) {
		this.donePercent = donePercent;
	}
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	public String getSeenTime() {
		return seenTime;
	}
	public void setSeenTime(String seenTime) {
		this.seenTime = seenTime;
	}
	public boolean isDone() {
		return done;
	}
	public void setDone(boolean done) {
		this.done = done;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	
	
}

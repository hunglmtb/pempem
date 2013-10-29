package com.tbs.server.responder;


public class RespondNotification {

	private String mRespondCode;
	private String mRespondMessage;

	public RespondNotification(String code, String message) {
		super();
		this.mRespondCode = code;
		this.mRespondMessage = message;
	}

	public String getCategoryId() {
		return mRespondCode;
	}


	/**
	 * @return the mName
	 */
	public String getCategoryName() {
		return mRespondMessage;
	}
}

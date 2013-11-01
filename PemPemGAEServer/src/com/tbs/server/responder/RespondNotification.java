package com.tbs.server.responder;


public class RespondNotification {

	private String mRespondCode;
	private String mRespondMessage;
	private Object mRespondData;
	
	public RespondNotification(String code, String message) {
		super();
		this.mRespondCode = code;
		this.mRespondMessage = message;
	}

	public RespondNotification(String mRespondCode, String mRespondMessage,
			Object mRespondData) {
		super();
		this.mRespondCode = mRespondCode;
		this.mRespondMessage = mRespondMessage;
		this.mRespondData = mRespondData;
	}

	public String getRespondCode() {
		return mRespondCode;
	}

	public String getRespondMessage() {
		return mRespondMessage;
	}

	public Object getRespondData() {
		return mRespondData;
	}
	
	

}

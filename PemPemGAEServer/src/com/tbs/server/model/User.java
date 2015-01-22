package com.tbs.server.model;

import java.util.Date;

import org.slim3.datastore.Model;

@Model
public class User  extends JsonRespond {

	private String facebookId;

	private String facebookAccount;

	private String mailAddress;

	private String username;

	private String macAddress;

	private String status;

	private String device;

	private Date registeredDate;

	private Date modifiedDate;

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}


	/**
	 * Gets status of the user in String
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets status of the user
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}


	/**
	 * Gets date when the user registered 
	 * @return the registered date
	 */
	public Date getRegisteredDate() {
		return registeredDate;
	}

	/**
	 * Sets registration date of the user
	 * @param registeredDate the registration date to set
	 */
	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}

	/**
	 * Gets last modification date of the user
	 * @return the modification date
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * Sets last modification date of the user
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getFacebookAccount() {
		return facebookAccount;
	}

	public void setFacebookAccount(String facebookAccount) {
		this.facebookAccount = facebookAccount;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

}

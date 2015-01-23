package com.tbs.server.model;

import java.util.Date;

import org.slim3.datastore.Model;

@Model
public class Category extends JsonRespond{
	
	private String categoryName;
	private Date registeredDate;
	private Date modifiedDate;
	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String mCategoryName) {
		this.categoryName = mCategoryName;
	}


	/**
	 * Gets last modification date of the user
	 * @return the modification date
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * Sets last modification date of the user
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Date getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}

}

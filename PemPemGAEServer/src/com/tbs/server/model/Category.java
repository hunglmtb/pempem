package com.tbs.server.model;

import java.util.Date;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.InverseModelListRef;
import org.slim3.datastore.Model;

import com.google.appengine.api.datastore.Key;
import com.tbs.server.meta.MediaMeta;

@Model
public class Category {
	/////////////////// Attributes ///////////////////////////////
	@Attribute(primaryKey = true)
	private Key key;
	
	private String categoryName;
	private String categoryId;
	private Date registeredDate;
	private Date modifiedDate;
	
	/////////////////// Relationship ///////////////////////////////
	@Attribute(persistent = false)
    private InverseModelListRef<Media, Category> mediaListRef = new  InverseModelListRef<Media,Category>
																				(Media.class,
																				MediaMeta.get().categoryRef.getName(),
																				this);
    /////////////////// Attribute getters/setters //////////////////
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public InverseModelListRef<Media, Category> getMediaListRef() {
		return mediaListRef;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String mCategoryName) {
		this.categoryName = mCategoryName;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
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

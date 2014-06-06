package com.tbs.server.responder;

import com.google.appengine.api.datastore.KeyFactory;
import com.tbs.server.model.Category;

public class CategoryInfo {

	private String mCategoryId = "";
	private String mCategoryName = "";
	private String mCategoryKeyString = "";

	public CategoryInfo(String mCategoryId, String mCategoryName) {
		super();
		this.mCategoryId = mCategoryId;
		this.mCategoryName = mCategoryName;
	}
	
	public CategoryInfo(Category category) {
		super();
		if (category!=null) {
			this.mCategoryId = category.getCategoryId();
			this.mCategoryName = category.getCategoryName();
			this.mCategoryKeyString = KeyFactory.keyToString(category.getKey());			
		}
	}

	public String getCategoryId() {
		return mCategoryId;
	}


	/**
	 * @return the mName
	 */
	public String getCategoryName() {
		return mCategoryName;
	}

	public String getCategoryKeyString() {
		return mCategoryKeyString;
	}
}

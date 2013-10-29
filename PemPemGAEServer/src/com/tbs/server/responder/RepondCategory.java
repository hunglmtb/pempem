package com.tbs.server.responder;

import com.google.appengine.api.datastore.KeyFactory;
import com.tbs.server.model.Category;

public class RepondCategory {

	private String mCategoryId = "";
	private String mCategoryName = "";
	private String mCategoryKeyString = "";

	public RepondCategory(String mCategoryId, String mCategoryName) {
		super();
		this.mCategoryId = mCategoryId;
		this.mCategoryName = mCategoryName;
	}
	
	public RepondCategory(Category category) {
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

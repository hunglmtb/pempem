package com.tbs.server.responder;

import com.google.appengine.api.datastore.KeyFactory;
import com.tbs.server.model.Category;

public class CategoryInfo {

	private String mCategoryName = "";
	private String mCategoryKeyString = "";

	public CategoryInfo(String mCategoryName) {
		super();
		this.mCategoryName = mCategoryName;
	}
	
	public CategoryInfo(Category category) {
		super();
		if (category!=null) {
			this.mCategoryName = category.getCategoryName();
			this.mCategoryKeyString = KeyFactory.keyToString(category.getKey());			
		}
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

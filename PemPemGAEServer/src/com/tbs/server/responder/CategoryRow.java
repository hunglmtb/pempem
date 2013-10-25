package com.tbs.server.responder;

import java.util.List;

public class CategoryRow {

	private String mCategoryId;
	private String mCategoryName;
	private boolean mShowChildCategory;
	private List<CategoryRow> mChildCategories;

	public CategoryRow(String mCategoryId, String mCategoryName,
			boolean mShowChildCategory,List<CategoryRow> mChildCategories) {
		super();
		this.mCategoryId = mCategoryId;
		this.mCategoryName = mCategoryName;
		this.mShowChildCategory = mShowChildCategory;
		this.mChildCategories = mChildCategories;
	}

	public CategoryRow(String mCategoryName,
			boolean mShowChildCategory, List<CategoryRow> mChildCategories) {
		super();
		this.mCategoryName = mCategoryName;
		this.mShowChildCategory = mShowChildCategory;
		this.mChildCategories = mChildCategories;
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

	public List<CategoryRow> getChildCategories() {
		return mChildCategories;
	}


	public void setChildCategories(List<CategoryRow> mChildCategories) {
		this.mChildCategories = mChildCategories;
	}


	public boolean isShowChildCategory() {
		return mShowChildCategory;
	}

}

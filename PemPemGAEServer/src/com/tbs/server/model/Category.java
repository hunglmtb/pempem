package com.tbs.server.model;

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

}

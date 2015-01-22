package com.tbs.server.factories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.ModelQuery;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.gwt.rpc.server.Pair;
import com.tbs.server.meta.CategoryMeta;
import com.tbs.server.model.Category;
import com.tbs.server.model.Media;
import com.tbs.server.responder.CategoryInfo;
import com.tbs.server.util.Common;

public class CategoryFactory extends EntityFactory {

	private static CategoryFactory instance = null;

	public static CategoryFactory getInstance() {
		if (instance == null) {
			instance = new CategoryFactory();
		}
		return instance;
	}

	public Category insertOrUpdateCategory(String categoryId, String categoryName){

		if (categoryId==null||categoryId.length()<=0||categoryName==null||categoryName.length()<=0) {
			return null;
		}


		Category category = getCategoryById(categoryId);
		if(category != null){
			category.setModifiedDate(new Date());
		}
		else{
			category = new Category();
			Key ancestorKey = KeyFactory.createKey("Category", "Category");
			Key childKey = Datastore.allocateId(ancestorKey, Category.class);
			category.setKey(childKey);
			category.setRegisteredDate(new Date());
			category.setCategoryId(categoryId);
		}

		category.setCategoryName(categoryName);

		Key key = Datastore.put(category);

		if (key!=null) {
			return category;
		}
		else return null;
	}

	public Category getCategoryById(String categoryId) {
		if (Common.validateString(categoryId)) {
			Category category = Datastore.query(Category.class)
					.filter(CategoryMeta.get().categoryId.getName(), FilterOperator.EQUAL, categoryId)
					.asSingle();
			return category;
		}
		return null;
	}

	/*private Category getCategory(Key categoryKey) {
		Category category = null;
		category = Datastore.get(Category.class, categoryKey);
		return category;
	}*/

	public List<Category> getCategory() {

		Key ancestorKey = KeyFactory.createKey("Category", "Category");
		List<Category> lCategory = null;

		ModelQuery<Category> categoryQuery = Datastore.query(Category.class,ancestorKey);
		lCategory = categoryQuery.asList();

		return lCategory;

	}
	public boolean deleteCategory() {
		return false;
	}

	public void deleteCategory(String categoryKeyString) throws Exception{
		Key categoryKey = KeyFactory.stringToKey(categoryKeyString);			
		Datastore.delete(categoryKey);
	}



	public List<CategoryInfo> getCategories() {
		List<Category> categories = CategoryFactory.getInstance().getCategory();
		List<CategoryInfo> rcs = convertToRespond(categories);
		return rcs;
	}


	private List<CategoryInfo> convertToRespond(List<Category> categories) {
		if (categories!=null) {
			List<CategoryInfo> rcs = new ArrayList<CategoryInfo>();
			for (Category category : categories) {
				rcs.add(new CategoryInfo(category));

			}		
			return rcs;
		}
		return null;
	}

	public List<Pair<String, String>> getCategoryPairList() {
		List<Category> categories = getCategory();
		List<Pair<String, String>> ctl = new ArrayList<Pair<String, String>>();
		for (Category category : categories) {
			ctl.add(new Pair<String, String>(category.getCategoryId(), category.getCategoryName()));
		}
		return ctl ;
	}

	public Category getCategory(String categoryKey) {
		if (Common.validateString(categoryKey)) {
			Key key = KeyFactory.stringToKey(categoryKey);
			Category category = Datastore.get(Category.class, key);
			return category;
		}
		return null;
	}

	public Category createOtherCategory() {
		// TODO Auto-generated method stub
		return insertOrUpdateCategory("OTHER_CATEGORY", "Other");
	}
}


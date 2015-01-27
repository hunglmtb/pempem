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

	public Category insertOrUpdateCategory(String categoryName, String categoryKeyString){

		if (!Common.validateString(categoryName)) {
			return null;
		}
		
		Category category = null;
		if (Common.validateString(categoryKeyString)) {
			category = getCategory(categoryKeyString);
		}
		else if (getCategoryByName(categoryName)==null) {
			category = new Category();
			Key ancestorKey = KeyFactory.createKey("Category", "Category");
			Key childKey = Datastore.allocateId(ancestorKey, Category.class);
			category.setKey(childKey);
			category.setRegisteredDate(new Date());
		}
		
		if (category==null) {
			return null;
		}

		category.setModifiedDate(new Date());
		category.setCategoryName(categoryName);

		Key key = Datastore.put(category);

		if (key!=null) {
			return category;
		}
		else return null;
	}

	public Category getCategoryByName(String categoryName) {
		if (Common.validateString(categoryName)) {
			Category category = Datastore.query(Category.class)
					.filter(CategoryMeta.get().categoryName.getName(), FilterOperator.EQUAL, categoryName)
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



	public List<Category> getCategories() {
		List<Category> categories = getCategory();
		return categories;
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
			ctl.add(new Pair<String, String>(category.getKeyString(), category.getCategoryName()));
		}
		return ctl ;
	}
	
	public String getCategoryName(String categoryKeyString, List<Pair<String, String>> list){
		if (list!=null&&Common.validateString(categoryKeyString)) {
			for (Pair<String, String> pair : list) {
				if(categoryKeyString.equals(pair.getA())){
					return pair.getB();
				}
			}
		}
		return categoryKeyString;
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
		return insertOrUpdateCategory("Other",null);
	}
}


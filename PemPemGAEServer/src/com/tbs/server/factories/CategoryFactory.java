package com.tbs.server.factories;

import java.util.Date;
import java.util.List;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.ModelQuery;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.tbs.server.meta.CategoryMeta;
import com.tbs.server.model.Category;

public class CategoryFactory extends EntityFactory {

	private static CategoryFactory instance = null;

	public static CategoryFactory getInstance() {
		if (instance == null) {
			instance = new CategoryFactory();
		}
		return instance;
	}

	public Category insertOrUpdateCategory(String categoryId, String categoryName) throws Exception {
		
		if (categoryId==null||categoryId.length()<=0||categoryName==null||categoryName.length()<=0) {
			return null;
		}
		

		Category category = getCategory(categoryId);
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

	private Category getCategory(String categoryId) {
		Category category = Datastore.query(Category.class)
				.filter(CategoryMeta.get().categoryId.getName(), FilterOperator.EQUAL, categoryId)
				.asSingle();
		return category;
	}

	/*private Category getCategory(Key categoryKey) {
		Category category = null;
		category = Datastore.get(Category.class, categoryKey);
		return category;
	}*/

	public List<Category> getCategory() {

		Key ancestorKey = KeyFactory.createKey("Category", "Category");
		List<Category> lCategory = null;
		ModelQuery<Category> recipeQuery = null;
		
		recipeQuery = Datastore.query(Category.class,ancestorKey);

		lCategory = recipeQuery.asList();

		return lCategory;

	}
	public boolean deleteCategory() {
		return false;
	}

	public void deleteCategory(String categoryKeyString) throws Exception{
		Key categoryKey = KeyFactory.stringToKey(categoryKeyString);			
		Datastore.delete(categoryKey);
	}
}


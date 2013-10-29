package com.tbs.server.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tbs.server.factories.CategoryFactory;
import com.tbs.server.model.Category;
import com.tbs.server.responder.RepondCategory;


@Controller
@RequestMapping("/category")
public class CategoryController {
	private static final Logger _logger = Logger.getLogger(CategoryController.class.getName());

	@RequestMapping("/get")
	@ResponseBody
	public List<RepondCategory> getCategories() {
		/*List<CategoryRow> categories = new ArrayList<CategoryRow>();
		categories.add(new CategoryRow(CATEGORY_ID_HEADER, "YOU",false, null));
		categories.add(new CategoryRow(CATEGORY_ID_HISTORY, "History", false, null));
		categories.add(new CategoryRow(CATEGORY_ID_SEARCH, "Search", false, null));
		categories.add(new CategoryRow(CATEGORY_ID_NEWSFEED, "NewsFeed", false, null));
		categories.add(new CategoryRow(CATEGORY_ID_SETTING, "Setting", false, null));
		categories.add(new CategoryRow(CATEGORY_ID_TIMER, "Timer",  false, null));
		categories.add(new CategoryRow(CATEGORY_ID_EXIT, "Exit", false, null));
		categories.add(new CategoryRow(CATEGORY_ID_HEADER, "CHUYÊN MỤC", false, null));
		categories.add(new CategoryRow(CATEGORY_ID_NAME_01, "Kể chuyện đêm khuya", false, null));
		categories.add(new CategoryRow(CATEGORY_ID_NAME_02, "Cửa sổ tình yêu", false, null));
		categories.add(new CategoryRow(CATEGORY_ID_NAME_03, "Blog Radio",  false, null));
		categories.add(new CategoryRow(CATEGORY_ID_NAME_04, "Quick & Snow Show", false, null));
		categories.add(new CategoryRow(CATEGORY_ID_NAME_05, "Truyện ngắn kinh dị",  false, null));
		categories.add(new CategoryRow(CATEGORY_ID_NAME_06, "Thơ quán",  false, null));
		*/
		List<Category> categories = CategoryFactory.getInstance().getCategory();
		List<RepondCategory> rcs = new ArrayList<RepondCategory>();
		for (Category category : categories) {
			rcs.add(new RepondCategory(category));
		}
		return rcs;
	}
	
	@RequestMapping(value="/add", params={"categoryid","categoryname"})
	@ResponseBody
	public List<RepondCategory> addCategory(
			@RequestParam("categoryid") String categoryId,
			@RequestParam("categoryname") String categoryName) {
		
		Category category;
		List<RepondCategory> rcs =null;
		try {
			category = CategoryFactory.getInstance().insertOrUpdateCategory(categoryId,categoryName);
			if (category!=null) {
				rcs = getCategories();				
			}
			
		} catch (Exception e) {
			_logger.warning(e.getMessage());
			e.printStackTrace();
		}
		return rcs;
	}
	
	@RequestMapping(value="/delete", params={"categorykeystring"})
	@ResponseBody
	public List<RepondCategory> deleteCategory(@RequestParam("categorykeystring") String categoryKeyString) {
		
//		RespondNotification repondNotification = null;
		List<RepondCategory> rcs = null;
		
		try {
			CategoryFactory.getInstance().deleteCategory(categoryKeyString);
//			repondNotification = new RespondNotification(Util.SUCCESS, Util.DELETE_SUCCESS);
			rcs = getCategories();				
		} catch (Exception e) {
			_logger.warning(e.getMessage());
//			repondNotification = new RespondNotification(Util.ERROR, Util.DELETE_ERROR);
		}
		return rcs;
	}
}

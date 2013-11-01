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
import com.tbs.server.responder.RespondNotification;
import com.tbs.server.util.Util;


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
		List<RepondCategory> rcs = convertToRespond(categories);
		return rcs;
	}
	
	//for admin web client
	@RequestMapping("/admin/get")
	@ResponseBody
	public RespondNotification getAllCategories() {
		
		List<RepondCategory> rcs = getCategories();
		RespondNotification respond = null;
		if (rcs!=null) {
			respond = new RespondNotification(Util.RESPOND_SUCCESS_CODE, "OK", rcs);			
		}
		else{
			respond = new RespondNotification(Util.RESPOND_ERROR_CODE, "error when query data", null);			
		}
		return respond;
	}
	
	//for admin web client
		@RequestMapping("/admin/getdropdown")
		@ResponseBody
		public RespondNotification getCategoryDropDown() {
			
			List<RepondCategory> rcs = getCategories();
			RespondNotification respond = null;
			if (rcs!=null) {
				String dropDown = convertCategoriesToDropDownList(rcs);
				respond = new RespondNotification(Util.RESPOND_SUCCESS_CODE, "OK", dropDown);			
			}
			else{
				respond = new RespondNotification(Util.RESPOND_ERROR_CODE, "error when query data", null);			
			}
			return respond;
		}
	
	
	private String convertCategoriesToDropDownList(List<RepondCategory> rcs) {
		String jDropDown = "{";
		for (RepondCategory repondCategory : rcs) {
			jDropDown += "\""+repondCategory.getCategoryId()+"\":\""+repondCategory.getCategoryName()+"\",";
			
		}
		jDropDown += "}";
		return jDropDown;
	}

	private List<RepondCategory> convertToRespond(List<Category> categories) {
		if (categories!=null) {
			List<RepondCategory> rcs = new ArrayList<RepondCategory>();
			for (Category category : categories) {
				rcs.add(new RepondCategory(category));
				
			}		
			return rcs;
		}
		return null;
	}

	
	@RequestMapping(value="/add", params={"categoryid","categoryname"})
	@ResponseBody
	public RespondNotification addCategory(
			@RequestParam("categoryid") String categoryId,
			@RequestParam("categoryname") String categoryName) {
		
		Category category;
		
		try {
			category = CategoryFactory.getInstance().insertOrUpdateCategory(categoryId,categoryName);
			if (category!=null) {
				return getAllCategories();				
			}
			
		} catch (Exception e) {
			_logger.warning(e.getMessage());
			e.printStackTrace();
		}
		return new RespondNotification(Util.RESPOND_ERROR_CODE, "error when add data", null);		
	}
	
	
	
	@RequestMapping(value="/delete", params={"categorykeystring"})
	@ResponseBody
	public RespondNotification deleteCategory(@RequestParam("categorykeystring") String categoryKeyString) {
		
		try {
			CategoryFactory.getInstance().deleteCategory(categoryKeyString);
			return getAllCategories();		
			
		} catch (Exception e) {
			_logger.warning(e.getMessage());
		}
		return new RespondNotification(Util.RESPOND_ERROR_CODE, "error when delete data", null);		
	}
}

package com.tbs.server.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tbs.server.factories.CategoryFactory;
import com.tbs.server.model.Category;
import com.tbs.server.responder.RespondNotification;
import com.tbs.server.util.Common;
import com.tbs.server.util.Util;


@Controller
@RequestMapping("/category")
public class CategoryController {
	@RequestMapping("/get")
	@ResponseBody
	public List<Category> getCategories() {
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
		//		List<Category> categories = CategoryFactory.getInstance().getCategory();
		//		List<RepondCategory> rcs = CategoryFactory.getInstance().convertToRespond(categories);
		return CategoryFactory.getInstance().getCategories();
	}

	//for admin web client
	@RequestMapping("/admin/get")
	@ResponseBody
	public List<Category> getAllCategories() {
		List<Category> rcs = getCategories();
		return rcs;
	}

	//for admin web client
	@RequestMapping("/admin/getdropdown")
	@ResponseBody
	public RespondNotification getCategoryDropDown() {

		List<Category> rcs = getCategories();
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


	private String convertCategoriesToDropDownList(List<Category> rcs) {
		String jDropDown = "{";
		for (Category repondCategory : rcs) {
			jDropDown += "\""+repondCategory.getKeyString()+"\":\""+repondCategory.getCategoryName()+"\",";

		}
		jDropDown += "}";
		return jDropDown;
	}




	@RequestMapping(value="/add",method = RequestMethod.POST)
	@ResponseBody
	public List<Category> addCategory(HttpServletRequest req) {

		String categoryName =  Util.getUtf8String(req.getParameter("categoryName"));
		String categoryKeyString = Util.getUtf8String(req.getParameter("keyString"));
		Category category = null;
		List<Category> error = new ArrayList<>();
		try {
			//String name = Util.getUtf8String(categoryName);
			category = CategoryFactory.getInstance().insertOrUpdateCategory(categoryName,categoryKeyString);
			if (category!=null) {
				return getAllCategories();				
			}
			else{
				category = new Category();
				category.setErrorMessage("failt");
			}

		} catch (Exception e) {
			e.printStackTrace();
			category.setErrorMessage(Common.stackTraceToString(e));
		}
		error.add(category);
		return error;		
	}
	

	@RequestMapping(value="/delete", params={"categorykeystring"})
	@ResponseBody
	public List<Category> deleteCategory(@RequestParam("categorykeystring") String categoryKeyString) {
		Category category = null;
		List<Category> error = new ArrayList<>();
		try {
			CategoryFactory.getInstance().deleteCategory(categoryKeyString);
			return getAllCategories();		

		} catch (Exception e) {
			e.printStackTrace();
			category = new Category();
			category.setErrorMessage(Common.stackTraceToString(e));
		}
		error.add(category);
		return error;		
	}
}

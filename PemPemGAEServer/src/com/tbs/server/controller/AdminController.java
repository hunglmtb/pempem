package com.tbs.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/admin")
public class AdminController {


	//manage category
	@RequestMapping("/category")
	@ResponseBody
	public ModelAndView showCategory() {
		return new ModelAndView("category-manager");
	}

	@RequestMapping("/category/delete")
	@ResponseBody
	public ModelAndView deleteCategory() {
		return new ModelAndView("delete-category");
	}


	@RequestMapping("/category/get")
	@ResponseBody
	public ModelAndView getCategories() {
		return new ModelAndView("get-category");
	}

	@RequestMapping("/category/update")
	@ResponseBody
	public ModelAndView updateCategory() {
		return new ModelAndView("update-category");
	}
	
	
	//media admin
	@RequestMapping("/media")
	@ResponseBody
	public ModelAndView showMedia() {
		return new ModelAndView("media-manager");
	}
}
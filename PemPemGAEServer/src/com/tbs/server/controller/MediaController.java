package com.tbs.server.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tbs.server.responder.CategoryRow;
import com.tbs.server.responder.MediaInfo;


@Controller
@RequestMapping("/media")
public class MediaController {

	@RequestMapping(value="/new", params={"offset","limit"})
	@ResponseBody
	public List<CategoryRow> getNews(
			@RequestParam("offset") int offset,
			@RequestParam("limit") String limit) {

		return null;
	}

	@RequestMapping(value="/newsfeed", params={"userid","offset","limit"})
	@ResponseBody
	public List<CategoryRow> getNewsfeed(
			@RequestParam("userid") String userid,
			@RequestParam("offset") int offset,
			@RequestParam("limit") String limit) {

		return null;
	}

	@RequestMapping(value="/bycategory", params={"categogyid","offset","limit"})
	@ResponseBody
	public List<CategoryRow> getMediaByCategory(
			@RequestParam("categogyid") String categogyid,
			@RequestParam("offset") int offset,
			@RequestParam("limit") String limit) {

		return null;
	}

	@RequestMapping(value="/relative", params={"mediaid","offset","limit"})
	@ResponseBody
	public List<MediaInfo> getMediaList(@RequestParam("mediaid") String mediaid,
			@RequestParam("offset") int offset,
			@RequestParam("limit") String limit) {
		return null;
	}

	@RequestMapping(value="/history", params={"userid","offset","limit"})
	@ResponseBody
	public List<MediaInfo> getHistory(@RequestParam("userid") String userid,
			@RequestParam("offset") int offset,
			@RequestParam("limit") String limit) {
		return null;
	}

}

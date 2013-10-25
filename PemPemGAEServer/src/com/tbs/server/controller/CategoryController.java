package com.tbs.server.controller;

import static com.tbs.server.util.Common.CATEGORY_ID_EXIT;
import static com.tbs.server.util.Common.CATEGORY_ID_HEADER;
import static com.tbs.server.util.Common.CATEGORY_ID_HISTORY;
import static com.tbs.server.util.Common.CATEGORY_ID_NAME_01;
import static com.tbs.server.util.Common.CATEGORY_ID_NAME_02;
import static com.tbs.server.util.Common.CATEGORY_ID_NAME_03;
import static com.tbs.server.util.Common.CATEGORY_ID_NAME_04;
import static com.tbs.server.util.Common.CATEGORY_ID_NAME_05;
import static com.tbs.server.util.Common.CATEGORY_ID_NAME_06;
import static com.tbs.server.util.Common.CATEGORY_ID_NEWSFEED;
import static com.tbs.server.util.Common.CATEGORY_ID_SEARCH;
import static com.tbs.server.util.Common.CATEGORY_ID_SETTING;
import static com.tbs.server.util.Common.CATEGORY_ID_TIMER;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tbs.server.responder.CategoryRow;
import com.tbs.server.responder.MediaInfo;


@Controller
@RequestMapping("/categories")
public class CategoryController {

	
//    private @Autowired ServletContext servletContext;

	@RequestMapping(value="/{name}", method = RequestMethod.GET)
	public String getMovie(@PathVariable String name, ModelMap model) {

		model.addAttribute("movie", name);
		model.addAttribute("message", "koko");

		//retrun to jsp page, configurated in mvc-dispatcher-servlet.xml, view resolver
		return "list";

	}

	@RequestMapping("/get")
	@ResponseBody
	public List<CategoryRow> getCategories() {
		List<CategoryRow> categories = new ArrayList<CategoryRow>();
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
		
		return categories  ;
	}
	
	
	@RequestMapping(value="/medialist", params={"groupmode","offset","limit"})

	@ResponseBody
	public List<MediaInfo> getMediaList(@RequestParam("groupmode") String groupmode,
			@RequestParam("offset") int offset,
			@RequestParam("limit") String limit) {
		/*if (Common.mediaId==null) {
			Common.initTestData(servletContext);
		}
		List<MediaInfo> mediaList = new ArrayList<MediaInfo>();
		//init media
		MediaInfo media = null;
		for (int i = 0; i <= 10; i++) {
			media = new MediaInfo("mediaid"+i,
					"title"+i,
					Common.mediaFileUrl.get(i),
					groupmode,
					""+100+i,
					""+50+i,
					"speaker"+i,
					"contentinfo"+i,
					"34:"+i,
					Common.mediaLinkUrl.get(i),
					"author"+i,
					"2013/5/"+i,
					""+200+i,
					0,
					Common.mediaImageThumbUrl.get(i),
					Common.mediaImageUrl.get(i),
					i*10/100+" %",
					Common.mediaHistoryTime[i]);
			mediaList.add(media);
		}
		 */
		return null;
	}

}

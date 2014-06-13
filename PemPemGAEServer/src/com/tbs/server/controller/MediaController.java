package com.tbs.server.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.tbs.server.factories.MediaFactory;
import com.tbs.server.model.Media;
import com.tbs.server.responder.CategoryRow;
import com.tbs.server.responder.MediaInfo;
import com.tbs.server.responder.RespondNotification;
import com.tbs.server.util.Util;


@Controller
@RequestMapping("/media")
public class MediaController {
	private static final Logger _logger = Logger.getLogger(MediaController.class.getName());
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	//get api
	//for admin : web client
	@RequestMapping(value="/admin/all", params={"offset","limit"})
	@ResponseBody
	public RespondNotification getMediaList(
			@RequestParam("offset") int offset,
			@RequestParam("limit") int limit) {
		
		List<MediaInfo> ml = getAll(offset, limit);
		RespondNotification respond = null;
		if (ml!=null) {
			respond = new RespondNotification(Util.RESPOND_SUCCESS_CODE, "ok", ml);							
		}
		else{
			respond = new RespondNotification(Util.RESPOND_ERROR_CODE, "error when get media", null);							
		}
		return respond;
	}
	
	//for client smart phone
	@RequestMapping(value="/all", params={"offset","limit"})
	@ResponseBody
	public List<MediaInfo> getAll(
			@RequestParam("offset") int offset,
			@RequestParam("limit") int limit) {

		List<Media> mediaList = MediaFactory.getInstance().getMedia(offset,limit,Util.MediaQueryMode.MEDIA_GET_ALL);
		if (mediaList!=null) {
			List<MediaInfo> mediaInfoList = new ArrayList<MediaInfo>();
			for (Media media : mediaList) {
				mediaInfoList.add(new MediaInfo(media));
			}
			return mediaInfoList;
		}

		return null;
	}

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
	
	//api
	@RequestMapping(value="/update/viewcount", params={"userid","mediaid"})
	@ResponseBody
	public List<MediaInfo> updateViewCount(@RequestParam("userid") String userid,
			@RequestParam("mediaid") int mediaid) {
		return null;
	}


	//add api
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public RespondNotification addMedia(HttpServletRequest req) {
		String jsonDataString = getJsonString(req);
		if (jsonDataString!=null) {
			try {
				Media media = MediaFactory.getInstance().insertOrUpdateMedia(jsonDataString);
				if (media!=null) {
					List<MediaInfo> ml = getContiguousMedia(media);
					return new RespondNotification(Util.RESPOND_SUCCESS_CODE, "ok", ml);							
				}
				else{
					return new RespondNotification(Util.RESPOND_ERROR_CODE, "error when add media", null);	
				}
			} catch (JSONException e) {
				e.printStackTrace();
				return new RespondNotification(Util.RESPOND_ERROR_CODE, "error when parse json post data", null);		
			}
		}
		return new RespondNotification(Util.RESPOND_ERROR_CODE, "post data error or error when get post data string", null);		
	}
	

	@RequestMapping(value="/delete", params={"mediakeystring"})
	@ResponseBody
	public RespondNotification deleteCategory(@RequestParam("mediakeystring") String mediakeystring) {
		
		try {
			MediaFactory.getInstance().deleteMedia(mediakeystring);
			List<MediaInfo> ml = getAll(0, 10);
			return new RespondNotification(Util.RESPOND_SUCCESS_CODE, "ok", ml);	
			
		} catch (Exception e) {
			_logger.warning(e.getMessage());
		}
		return new RespondNotification(Util.RESPOND_ERROR_CODE, "error when delete media", null);		
	}

	//methods
	private List<MediaInfo> getContiguousMedia(Media media) {
		//TODO UPDATE LATER, THIS IS ONLY FOR TEST
		return getAll(0, 10);
	}

	private String getJsonString(HttpServletRequest request) {
		StringBuilder stringJsonData = null;
		try {
			stringJsonData = Util.converByteToString(request.getInputStream());
			return stringJsonData.toString();
		} catch (IOException e) {
			_logger.warning(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * getImageRecipe
	 * 
	 * @param imageBlobKey
	 *            : recipe key
	 * @param index
	 *            : index get image or thumbnails
	 * @param req
	 *            : HttpServletRequest
	 * @param resp
	 *            : HttpServletResponse
	 */
	
	@RequestMapping(value = "/image", params={"imagekey"}, method = RequestMethod.GET)
	@ResponseBody
	public void getImageRecipe(@RequestParam("imagekey") String imageBlobKey,
								HttpServletRequest req,
								HttpServletResponse resp) {
		
		try {
			BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
			BlobKey imageKey = new BlobKey(imageBlobKey);
			blobstoreService.serve(imageKey, resp);
			
//			ImagesService imagesService = ImagesServiceFactory.getImagesService();
//			imagesService.getServingUrl(imageKey);
			
		} catch (IOException e) {
			e.printStackTrace();
			_logger.warning(e.getMessage());
			_logger.warning(e.getStackTrace().toString());
		}
	}
	
	@RequestMapping(value="/play", params={"key"})
	@ResponseBody
	public void serveMedia(@RequestParam("key") String key,HttpServletResponse res) throws IOException {
		if (key!=null) {
			BlobKey blobKey = new BlobKey(key);
			blobstoreService.serve(blobKey, res);
		}
	}
	
	@RequestMapping(value="/action", params={"key"})
	@ResponseBody
	public ModelAndView actionMedia(@RequestParam("key") String key,HttpServletResponse res,ModelMap model) throws IOException {
		ModelAndView  redirect = new ModelAndView("media-play");
		if (key!=null) {
			model.addAttribute("mediaKey", key);
		}
		return redirect;
	}
	
	
	//facebook
	@RequestMapping(value="/social", params={"mediaId"})
	@ResponseBody
	public ModelAndView social(@RequestParam("mediaId") String aMediaId , ModelMap model) throws IOException {
		ModelAndView  redirect = new ModelAndView("commentplugin");
		String mediaUrl = "";
		//TODO update when update domain
		if (aMediaId!=null) {
			mediaUrl = "kcdkv2.appspot.com/media/"+aMediaId;
		}
		model.addAttribute("mediaUrl", mediaUrl);
		return redirect;
	}
}

package com.tbs.server.controller;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreInputStream;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.tbs.server.factories.MediaFactory;
import com.tbs.server.model.Media;
import com.tbs.server.responder.CategoryRow;
import com.tbs.server.responder.MediaInfo;
import com.tbs.server.responder.RespondNotification;
import com.tbs.server.util.Common;
import com.tbs.server.util.Util;


@Controller
@RequestMapping("/media")
public class MediaController {
	private static final Logger _logger = Logger.getLogger(MediaController.class.getName());

	//get api
	//for admin : web client
	@RequestMapping(value="/admin/all", params={"offset","limit"})
	@ResponseBody
	public List<Media> getMediaList(
			@RequestParam("offset") int offset,
			@RequestParam("limit") int limit) {
		return  getAll(offset, limit);
	}

	//for client smart phone
	@RequestMapping(value="/all", params={"offset","limit"})
	@ResponseBody
	public List<Media> getAll(
			@RequestParam("offset") int offset,
			@RequestParam("limit") int limit) {

		List<Media> mediaList = null;
		List<Media> error = new ArrayList<>();
		try {
			mediaList = MediaFactory.getInstance().getMedia(offset,limit,Util.MediaQueryMode.MEDIA_GET_ALL,null);
			return mediaList;

		} catch (Exception e) {
			e.printStackTrace();
			Media category = new Media();
			category.setErrorMessage(Common.stackTraceToString(e));
			error.add(category);
		}

		return error;
	}

	//for client smart phone
	@RequestMapping(value="/category",params={"category","offset","limit"})
	@ResponseBody
	public List<Media> getMediaByCategory(
			@RequestParam("category") String categoryString,
			@RequestParam("offset") int offset,
			@RequestParam("limit") int limit) {

		List<Media> mediaList = null;
		List<Media> error = new ArrayList<>();
		try {
			mediaList = MediaFactory.getInstance().getMedia(offset,limit,Util.MediaQueryMode.MEDIA_GET_ALL,categoryString);
			return mediaList;

		} catch (Exception e) {
			e.printStackTrace();
			Media category = new Media();
			category.setErrorMessage(Common.stackTraceToString(e));
			error.add(category);
		}

		return error;
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
		String jsonDataString = Common.getJsonString(req);
		if (jsonDataString!=null) {
			try {
				Media media = MediaFactory.getInstance().insertOrUpdateMedia(jsonDataString);
				if (media!=null) {
					List<Media> ml = getContiguousMedia(media);
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
	public RespondNotification deleteMedia(@RequestParam("mediakeystring") String mediakeystring) {

		try {
			MediaFactory.getInstance().deleteMedia(mediakeystring);
			List<Media> ml = getAll(0, 10);
			return new RespondNotification(Util.RESPOND_SUCCESS_CODE, "ok", ml);	

		} catch (Exception e) {
			_logger.warning(e.getMessage());
		}
		return new RespondNotification(Util.RESPOND_ERROR_CODE, "error when delete media", null);		
	}

	//methods
	private List<Media> getContiguousMedia(Media media) {
		//TODO UPDATE LATER, THIS IS ONLY FOR TEST
		return getAll(0, 10);
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
			// Build response headers
			res.setContentType("audio/mpeg");
			//res.setContentLength(7654321);
			BlobKey blobKey = new BlobKey(key);
			//BlobstoreInputStream in = new BlobstoreInputStream(blobKey); 
			//blobstoreService.serve(blobKey, res);


			try {
				// get your file as InputStream
				BlobstoreInputStream is = new BlobstoreInputStream(blobKey); 
				// copy it to response's OutputStream
				IOUtils.copy(is, res.getOutputStream());
				res.flushBuffer();
			} catch (IOException ex) {
				//log.info("Error writing file to output stream. Filename was '{}'", fileName, ex);
				throw new RuntimeException("IOError writing file to output stream");
			}
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


	//comment facebook
	@RequestMapping(value="/social", params={"mediaId"})
	@ResponseBody
	public ModelAndView social(@RequestParam("mediaId") String aMediaId , ModelMap model) throws IOException {
		ModelAndView  redirect = new ModelAndView("commentplugin");
		String mediaUrl = "";
		String encodedMediaUrl = "";
		//TODO update when update domain
		if (aMediaId!=null) {
			mediaUrl = "kcdkv2.appspot.com/media/"+aMediaId;
			encodedMediaUrl = URLEncoder.encode(mediaUrl);
		}
		model.addAttribute("mediaUrl", mediaUrl);
		model.addAttribute("encodedMediaUrl", encodedMediaUrl);
		return redirect;
	}

	//login plugin facebook
	@RequestMapping(value="/authenticate")
	@ResponseBody
	public ModelAndView authenticate(ModelMap model) throws IOException {
		ModelAndView  redirect = new ModelAndView("loginplugin");
		return redirect;
	}

	//for client smart phone
	@RequestMapping(value="/sample")
	@ResponseBody
	public List<Media> getSample() {
		int offset = 0;
		int limit = 10;
		List<Media> mediaList = MediaFactory.getInstance().getMedia(offset,limit,Util.MediaQueryMode.MEDIA_GET_ALL,null);
		return mediaList;
	}
}

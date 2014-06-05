package com.tbs.server.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.tbs.server.factories.MediaFactory;
import com.tbs.server.model.Media;
import com.tbs.server.responder.MediaInfo;
import com.tbs.server.util.Util;
@Controller
@RequestMapping("/admin")
public class AdminController {

	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    private ImagesService imagesService = ImagesServiceFactory.getImagesService();


	//manage category
	@RequestMapping("/category")
	@ResponseBody
	public ModelAndView showCategory() {
		return new ModelAndView("category-manager");
	}

	//media administrator
	@RequestMapping("/media")
	@ResponseBody
	public ModelAndView showMedia() {
		//List<Media> mediaList = MediaFactory.getInstance().getMedia(0,15,Util.MediaQueryMode.MEDIA_GET_ALL);
		MediaController mdc = new MediaController();
		List<MediaInfo> mediaList = mdc .getAll(0, 20);

		ModelAndView model = new ModelAndView("media-manager"); 
		model.addObject("mediaList",mediaList);
		return model;
	}

	//upload file 
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView  upload(HttpServletRequest req,ModelMap model) throws UnsupportedEncodingException {

		ModelAndView  redirect = new ModelAndView("media-manager");
		
		//get blob key
		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
		List<BlobKey> imageBlobKeys = blobs.get("imageFile");
		List<BlobKey> mediaBlobKeys = blobs.get("mediaFile");

		//get text fields
		String title =  Util.getUtf8String(req.getParameter("title"));
		String content = Util.getUtf8String(req.getParameter("content"));
		String speaker = Util.getUtf8String(req.getParameter("speaker"));
		String author = Util.getUtf8String(req.getParameter("author"));
		String mediaKey = req.getParameter("mediaKey");
		String categoryId = req.getParameter("categoryId");	

		String imageBlobKey = null;
		String mediaFileBlobKey = null;
		if (imageBlobKeys != null ) {
			imageBlobKey = imageBlobKeys.get(0).getKeyString();
		}
		
		if (mediaBlobKeys!=null) {
			mediaFileBlobKey = mediaBlobKeys.get(0).getKeyString();
		}
		
		Media media = MediaFactory.getInstance().insertOrUpdateMedia(mediaKey,
				title,
				content,
				speaker,
				author,
				imageBlobKey,
				mediaFileBlobKey,
				categoryId);
		if (media!=null) {
			model.addAttribute("result", "success");
		}
		else{
			model.addAttribute("result", "failt when update datastore");
		}
		
		MediaController mdc = new MediaController();
		List<MediaInfo> mediaList = mdc .getAll(0, 20);
		redirect.addObject("mediaList",mediaList);

		return redirect;
	}
	
	//serve file 
	@RequestMapping(value = "serve", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView serve(HttpServletRequest req,HttpServletResponse res) throws IOException {

		String bkey = req.getParameter("blob-key");
		if (bkey!=null) {
			BlobKey blobKey = new BlobKey(bkey);
			blobstoreService.serve(blobKey, res);
		}
		
		ModelAndView redirect = new ModelAndView("media-manager");
		return redirect;
	}
	
	
	//upload image 
	@RequestMapping(value = "/upload-image", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView  uploadImage(HttpServletRequest req) {

		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
		List<BlobKey> blobKeys = blobs.get("uploadImage");

		if (blobKeys == null) {
			ModelAndView  redirect = new ModelAndView("media-manager");
			return redirect;
		} else {
			RedirectView  redirect = new RedirectView("/admin/serve-image");
			Properties attributes = new Properties();
			String bkey = blobKeys.get(0).getKeyString();
			attributes.put("blob-key",bkey );
			redirect.setAttributes(attributes );
			
			return new ModelAndView(redirect);
		}
	}
	
	//serve image 
	@RequestMapping(value = "serve-image", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView serveImage(HttpServletRequest req,HttpServletResponse res) throws IOException {

		String bkey = req.getParameter("blob-key");
		if (bkey!=null) {
			BlobKey blobKey = new BlobKey(bkey);
			imagesService.getServingUrl(blobKey);
			blobstoreService.serve(blobKey, res);
		}
		
		ModelAndView redirect = new ModelAndView("media-manager");
		return redirect;
	}
}

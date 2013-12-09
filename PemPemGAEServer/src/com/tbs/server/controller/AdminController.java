package com.tbs.server.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
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
		return new ModelAndView("media-manager");
	}

	//upload file 
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView  upload(HttpServletRequest req) {

		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
		List<BlobKey> blobKeys = blobs.get("uploadFile");

		if (blobKeys == null) {
			ModelAndView  redirect = new ModelAndView("media-manager");
			return redirect;
		} else {
			RedirectView  redirect = new RedirectView("/admin/serve");
			Properties attributes = new Properties();
			String bkey = blobKeys.get(0).getKeyString();
			attributes.put("blob-key",bkey );
			redirect.setAttributes(attributes );
			
			return new ModelAndView(redirect);
		}
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

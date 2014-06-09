package com.tbs.server.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.tbs.server.responder.CategoryInfo;
import com.tbs.server.responder.MediaInfo;
import com.tbs.server.util.Util;
import com.tbs.server.util.UtilView;
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	private static final int sLimit = 3;
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    private ImagesService imagesService = ImagesServiceFactory.getImagesService();


	//manage category
	@RequestMapping(value={"/category","/categories"})
	@ResponseBody
	public ModelAndView showCategory() {
		CategoryController cdc = new CategoryController();
		List<CategoryInfo> categoryList = cdc.getCategories();
		ModelAndView model = new ModelAndView("category-manager2"); 
		model.addObject("categoryList",categoryList);
		
		return model;
	}
	
	
	//media administrator
	@RequestMapping(value={"/medias","/media/{aPage}"},method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView showMedia(@PathVariable int aPage) {
		//List<Media> mediaList = MediaFactory.getInstance().getMedia(0,15,Util.MediaQueryMode.MEDIA_GET_ALL);
		MediaController mdc = new MediaController();
		int page = aPage -1;
		int offset = page<=0?0:page*sLimit;
		List<MediaInfo> mediaList = mdc.getAll(offset,sLimit);

		ModelAndView model = new ModelAndView("media-manager"); 
		model.addObject("mediaList",mediaList);
		model.getModelMap().addAttribute("page", aPage );
		int size = mediaList.size();
		String paginator = UtilView.getInstance().buildPaginatorHtml(aPage,offset,sLimit,size);
		model.getModelMap().addAttribute("paginator", paginator);
		return model;
	}

	//upload file 
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView  upload(HttpServletRequest req) throws UnsupportedEncodingException {

		
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
		int page = Integer.valueOf(req.getParameter("page"));

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
		
		ModelAndView model = showMedia(page);
		if (media!=null) {
			model.getModelMap().addAttribute("result", "success");
		}
		else{
			model.getModelMap().addAttribute("result", "failt when update datastore");
		}
		return model;
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

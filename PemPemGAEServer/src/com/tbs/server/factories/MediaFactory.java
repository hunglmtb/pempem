package com.tbs.server.factories;

import java.util.Date;
import java.util.List;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.ModelQuery;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.tbs.server.meta.MediaMeta;
import com.tbs.server.model.Category;
import com.tbs.server.model.Media;
import com.tbs.server.util.Common;
import com.tbs.server.util.Util.MediaQueryMode;



public class MediaFactory extends EntityFactory {

	private static MediaFactory instance = null;

	public static MediaFactory getInstance() {
		if (instance == null) {
			instance = new MediaFactory();
		}

		return instance;
	}

	public Media insertOrUpdateMedia(String jsonDataString) throws JSONException {

		JSONObject jsonData = new JSONObject(jsonDataString);

		Category category = null;
		String categoryKey = jsonData.getString(Common.JSON_CATEGORY_KEY);
		if(!Common.validateString(categoryKey)){
			category = CategoryFactory.getInstance().createOtherCategory();
		}
		else{
			category = CategoryFactory.getInstance().getCategory(categoryKey);
		}
		if (category==null) category = CategoryFactory.getInstance().createOtherCategory();

		//---------------------------------------------------------------------------------------
		String mediaKey = jsonData.getString(Common.JSON_MEDIA_KEY);
		Media media = getMedia(mediaKey);
		//insert or update
		Date now = new Date();
		if(media != null){
			media.setModifiedDate(now);
		}
		else{
			media = new Media();
			Key ancestorKey = category.getKey();
			Key childKey = Datastore.allocateId(ancestorKey, Media.class);
			media.setKey(childKey);
			media.setPublishedDate(now);
			media.setPublishedDate(now);
			media.setViewCount(0);
			//media.setDuration("4:17");
		}

		//set properties for media
		media.setTitle(jsonData.getString(MediaMeta.get().title.getName()));
		media.setContentInfo(jsonData.getString(MediaMeta.get().contentInfo.getName()));
		media.setAuthor(jsonData.getString(MediaMeta.get().author.getName()));
		media.setSpeaker(jsonData.getString(MediaMeta.get().speaker.getName()));
		media.setDuration(jsonData.getString(MediaMeta.get().duration.getName()));
		/*media.setMediaType(jsonRecipe.getInt(MediaMeta.get().mediaType.getName()));*/
		media.setMediaType(Common.MEDIA_TYPE_AUDIO);

		//image
		media.setMediaImageUrl(jsonData.getString(MediaMeta.get().mediaImageUrl.getName()));
		media.setMediaImageThumbUrl(jsonData.getString(MediaMeta.get().mediaImageThumbUrl.getName()));

		//media file 
		media.setMediaFileUrl(jsonData.getString(MediaMeta.get().mediaFileUrl.getName()));


		//TODO update later for need to update category and update web client too
		//check update category
		//set category
		/*String categoryId = jsonData.getString(CategoryMeta.get().categoryId.getName());
		Category category = CategoryFactory.getInstance().getCategoryById(categoryId);*/

		Key key = Datastore.put(media);

		if (key!=null) {
			String url = Common.initMediaLinkUrl(key);
			if (Common.validateString(url)) {
				media.setMediaLinkUrl(url);
				Datastore.put(media);
			}
			return media;
		}
		else return null;
	}


	public Media insertOrUpdateMedia(String mediaKey,
			String title,
			String content,
			String speaker,
			String author,
			String imageBlobKey,
			String mediaFileBlobKey,
			String categoryKey, String duration){


		Media media = getMedia(mediaKey);

		//insert or update
		Date now = new Date();
		if(media != null){
			//update
			media.setModifiedDate(now);
		}
		else if (mediaFileBlobKey!=null){
			//insert
			Category category = null;
			if(Common.validateString(categoryKey)){
				category = CategoryFactory.getInstance().getCategory(categoryKey);
			}
			else{
				category = CategoryFactory.getInstance().createOtherCategory();
			}

			media = new Media();
			Key ancestorKey = category.getKey();
			Key childKey = Datastore.allocateId(ancestorKey, Media.class);
			media.setKey(childKey);
			media.setPublishedDate(now);
			media.setPublishedDate(now);
			media.setViewCount(0);
		}
		else return null;

		//set properties for media
		media.setTitle(title);
		media.setContentInfo(content);
		media.setAuthor(author);
		media.setSpeaker(speaker);
		media.setDuration(duration!=null&&duration.length()>0?duration:"-:-");
		/*media.setMediaType(jsonRecipe.getInt(MediaMeta.get().mediaType.getName()));*/
		media.setMediaType(Common.MEDIA_TYPE_AUDIO);

		//media file 
		if (mediaFileBlobKey!=null) {
			media.setMediaFileUrl(mediaFileBlobKey);
		}

		//image
		if (imageBlobKey!=null) {
			media.setMediaImageUrl(imageBlobKey);
			media.setMediaImageThumbUrl(imageBlobKey);				
		}

		//TODO update later for need to update category and update web client too
		//check update category
		//set category
		//String categoryId = jsonRecipe.getString(CategoryMeta.get().categoryId.getName());

		Key key = Datastore.put(media);

		if (key!=null) {
			String url = Common.initMediaLinkUrl(key);
			if (url!=null&&url.length()>0) {
				media.setMediaLinkUrl(url);
				Datastore.put(media);
			}
			return media;
		}
		else return null;
	}


	public Media getMedia(String mediaKey) {
		if (Common.validateString(mediaKey)) {
			Key key = KeyFactory.stringToKey(mediaKey);
			Media media = Datastore.get(Media.class, key);
			return media;
		}
		return null;
	}

	public Media getMedia() {
		return null;
	}

	public void deleteMedia(String mediakeystring) throws Exception{
		Key categoryKey = KeyFactory.stringToKey(mediakeystring);			
		Datastore.delete(categoryKey);
	}

	public List<Media> getMedia(int offset, int limit, MediaQueryMode mode, String categoryKeyString) {

		switch (mode) {
		case MEDIA_GET_ALL:
			List<Media> lMedia = null;
			ModelQuery<Media> mediaQuery = null;
			if (Common.validateString(categoryKeyString)) {
				Key categoryKey = KeyFactory.stringToKey(categoryKeyString);
				mediaQuery =  Datastore.query(Media.class,categoryKey);
			}
			else{
				mediaQuery  = Datastore.query(Media.class);
			}

			mediaQuery.limit(limit);
			mediaQuery.offset(offset);

			//mediaQuery.
			lMedia = mediaQuery.asList();

			return lMedia;

		default:
			break;
		}
		return null;
	}
}


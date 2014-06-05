package com.tbs.server.factories;

import java.util.Date;
import java.util.List;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.ModelQuery;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.gwt.rpc.server.Pair;
import com.tbs.server.meta.CategoryMeta;
import com.tbs.server.meta.MediaMeta;
import com.tbs.server.model.Category;
import com.tbs.server.model.Media;
import com.tbs.server.model.User;
import com.tbs.server.util.Common;
import com.tbs.server.util.Util;
import com.tbs.server.util.Util.MediaQueryMode;
import com.tbs.server.util.UtilView;



public class MediaFactory extends EntityFactory {

	private static MediaFactory instance = null;

	public static MediaFactory getInstance() {
		if (instance == null) {
			instance = new MediaFactory();
		}

		return instance;
	}

	public Media insertOrUpdateMedia(String jsonDataString) throws JSONException {

		JSONObject jsonRecipe = new JSONObject(jsonDataString);
		String mediaKey = jsonRecipe.getString(Util.MEDIA_KEY_STRING);

		Media media = getMedia(mediaKey);

		//insert or update
		Date now = new Date();
		if(media != null){
			media.setModifiedDate(now);
		}
		else{
			media = new Media();
			Key ancestorKey = KeyFactory.createKey("Media", "Media");
			Key childKey = Datastore.allocateId(ancestorKey, Media.class);
			media.setKey(childKey);
			media.setPublishedDate(now);
			media.setPublishedDate(now);
			media.setViewCount(0);
			media.setDuration("4:17");
		}

		//set properties for media
		media.setTitle(jsonRecipe.getString(MediaMeta.get().title.getName()));
		media.setContentInfo(jsonRecipe.getString(MediaMeta.get().contentInfo.getName()));
		media.setAuthor(jsonRecipe.getString(MediaMeta.get().author.getName()));
		media.setSpeaker(jsonRecipe.getString(MediaMeta.get().speaker.getName()));
		/*media.setMediaType(jsonRecipe.getInt(MediaMeta.get().mediaType.getName()));*/
		media.setMediaType(Common.MEDIA_TYPE_AUDIO);

		//image
		media.setMediaImageUrl(jsonRecipe.getString(MediaMeta.get().mediaImageUrl.getName()));
		media.setMediaImageThumbUrl(jsonRecipe.getString(MediaMeta.get().mediaImageThumbUrl.getName()));

		//media file 
		media.setMediaFileUrl(jsonRecipe.getString(MediaMeta.get().mediaFileUrl.getName()));


		//TODO update later for need to update category and update web client too
		//check update category
		//set category
		String categoryId = jsonRecipe.getString(CategoryMeta.get().categoryId.getName());
		Category category = CategoryFactory.getInstance().getCategory(categoryId);
		media.getCategoryRef().setModel(category);

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


	public Media insertOrUpdateMedia(String mediaKey,
									String title,
									String content,
									String speaker,
									String author,
									String imageBlobKey,
									String mediaFileBlobKey,
									String categoryId){


		Media media = getMedia(mediaKey);

		//insert or update
		Date now = new Date();
		if(media != null){
			//update
			media.setModifiedDate(now);
		}
		else if (mediaFileBlobKey!=null){
			//insert
			media = new Media();
			Key ancestorKey = KeyFactory.createKey("Media", "Media");
			Key childKey = Datastore.allocateId(ancestorKey, Media.class);
			media.setKey(childKey);
			media.setPublishedDate(now);
			media.setPublishedDate(now);
			media.setViewCount(0);
			media.setDuration("4:17");
		}
		else return null;

		//set properties for media
		media.setTitle(title);
		media.setContentInfo(content);
		media.setAuthor(author);
		media.setSpeaker(speaker);
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
		Category category = CategoryFactory.getInstance().getCategory(categoryId);
		media.getCategoryRef().setModel(category);

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
	
	
	private Media getMedia(String mediaKey) {
		if (mediaKey!=null&&mediaKey.length()>0) {
			try {
				Key key = KeyFactory.stringToKey(mediaKey);
				Media media = Datastore.get(Media.class, key);
				return media;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public User getMedia() {
		return null;
	}
	public void deleteMedia(String mediakeystring) throws Exception{
		Key categoryKey = KeyFactory.stringToKey(mediakeystring);			
		Datastore.delete(categoryKey);

	}

	public List<Media> getMedia(int offset, int limit,
			MediaQueryMode mode) {

		switch (mode) {
		case MEDIA_GET_ALL:
			Key ancestorKey = KeyFactory.createKey("Media", "Media");
			List<Media> lMedia = null;
			ModelQuery<Media> mediaQuery  = Datastore.query(Media.class,ancestorKey);

			lMedia = mediaQuery.asList();

			return lMedia;

		default:
			break;
		}
		return null;
	}
}


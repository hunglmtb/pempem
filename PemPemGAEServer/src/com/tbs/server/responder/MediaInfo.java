package com.tbs.server.responder;

import org.slim3.datastore.ModelRef;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import com.tbs.server.model.Category;
import com.tbs.server.model.Media;
import com.tbs.server.util.Common;

public class MediaInfo {
	private static ImagesService imagesService = ImagesServiceFactory.getImagesService();

	public static String HOST_NAME = "127.0.0.1";
	//core fields 
	private String mTitle;
	private String mSpeaker;
	private String mContentInfo;
	private String mAuthor;
	private int mMediaType = Common.MEDIA_TYPE_AUDIO;

	//complete fields
	
	private String mMediaId;				//match to Key field in model
	private String mCategoryId;				//match to category reference field in model
	private String mMediaFileUrl;
	private String mLikeCount;
	private String mCommentCount;
	private String mDuration;
	private String mPublishedDate;
	private String mMediaLinkUrl;
	private int mViewCount;
	private String mMediaImageThumbUrl;
	private String mMediaImageUrl;
	
	//specific user fields
	private String mEnjoyDonePercent;
	private int mTimeDurationAgo;
	

	public MediaInfo(String mediaID, String title, String mediaFileUrl, int mediaType) {
		this.mMediaId = mediaID;
		this.mTitle = title;
		this.mMediaFileUrl = mediaFileUrl;
		this.mMediaType = mediaType;
	}
	
	

	public MediaInfo(String mMediaID, String mTitle, String mMediaUrl,
			String mCategoryID, String mLikeCount, String mCommentCount,
			String mBonusInfo, String mContentInfo, String mDuration,
			String mediaLinkUrl) {
		super();
		this.mMediaId = mMediaID;
		this.mTitle = mTitle;
		this.mMediaFileUrl = mMediaUrl;
		this.mCategoryId = mCategoryID;
		this.mLikeCount = mLikeCount;
		this.mCommentCount = mCommentCount;
		this.mSpeaker = mBonusInfo;
		this.mContentInfo = mContentInfo;
		this.mDuration = mDuration;
		this.mMediaLinkUrl = mediaLinkUrl;
	}


	public MediaInfo(Media media) {
		if (media!=null) {
			
			//key field
			this.mMediaId = KeyFactory.keyToString(media.getKey());
			 ModelRef<Category> cr = media.getCategoryRef();
			 
			if (cr!=null&&cr.getModel()!=null) {
				this.mCategoryId = cr.getModel().getCategoryId();
			}
			else{
				this.mCategoryId = "";
			}
			
			//core fields 
			this.mTitle = media.getTitle();
			this.mSpeaker = media.getSpeaker();
			this.mContentInfo = media.getContentInfo();
			this.mAuthor = media.getAuthor();
			
			this.mCommentCount = media.getCommentCount();
			this.mMediaFileUrl = media.getMediaFileUrl();
			this.mLikeCount = media.getLikeCount();
			this.mDuration = media.getDuration();
			this.mMediaLinkUrl = media.getMediaLinkUrl();
			this.mPublishedDate = media.getPublishedDate().toString();
			this.mViewCount = media.getViewCount();
			this.mMediaType = media.getMediaType();
			this.mMediaImageThumbUrl = media.getMediaImageThumbUrl();
			BlobKey imageKey = new BlobKey(media.getMediaImageUrl());
			this.mMediaImageUrl = imagesService.getServingUrl(imageKey);
			this.mMediaImageUrl = this.mMediaImageUrl.replaceFirst("0.0.0.0", HOST_NAME);
		}
	}

	public String getSpeaker() {
		return mSpeaker;
	}

	public String getMediaImageThumbUrl() {
		return mMediaImageThumbUrl;
	}



	public String getMediaImageUrl() {
		return mMediaImageUrl;
	}



	public String getAuthor() {
		return mAuthor;
	}



	public String getPublishedDate() {
		return mPublishedDate;
	}


	public String getCategoryId() {
		return mCategoryId;
	}

	public String getLikeCount() {
		return mLikeCount;
	}

	public String getCommentCount() {
		return mCommentCount;
	}


	public String getContentInfo() {
		return mContentInfo;
	}



	public String getDuration() {
		return mDuration;
	}



	public String getMediaLinkUrl() {
		return mMediaLinkUrl;
	}


	public String getMediaId() {
		return mMediaId;
	}

	public String getTitle() {
		return mTitle;
	}

	public String getMediaFileUrl() {
		return mMediaFileUrl;
	}

	public int getMediaType() {
		return mMediaType;
	}

	public int getViewCount() {
		return mViewCount;
	}



	public String getEnjoyDonePercent() {
		return mEnjoyDonePercent;
	}


	public int getTimeDurationAgo() {
		return mTimeDurationAgo;
	}
}

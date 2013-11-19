package com.tbs.server.model;

import java.util.Date;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.InverseModelListRef;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

import com.google.appengine.api.datastore.Key;
import com.tbs.server.meta.HistoryMeta;

@Model
public class Media {
	/////////////////// Attributes ///////////////////////////////
	@Attribute(primaryKey = true)
	private Key key;

	/////////////////// Relationship ///////////////////////////////
	private ModelRef<Category> categoryRef = new ModelRef<Category>(Category.class);

	@Attribute(persistent = false)
	private InverseModelListRef<History, Media> historyListRef = new  InverseModelListRef<History,Media>
	(History.class,
			HistoryMeta.get().mediaRef.getName(),
			this);

	/////////////////// fields ///////////////////////////////
	//input fields
	private String title;
	private String speaker;
	private String contentInfo;
	private String author;

	//TODO update later fields
	private int mediaType;

	//upload field
	private String mediaFileUrl;
	private String mediaImageUrl;

	//auto complete fields
	private String mediaLinkUrl;
	private String mediaImageThumbUrl;
	private String duration;
	private int viewCount;
	private String commentCount;
	private String likeCount;
	//	private int mediaType = Common.MEDIA_TYPE_AUDIO;

	//for admin
	private Date publishedDate;
	private Date modifiedDate;

	//unused fields
	private String mediaId;

	/////////////////// Attribute getters/setters //////////////////
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public InverseModelListRef<History, Media> getHistoryListRef() {
		return historyListRef;
	}

	public ModelRef<Category> getCategoryRef() {
		return categoryRef;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMediaFileUrl() {
		return mediaFileUrl;
	}

	public void setMediaFileUrl(String mediaFileUrl) {
		this.mediaFileUrl = mediaFileUrl;
	}

	public String getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(String likeCount) {
		this.likeCount = likeCount;
	}

	public String getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(String commentCount) {
		this.commentCount = commentCount;
	}

	public String getSpeaker() {
		return speaker;
	}

	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}

	public String getContentInfo() {
		return contentInfo;
	}

	public void setContentInfo(String contentInfo) {
		this.contentInfo = contentInfo;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getMediaLinkUrl() {
		return mediaLinkUrl;
	}

	public void setMediaLinkUrl(String mediaLinkUrl) {
		this.mediaLinkUrl = mediaLinkUrl;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public int getMediaType() {
		return mediaType;
	}

	public void setMediaType(int mediaType) {
		this.mediaType = mediaType;
	}

	public String getMediaImageThumbUrl() {
		return mediaImageThumbUrl;
	}

	public void setMediaImageThumbUrl(String mediaImageThumbUrl) {
		this.mediaImageThumbUrl = mediaImageThumbUrl;
	}

	public String getMediaImageUrl() {
		return mediaImageUrl;
	}

	public void setMediaImageUrl(String mediaImageUrl) {
		this.mediaImageUrl = mediaImageUrl;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
}

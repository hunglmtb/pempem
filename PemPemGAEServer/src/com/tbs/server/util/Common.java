package com.tbs.server.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


public class Common {

	public static final String CATEGORY_ID_HEADER = "HEADER";
	public static final String CATEGORY_ID_SEARCH = "SEARCH";
	public static final String CATEGORY_ID_HISTORY = "HISTORY";
	public static final String CATEGORY_ID_TIMER = "TIMER";
	public static final String CATEGORY_ID_NEWSFEED = "NEWSFEED";
	public static final String CATEGORY_ID_SETTING = "SETTING";
	public static final String CATEGORY_ID_EXIT = "EXIT";

	public static final String CATEGORY_ID_NAME_01 = "CATEGORY01";
	public static final String CATEGORY_ID_NAME_02 = "CATEGORY02";
	public static final String CATEGORY_ID_NAME_03 = "CATEGORY03";
	public static final String CATEGORY_ID_NAME_04 = "CATEGORY04";
	public static final String CATEGORY_ID_NAME_05 = "CATEGORY05";
	public static final String CATEGORY_ID_NAME_06 = "CATEGORY06";
	public static final String CATEGORY_ID_NAME_07 = "CATEGORY07";
	public static final String CATEGORY_ID_NAME_08 = "CATEGORY08";

	public static final int MEDIA_TYPE_AUDIO = 0;
	public static final int MEDIA_TYPE_VIDEO = 1;
	public static final int MEDIA_TYPE_IMAGE = 2;
	private static final String APP_DOMAIN = "langnghe.com";
	
	//key json
	public static final String JSON_HISTORY_KEY = "historykey";
	public static final String JSON_CATEGORY_KEY = "categorykey";
	public static final String JSON_MEDIA_KEY = "mediakey";
	public static final String JSON_USER_KEY = "userkey";
	
	public static String[] mediaId;
	public static List<String> mediaFileUrl;
	public static List<String> mediaLinkUrl;
	public static List<String> mediaImageThumbUrl;
	public static List<String> mediaImageUrl;
	public static int[] mediaHistoryTime;


	public static List<String> loadArray(String path, ServletContext servletContext){
		List<String> lines = new ArrayList<String>();
		//		BufferedReader reader = null;
		try {
			InputStream resourceContent = servletContext.getResourceAsStream(path);
			BufferedReader reader = new BufferedReader(new InputStreamReader(resourceContent));

			//			reader = new BufferedReader(new FileReader(path));
			String line = null;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
			reader.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return lines;
	}

	public static void initTestData(ServletContext servletContext) {
		/*mediaId = loadArray("mediaid.txt");
		title = loadArray("title.txt");
		categoryId = loadArray("categoryId.txt");
		likeCount = loadArray("likeCount.txt");
		commentCount = loadArray("commentCount.txt");
		speaker = loadArray("speaker.txt");
		contentInfo = loadArray("contentInfo.txt");
		duration = loadArray("mediaid.txt");
		author = loadArray("mediaid.txt");
		publishedDate = loadArray("mediaid.txt");
		viewCount = loadArray("mediaid.txt");*/
		//		mediaType = loadArray("mediaid.txt");
		mediaLinkUrl = loadArray("/WEB-INF//mediaLinkUrl.txt",servletContext);
		mediaFileUrl = loadArray("/WEB-INF//mediaFileUrl.txt",servletContext);
		mediaImageThumbUrl = loadArray("/WEB-INF//mediaImageThumbUrl.txt",servletContext);
		mediaImageUrl = loadArray("/WEB-INF//mediaImageUrl.txt",servletContext);
		mediaHistoryTime = loadIntArray("/WEB-INF//mediaHIstory.txt",servletContext);
	}

	private static int[] loadIntArray(String path,
			ServletContext servletContext) {
		int[] lines = new int[128];
		try {
			InputStream resourceContent = servletContext.getResourceAsStream(path);
			BufferedReader reader = new BufferedReader(new InputStreamReader(resourceContent));

			String line = null;
			int i = 0;
			while ((line = reader.readLine()) != null) {
				lines[i++]=Integer.valueOf(line);
			}
			reader.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return lines;
	}

	public static String initMediaLinkUrl(Key key) {
		try {
			String url = "http://"+APP_DOMAIN+"/media/"+KeyFactory.keyToString(key);
			return url;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean validateString(String string) {
		return string!=null&&string.length()>0;
	}

	public static String getKeyString(Key key) {
		if (key!=null) {
			return KeyFactory.keyToString(key);			
		}
		else{
			return "";
		}
	}


	public static String getJsonString(HttpServletRequest request) {
		StringBuilder stringJsonData = null;
		try {
			stringJsonData = Util.converByteToString(request.getInputStream());
			return stringJsonData.toString();
		} catch (IOException e) {
			Logger.getLogger(Common.class.getName()).warning(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	public static String stackTraceToString(Throwable e) {
	    StringBuilder sb = new StringBuilder();
	    sb.append(e.getMessage()+"\n-------------------------\n");
	    for (StackTraceElement element : e.getStackTrace()) {
	        sb.append(element.toString());
	        sb.append("\n");
	    }
	    return sb.toString();
	}

}

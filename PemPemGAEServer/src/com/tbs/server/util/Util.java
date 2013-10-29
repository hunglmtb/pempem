package com.tbs.server.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slim3.repackaged.com.google.gdata.util.common.util.Base64;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class Util {
	public final static String CATEGORY_ID = "categoryId";
	public final static String CATEGORY_NAME = "categoryName";
	
	public final static String ERROR_CODE = "errorCode";
	public final static String MESSAGE = "message";
	public final static String USER_ID = "userId";
	public final static String RECIPE = "Recipe";
	public final static String RECIPE_ID = "recipeId";
	public final static String RECIPE_TITLE = "title";
	public final static String RECIPE_OWNERNAME = "ownerName";
	public final static String RECIPE_GENRE = "genre";
	public final static String RECIPE_PERSONS = "persons";
	public final static String RECIPE_STATUS = "status";
	public final static String RECIPE_LIKES = "likes";
	public final static String RECIPE_NICK_NAME = "nickname";
	public static final String RECIPE_EMAIL = "email";
	public final static String RECIPE_FBLINK = "fbLink";
	public final static String RECIPE_HISTORYAWARD = "historyAward";
	public final static String RECIPE_MAIN_PHOTO = "mainPhoto";
	public final static String RECIPE_COOKING_STEP = "cookingStep";
	public final static String RECIPE_CREATED_DATE = "createdDate";
	public final static String RECIPE_MODIFIED_DATE = "modifiedDate";
	public final static String RECIPE_INGREDIENT_LIST = "ingredientList";
	public final static String COOKING_STEP_ORDER = "stepOrder";
	public final static String COOKING_EXPLANATION = "explanation";
	public final static String INGREDIENT_ITEM_NAME = "name";
	public final static String INGREDIENT_ITEM_QUANTITY = "quantity";
	public final static String INGREDIENT_UNIT = "unit";
	public final static String USER_NAME = "username";
	public final static String USER_FACEBOOK_ID = "facebookId";
	public final static String USER_MAC_ADDRESS = "macAddress";
	public final static String MESSAGE_PARSE_JSON_FAIL = "parse json fail";
	public final static String MESSAGE_SAVE_RECIPE_FAIL = "save recipe fail";
	public final static String MESSAGE_UPDATE_LIKE_RECIPE_FAIL = "update like recipe fail";
	public final static String MESSAGE_UPDATE_FBLINK_RECIPE_FAIL = "update face book link recipe fail";
	public final static String MESSAGE_UPDATE_HISTORY_AWARD_RECIPE_FAIL = "update history award recipe fail";
	public final static String MESSAGE_UPDATE_PRIZE_RECIPE_FAIL = "update prize recipe fail";
	public final static String READ_IMAGE_BYTE_FAIL = "Could not completely read request body";
	public final static String MESSAGE_NOT_EXIST_RECIPE = "not exist recipe";
	public final static String MESSAGE_UPDATE_IMAGE_FAIL = "save image fail";
	public final static String MESSAGE_UPDATE_USER_FAIL = "save user fail";
	public final static String MESSAGE_CHECK_USER_FAIL = "user not exist";
	public final static String MESSAGE_ASAVE_ACCOUNT_FAIL = "save account fail";
	public final static String MESSAGE_API_NOT_EXIST = "api key not true";
	public final static String MESSAGE_NOT_EXIST_BYTE_IMAGE_REQUEST = "not get image byte request";
	public final static String MESSAGE_DELETE_RECIPE_FAIL = "can't delete recipe";
	public final static String MESSAGE_UPDATE_COOKING_STEP = "save cooking way step fail";
	public final static String MESSAGE_UPDATE_INGREDIENT_FAIL = "save ingredient item fail";
	public final static String INDEX_METHOR_INSERT = "0";
	public final static String INGREDIENT_INDEX = "index";
	
	public final static String ADMIN = "admin";
	public final static String PERMISSION_A = "permission a";
	public final static String PERMISSION_B = "permission b";
	
	public final static int ADMIN_TYPE = 0;
	public final static int PERMISSION_A_TYPE = 1;
	public final static int PERMISSION_B_TYPE = 2;
	
	public final static int ERROR_CODE_SAVE_SUCCESS = 0;
	public final static int ERROR_CODE_SAVE_FAIL = 1;
	public final static int ERROR_CODE_PARSE_JOSN_FAIL = 2;
	public final static int ERROR_CONVERT_BYTE_TO_STRING_FAIL = 4;
	public final static int ERROR_BYTE_IMAGE_REQUEST = 6;
	public final static int ERROR_CKECK_API_KEY_FAIL = 3;
	public final static int NOT_EXIST_BYTE_REQUEST = 5;
	
	public final static int IMAGE_THUMBNAILS = 1;
	public final static int IMAGE_BIG = 0;
	
	public final static String API_KEY = "apiKey";
	public final static String ORDER_LIKE = "orderLike";
	public static final String SUCCESS = "SUCCESS";
	public static final String DELETE_SUCCESS = "DELETE_SUCCESS";
	public static final String ERROR = "ERROR";
	public static final String DELETE_ERROR = "DELETE_ERROR";


	/**
	 * convert InputStream to String Builder.
	 * 
	 * @param is
	 *            : byte Input Stream.
	 * @return StringBuilder
	 * @throws IOException 
	 */
	public static StringBuilder converByteToString(InputStream is) throws IOException {
		StringBuilder stringJsonData = null;
		// read it with BufferedReader
		BufferedReader br = new BufferedReader(new InputStreamReader(is,
				"UTF-8"));
		stringJsonData = new StringBuilder();
		String line = "";
		while ((line = br.readLine()) != null) {
			stringJsonData.append(line);
		}
		br.close();
		return stringJsonData;
	}

	/**
	 * Insert media data into blob store.
	 * 
	 * @param data
	 *            : byte [] array
	 * @return BlobKey
	 * @throws IOException
	 */
	public static BlobKey insertBlobFile(byte[] data) throws IOException {

		FileService fileService = FileServiceFactory.getFileService();
		// Create a new Blob file with type "octet-stream"
		AppEngineFile file = fileService
				.createNewBlobFile("application/octet-stream");
		// Open a channel to write to it
		FileWriteChannel writeChannel = fileService
				.openWriteChannel(file, true);
		// This time we write to the channel directly
		writeChannel.write(ByteBuffer.wrap(data));
		// Now finalize
		writeChannel.closeFinally();
		return fileService.getBlobKey(file);
	}

	public static  boolean checkApi(String apiKey) {
		boolean isCheck = false;
		if (apiKey.equals("agtrdGNhbWNsb3VuZHILCxIEVXNlchjQAgw")){
			isCheck = true;
		}
		return isCheck;
	}
	
	/**
	 * parseParamUrl
	 * @param res : HttpServletRequest
	 * @param name : name of param
	 * @return String
	 */
	public static String parseParamUrl(HttpServletRequest res, String name) {
		String response = "";
		try {
			if (res.getParameter(name) != null && !res.getParameter(name).equals("")) {
				response = URLDecoder.decode(res.getParameter(name),"UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	/**
	 * networkConnectionGet
	 * @param path : url get data.
	 * @return String : data response
	 */
	public static String networkConnectionGet(String path) throws ProtocolException,
	IOException, InterruptedException{
		int resCode = -1;
		String result = "";
		String line = "";
		try {
			URL url = new URL(path);
			URLConnection urlConn = url.openConnection();
			HttpURLConnection  httpConn = (HttpURLConnection) urlConn;
			httpConn.setRequestMethod("GET");
			httpConn.connect();
			resCode = httpConn.getResponseCode();

			if (resCode == HttpURLConnection.HTTP_OK) {

				InputStreamReader myInputStreamReader = new InputStreamReader(
						httpConn.getInputStream());
				BufferedReader rd = new BufferedReader(myInputStreamReader);
				while ((line = rd.readLine()) != null) {
					result += line;
				}
			}
			httpConn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	
	public static JSONObject parseJsonFromBase64(String base64){
		JSONObject returnObject = new JSONObject();
		
		try {
			byte[] bytes = Base64.decode(base64);
			String string = new String(bytes);
			
			returnObject = new JSONObject(string);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnObject;
	}
	
	/**
	 * convert date server to date string Japan.
	 * @param date : date server
	 * @return String
	 */
	public static String convertDatetoString(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, 9);
		date = calendar.getTime();
		String dateResPonse = df.format(date);
		return dateResPonse;
	}
	/**
	 * convert date server to date Japan.
	 * @param date : date server
	 * @return Date
	 */
	public static Date convertDateToDateServer(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, 9);
		return calendar.getTime();
	}
}

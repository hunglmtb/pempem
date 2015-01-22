package com.tbs.server.factories;

import java.util.Date;
import java.util.List;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.ModelQuery;
import org.slim3.datastore.StringAttributeMeta;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.tbs.server.meta.HistoryMeta;
import com.tbs.server.model.History;
import com.tbs.server.model.Media;
import com.tbs.server.model.User;
import com.tbs.server.util.Common;

public class HistoryFactory extends EntityFactory {

	private static HistoryFactory instance = null;

	public static HistoryFactory getInstance() {
		if (instance == null) {
			instance = new HistoryFactory();
		}

		return instance;
	}

	public History insertOrUpdateHistory(String jsonData)  throws JSONException {
		History error = new History();
		error.setErrorMessage("not validatedJsonData");

		JSONObject jsonHistory = new JSONObject(jsonData);
		if (!validatedJsonData(jsonHistory)) {
			return error;
		}
		String historyKey = jsonHistory.has(Common.JSON_HISTORY_KEY)?
				jsonHistory.getString(Common.JSON_HISTORY_KEY):null;


				History history = getHistory(historyKey);
				//Media media = getMedia(historyKey);

				//insert or update
				if(history == null){
					User user = null;
					String userKey = jsonHistory.has(Common.JSON_USER_KEY)?
							jsonHistory.getString(Common.JSON_USER_KEY):null;
							if(Common.validateString(userKey)){
								user = UserFactory.getInstance().getUser(userKey);
							}
							if (user==null) {
								error.setErrorMessage("user no found");
								return null;
							}

							String mediaKeyString = jsonHistory.has(Common.JSON_MEDIA_KEY)?jsonHistory.getString(Common.JSON_MEDIA_KEY):null;
							Media media = MediaFactory.getInstance().getMedia(mediaKeyString);
							if (media==null){
								error.setErrorMessage("media no found");
								return null;
							}

							history = getHistory(user.getKeyString(), mediaKeyString);

							if (history==null) {
								history = new History();
								Key ancestorKey = user.getKey();
								Key childKey = Datastore.allocateId(ancestorKey, History.class);
								history.setKey(childKey);
								history.setMediaKeyString(mediaKeyString);
							}
							else{
								history.setViewCount(0);
							}

				}

				//set properties for history
				//		int viewCount = history.getViewCount();
				history.updateViewCount(jsonHistory.getBoolean(HistoryMeta.get().done.getName()),jsonHistory.getInt(HistoryMeta.get().donePercent.getName()));
				Date now = new Date();
				history.setLastSeenTime(now);
				history.setCurrentSeenTime(jsonHistory.getString(HistoryMeta.get().currentSeenTime.getName()));

				Datastore.put(history);

				return history;
	}

	private boolean validatedJsonData(JSONObject jsonHistory) {

		boolean result = (jsonHistory.has(Common.JSON_USER_KEY))&&
				(jsonHistory.has(Common.JSON_MEDIA_KEY));

		return result;
	}

	private History getHistory(String historyKey) {
		if (Common.validateString(historyKey)) {
			Key key = KeyFactory.stringToKey(historyKey);
			History history = Datastore.get(History.class, key);
			return history;
		}
		return null;
	}

	public History getHistory(String userKeyString,String mediaKeyString) {
		if (Common.validateString(userKeyString)&&Common.validateString(mediaKeyString)) {
			Media media = MediaFactory.getInstance().getMedia(mediaKeyString);

			if (media!=null) {
				HistoryMeta  historyMeta  =  new  HistoryMeta (); 
				StringAttributeMeta<History> mediaKeyStringMeta = historyMeta.mediaKeyString;
				Key userKey = KeyFactory.stringToKey(userKeyString);
				History  history  = Datastore.query(History.class,userKey)
						.filter(mediaKeyStringMeta.equal(mediaKeyString))
						.asSingle();
				return history;
			}
		}
		return null;
	}	

	public List<History> getHistories(String userKeyString) {
		List<History> historyEntities = null;
		if (Common.validateString(userKeyString)) {

			Key userKey = KeyFactory.stringToKey(userKeyString);

			ModelQuery<History> historyQuery  = Datastore.query(History.class,userKey);

			int limit = 10;
			int offset = 0;
			historyQuery.limit(limit);
			historyQuery.offset(offset);
			//mediaQuery.
			historyEntities = historyQuery.asList();

		}

		return historyEntities;
	}


	public boolean deleteHistory(String historyKeystring) {
		if (Common.validateString(historyKeystring)) {
			Key historyKey = KeyFactory.stringToKey(historyKeystring);			
			Datastore.delete(historyKey);
			return true;
		}
		return false;
	}
}


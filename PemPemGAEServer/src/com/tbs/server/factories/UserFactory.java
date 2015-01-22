package com.tbs.server.factories;

import java.util.Date;
import java.util.List;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.ModelQuery;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.tbs.server.meta.UserMeta;
import com.tbs.server.model.User;
import com.tbs.server.util.Common;
import com.tbs.server.util.Util;


public class UserFactory extends EntityFactory {

	private static final String USER = "User";
	private static UserFactory instance = null;

	public static UserFactory getInstance() {
		if (instance == null) {
			instance = new UserFactory();
		}

		return instance;
	}

	public boolean insertOrUpdateUser(User user) {
		return false;
	}
	public User getUser(String userKey) {
		if (Common.validateString(userKey)) {
			Key key = KeyFactory.stringToKey(userKey);
			User user = Datastore.get(User.class, key);
			return user;
		}
		return null;
	}
	public boolean deleteUser() {
		return false;
	}


	/**
	 * insertOrUpdateUser
	 * @param userId : user id
	 * @param facebookId : facebook id
	 * @param macAddress : mac address
	 * @param facebookAccount 
	 * @param userName : user name
	 * @return Key : user key
	 */
	public Key insertOrUpdateUser(String userId,
			String username, String macAddress) {
		User user = null;
		if (!Util.INDEX_METHOR_INSERT.equals(userId)) {
			Key userKey = KeyFactory.stringToKey(userId);
			user = getUser(userKey);
		}

		if(user==null&&macAddress != null){
			user = getUserByMacAddress(macAddress);
		}

		if(user == null){
			user = new User();
			if(userId.equals(Util.INDEX_METHOR_INSERT)){
				user.setKey(Datastore.allocateId(User.class));
				user.setRegisteredDate(new Date());
			}
			else{
				Key userKey = KeyFactory.stringToKey(userId);
				user.setKey(userKey);
			}
		}

		if(macAddress != null){
			user.setMacAddress(macAddress);
		}
		user.setUsername(username);
		user.setModifiedDate(new Date());
		Datastore.put(user);

		return user.getKey();
	}

	/**
	 * get user by mac address.
	 * @param macAddress : mac address of device.
	 * @return User
	 */
	public User getUserByMacAddress(String macAddress) {
		User user = null;
		if (Common.validateString(macAddress)) {
			user = Datastore.query(User.class)
					.filter(UserMeta.get().macAddress.getName(), FilterOperator.EQUAL, macAddress)
					.asSingle();

		}
		return user;
	}


	/**
	 * getUser
	 * @param userKey : user key
	 * @return User
	 */
	public User getUser(Key userKey){
		User user = null;
		user = Datastore.get(User.class, userKey);
		return user;
	}


	public List<User> getUsers() {
		List<User> users = null;
		Key ancesstorkey = KeyFactory.createKey(USER, USER);
		ModelQuery<User> userQuery  = Datastore.query(User.class,ancesstorkey);
		int limit = 20;
		int offset = 0;
		userQuery.limit(limit);
		userQuery.offset(offset);
		users = userQuery.asList();
		return users;
	}

	public User registerUser(String macAddress, String userName) {
		User user = null;
		if (Common.validateString(macAddress)) {
			user = getUserByMacAddress(macAddress);
			if (user ==null) {
				user = new User();
				Key ancestorKey = KeyFactory.createKey(USER, USER);
				Key key = Datastore.allocateId(ancestorKey, User.class);
				user.setKey(key);
				user.setMacAddress(macAddress);
				user.setUsername(userName);
				Datastore.put(user);
			}
		}
		return user;
	}
}


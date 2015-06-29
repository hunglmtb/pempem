package com.tbs.server.controller;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tbs.server.factories.HistoryFactory;
import com.tbs.server.factories.UserFactory;
import com.tbs.server.model.History;
import com.tbs.server.model.User;
import com.tbs.server.util.Common;


@Controller
@RequestMapping("/user")
public class UserController {

	@RequestMapping(value="/test/create/user")
	@ResponseBody
	public List<User> createUser() {
		UserFactory userFactory = UserFactory.getInstance();
		List<User> users = userFactory.getUsers();
		if (users==null||users.size()<=0) {
			
			String [] macaddress = new String[]{"00:09:5B:EC:EE:F1",
												"00:09:5B:EC:EE:F2",
												"00:09:5B:EC:EE:F3",
												"00:09:5B:EC:EE:F4",
												"00:09:5B:EC:EE:F5",
												"00:09:5B:EC:EE:F6",
												"00:09:5B:EC:EE:F7",
												"00:09:5B:EC:EE:F8",
												"00:09:5B:EC:EE:F9",
												"00:09:5B:EC:EE:FA",
												"00:09:5B:EC:EE:FB",
												"00:09:5B:EC:EE:FC",
												"00:09:5B:EC:EE:FD",
												"00:09:5B:EC:EE:FE"};
			for (int i = 0; i < macaddress.length; i++) {
				userFactory.registerUser(macaddress[i], "kaka "+i);			
			}
			users = userFactory.getUsers();
		}
		return users;
	}
	/*----------------------------------------------------------------------HISTORY-----------------------------------------------------*/


	//get histories
	@RequestMapping(value = "/get/macaddress")
	@ResponseBody
	public User getUserByMacAddress(HttpServletRequest req,
			@RequestParam("macaddress") String macaddress) {
		User user = null;
		try {
			if (Common.validateMacAddress(macaddress)) {
				user = UserFactory.getInstance().getUserByMacAddress(macaddress);
				if (user==null) {
					user = UserFactory.getInstance().registerUser(macaddress, "TBD1");
				}
			}
			else{
				user = new User();
				user.setErrorMessage("macadress invalid");
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			user = new User();
			user.setErrorMessage(Common.stackTraceToString(e));
		}
		return user;		
	}

	//get histories
	@RequestMapping(value = "/get/key")
	@ResponseBody
	public User getUserByKey(HttpServletRequest req,
			@RequestParam("key") String keyString) {
		User user = null;
		try {
			if (Common.validateString(keyString)) {
				user = UserFactory.getInstance().getUser(keyString);
			}
			else{
				user = new User();
				user.setErrorMessage("key invalid");
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			user = new User();
			user.setErrorMessage(Common.stackTraceToString(e));
		}
		return user;		
	}
	

	//add api
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public User saveUser(HttpServletRequest req){
		User user = null;
		try {
			String jsonDataString = Common.getJsonString(req);
			if (jsonDataString!=null) {
				user = UserFactory.getInstance().insertOrUpdateUser(jsonDataString);
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			user = new User();
			user.setErrorMessage(Common.stackTraceToString(e));
		}
		return user;		
	}
	
	@RequestMapping(value = "/delete")
	@ResponseBody
	public String deleteUser(HttpServletRequest req,
			@RequestParam("userkey") String userKeyString) {
		String resprond = "none";
		try {
			boolean result = UserFactory.getInstance().deleteUser(userKeyString);
			resprond = result?"OK":"key empty";
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			resprond = Common.stackTraceToString(e);
		}
		return resprond;		
	}

	/*----------------------------------------------------------------------HISTORY-----------------------------------------------------*/

	//add api
	@RequestMapping(value = "/history/save", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public History addHistory(HttpServletRequest req){
		History history = null;
		try {
			String jsonDataString = Common.getJsonString(req);
			if (jsonDataString!=null) {
				history = HistoryFactory.getInstance().insertOrUpdateHistory(jsonDataString);
			}
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			history = new History();
			history.setErrorMessage(Common.stackTraceToString(e));
		}
		return history;		
	}

	//get histories
	@RequestMapping(value = "/history/gets")
	@ResponseBody
	public List<History> getHistories(HttpServletRequest req,
			@RequestParam("userkey") String userKeyString) {
		List<History> history;
		try {
			history = HistoryFactory.getInstance().getHistories(userKeyString);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			history = new ArrayList<>();
			History error = new History();
			error.setErrorMessage(Common.stackTraceToString(e));
			history.add(error);
		}
		return history;		
	}

	@RequestMapping(value = "/history/get")
	@ResponseBody
	public History getHistory(HttpServletRequest req,
			@RequestParam("userkey") String userKeyString,
			@RequestParam("mediakey") String mediaKeyString) {
		History history = null;
		try {
			history = HistoryFactory.getInstance().getHistory(userKeyString, mediaKeyString);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			history = new History();
			history.setErrorMessage(Common.stackTraceToString(e));
		}
		if (history==null) {
			history = new History();
			history.setErrorMessage("history not exist");
		}
		return history;		
	}

	@RequestMapping(value = "/history/delete")
	@ResponseBody
	public String deleteHistory(HttpServletRequest req,
			@RequestParam("historykey") String historyKeyString) {
		String resprond = "none";
		try {
			boolean result = HistoryFactory.getInstance().deleteHistory(historyKeyString);
			resprond = result?"OK":"key empty";
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			resprond = Common.stackTraceToString(e);
		}
		return resprond;		
	}
}

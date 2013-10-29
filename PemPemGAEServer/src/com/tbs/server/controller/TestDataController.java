package com.tbs.server.controller;

import static com.tbs.server.util.Common.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.api.datastore.Key;
import com.tbs.server.factories.UserFactory;
import com.tbs.server.model.User;
import com.tbs.server.responder.CategoryRow;
import com.tbs.server.responder.MediaInfo;
import com.tbs.server.util.Util;


@Controller
@RequestMapping("/testdata")
public class TestDataController {


	@RequestMapping("/create")
	@ResponseBody
	public boolean makeTestData() {

		boolean result = makeCategories();
		result = result&&makeMedia();
		result = makeUser();
		return result;
	}

	@RequestMapping("/getcategories")
	@ResponseBody
	public List<MediaInfo> getMediaList() {
		return null;
	}


	@RequestMapping("/deletedata")
	@ResponseBody
	public List<MediaInfo> deleteTestData() {
		return null;
	}

	@RequestMapping("/updateuser")
	@ResponseBody
	public boolean testUpdateUser() {
		UserFactory uf = UserFactory.getInstance();
		Key key = null;
		key = uf.insertOrUpdateUser("aglub19hcHBfaWRyCgsSBFVzZXIYAQw","updatedUser", "MAC_updated");
		return key!=null;
	}
	
	@RequestMapping("/updateusermac")
	@ResponseBody
	public boolean testUpdateUserMac() {
		UserFactory uf = UserFactory.getInstance();
		Key key = null;
		key = uf.insertOrUpdateUser(Util.INDEX_METHOR_INSERT,"updatedUserMac", "MAC_ABC_DEF2");
		return key!=null;
	}


	private boolean makeUser() {
		UserFactory uf = UserFactory.getInstance();
		boolean result  = true;
		Key key = null;

		for (int i = 0; i < 10; i++) {
			key = uf.insertOrUpdateUser(Util.INDEX_METHOR_INSERT,"user"+i, "MAC_ABC_DEF"+i);
			result = result&&(key!=null);
		}

		return result;
	}


	private boolean makeMedia() {
		// TODO Auto-generated method stub
		return false;
	}


	private boolean makeCategories() {
		// TODO Auto-generated method stub
		return false;
	}

}

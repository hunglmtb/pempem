package com.tbs.server.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.api.datastore.Key;
import com.tbs.server.factories.SampleFactory;
import com.tbs.server.factories.UserFactory;
import com.tbs.server.model.Sample;
import com.tbs.server.responder.MediaInfo;
import com.tbs.server.util.Util;


@Controller
@RequestMapping("/test")
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
	
	@RequestMapping("/sample")
	@ResponseBody
	public List<Sample> getSample() {
		return SampleFactory.getInstance().getSamples();
	}
	@RequestMapping("/create/sample")
	@ResponseBody
	public List<Sample> generateSample() {
		SampleFactory sampleFacory = SampleFactory.getInstance();
		sampleFacory.generateSample();
		return sampleFacory.getSamples();
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

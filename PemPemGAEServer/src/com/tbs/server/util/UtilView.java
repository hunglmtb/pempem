package com.tbs.server.util;

import java.util.List;

import com.google.gwt.rpc.server.Pair;



public class UtilView {
	
	private static UtilView instance = null;

	public static UtilView getInstance() {
		if (instance == null) {
			instance = new UtilView();
		}
		return instance;
	}

	public String buildDropDownList(List<Pair<String, String>> list) {
		
		String dropDownList = "<select>";
		for (Pair<String, String> pair : list) {
			dropDownList += "<option value=\""+pair.getA()+"\">"+pair.getB()+"</option>";
		}
		dropDownList += "</select>";
		return dropDownList ;
	}

}

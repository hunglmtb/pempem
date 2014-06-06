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
		
		String dropDownList = "";
		for (Pair<String, String> pair : list) {
			dropDownList += "<option value=\""+pair.getA()+"\">"+pair.getB()+"</option>";
		}
		//dropDownList += "</select>";
		return dropDownList ;
	}

	public String buildPaginatorHtml(int aPage, int offset, int slimit, int size) {
		String prev = null;
		String next = null;
		int realPage = aPage;
		int nextPage = aPage+1;
		if(aPage <=1){
			prev = "<span class=\"disabled_tnt_pagination\">Prev</span>";
			realPage = 1;
			nextPage = 2;
		}
		else{
			prev = "<span><a href=\"/admin/media/" +(aPage-1)+
					"\">Prev</a></span>";
		}
		
		if(slimit > size){
			next = "<span class=\"disabled_tnt_pagination\">Next</span>";
		}
		else{
			next = "<span><a href=\"/admin/media/" +nextPage+
					"\">Next</a></span>";
		}
		
		String tag = "<div id=\"tnt_pagination\">" +prev +
				"<span class=\"active_tnt_link\">" + realPage+
				"</span>" +next +
				"</div>";
		return tag;
	}

}

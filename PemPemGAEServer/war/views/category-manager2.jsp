<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tbs.server.responder.CategoryInfo"%>
<%@ page import="com.tbs.server.factories.CategoryFactory"%>
<%@ page import="com.tbs.server.util.UtilView"%>
<%@ page import="com.google.gwt.rpc.server.Pair"%>
<%@ page import="java.util.List"%>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory"%>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService"%>

<%
	BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();
	Object result = request.getAttribute("result");
	Object rs = request.getAttribute("categoryList");
	List<CategoryInfo> categoryList = new ArrayList<CategoryInfo>();
	String notificationText = "";
	if (rs != null) {
		categoryList.addAll((ArrayList<CategoryInfo>) rs);
	} else {
		notificationText = "no categoryList";
	}

	List<Pair<String, String>> list = CategoryFactory.getInstance().getCategoryPairList();
	String dropDownList = UtilView.getInstance().buildDropDownList(list);
	
	//paginator 
	String paginator = (String)request.getAttribute("paginator");
	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Category Manager</title>
<script type="text/javascript" src="/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="/js/jquery.pagination.js"></script>
<script type="text/javascript" src="/js/media-manger.js" charset="UTF-8"></script>
<link rel="stylesheet" href="/css/styles.css" type="text/css">
</head>
<body onload='formLoad()'>
	<button class=buttoninsert onClick="displaymenu()">Back</button>
	<div class="module">
		<div class="module-table-body">
		
<%
			if (result != null) {
				out.print("<div><p>add result : " + result.toString()
						+ "<p/></div>");
			}
		%>
			
<table id="myTable" class="gridtable">
      <thead>
		<tr>
			<th style="width: 13%">Category ID</th>
			<th style="width: 14%">Category Name</th>
			<th style="width: 16%">Key</th>
<!-- 			<th style="width: 15%">Modified Date</th>
 -->		</tr>
	</thead>
  
<%
  	for (CategoryInfo categoryInfo : categoryList) {
  %>
	<tr id="view<%=categoryInfo.getCategoryKeyString()%>">
		<td><%=categoryInfo.getCategoryId()%></td>
		<td><%=categoryInfo.getCategoryName()%></td>
		<td><%=categoryInfo.getCategoryKeyString()%></td>
		<td><button class=buttoninsert onClick="editMedia('<%=categoryInfo.getCategoryKeyString()%>','<%=categoryInfo.getCategoryId()%>')">Edit</button></td>
	</tr>
	
	<tr id="<%=categoryInfo.getCategoryKeyString()%>" style="background-color: #9370D8;" hidden>
		<form action="<%=blobstoreService.createUploadUrl("/admin/upload")%>" method="post" enctype="multipart/form-data">
			<td><textarea name="categoryid" wrap="virtual"><%=categoryInfo.getCategoryId()%></textarea></td>
			<td><textarea name="categoryname" rows="8" cols="48" wrap="virtual"><%=categoryInfo.getCategoryName()%></textarea></td>
			<td><input type="submit" value="done"></td>
		</form>
			<td><button onClick="cancelEditMedia('<%=categoryInfo.getCategoryKeyString()%>')">cancel</button></td>
	</tr>

<%
	}
%>
			
<tr style="background-color: #DD22EE;"><%=paginator%></tr>	
	<tr style="background-color: #DDA0DD;">
		<form action="/category/add" method="get">
		<td><textarea name="categoryid" wrap="virtual"></textarea><br/><input type="file" name="mediaFile"></td>
		<td><textarea name="categoryname" rows="2" cols="16" wrap="virtual"></textarea></td>
		<td><input type="submit" value="add"></td>
		</form>
	</tr>	
</table>

<!-- End .module-table-body -->
</div>


<!-- End .module -->
<!-- <div>
		
	</div> -->
</body>
</html>
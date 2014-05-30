<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tbs.server.responder.MediaInfo"%>
<%@ page import="java.util.List"%>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory"%>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService"%>

<%
	BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	Object result = request.getAttribute("result");
	Object rs = request.getAttribute("mediaList");
	List<MediaInfo> mediaList = new ArrayList<MediaInfo>();
	mediaList.addAll((ArrayList<MediaInfo>)rs);
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

<!-- CSS goes in the document HEAD or added to your external stylesheet -->
<style type="text/css">
table.gridtable {
	font-family: verdana,arial,sans-serif;
	font-size:11px;
	color:#333333;
	border-width: 1px;
	border-color: #666666;
	border-collapse: collapse;
}
table.gridtable th {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #dedede;
}
table.gridtable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #ffffff;
}
</style>
</head>
<body onload='formLoad()'>
	<button class=buttoninsert onClick="displaymenu()">Back</button>
	<div class="module">
		<div class="module-table-body">
		
<%
	if(result!=null){
		out.print("<div><p>add result : " + result.toString()+"<p/></div>");
	}
%>
<!-- 			<table id="myTable" class="module-table-body">
				<thead>
					<tr>
						<th style="width: 10%">Media Title</th>
						<th style="width: 25%">Nội dung</th>
						<th style="width: 5%">Speaker</th>
						<th style="width: 5%">Author</th>
						<th style="width: 10%">Category</th>
						<th style="width: 10%">Image</th>
						<th style="width: 6%">Media Link</th>
						<th style="width: 8%">shared Link</th>
						<th style="width: 5%">view count</th>
						<th style="width: 5%">duration</th>
						<th style="width: 5%">published date</th>
						<th style="width: 5%">modified date</th>
					</tr>
				</thead>
				<tbody id="contests_table"></tbody>
			</table> -->
			<!-- <div class="bottom_content" id="bottom">
				<div id="Pagination" class="pagination">
					<form style="display: none">
						<p>
							<label for="items_per_page">Number of items per page</label><input
								type="text" value="30" name="items_per_page" id="items_per_page"
								class="numeric" />
						</p>
						<p>
							<label for="num_display_entries">Number of pagination
								links shown</label><input type="text" value="5"
								name="num_display_entries" id="num_display_entries"
								class="numeric" />
						</p>
						<p>
							<label for="num">Number of start and end points</label><input
								type="text" value="2" name="num_edge_entries"
								id="num_edge_entries" class="numeric" />
						</p>
						<p>
							<label for="prev_text">"Previous" label</label><input type="text"
								value="Prev" name="prev_text" id="prev_text" />
						</p>
						<p>
							<label for="next_text">"Next" label</label><input type="text"
								value="Next" name="next_text" id="next_text" />
						</p>
						<input type="button" id="setoptions" value="Set options" />
					</form>
				</div>
			</div> -->
			
<table id="myTable" class="gridtable">
    <thead>
		<tr>
			<th style="width: 10%">Media Title</th>
			<th style="width: 25%">Nội dung</th>
			<th style="width: 5%">Speaker</th>
			<th style="width: 5%">Author</th>
			<th style="width: 10%">Category</th>
			<th style="width: 10%">Image</th>
			<th style="width: 6%">Media Link</th>
			<th style="width: 8%">shared Link</th>
			<th style="width: 5%">view count</th>
			<th style="width: 5%">duration</th>
			<th style="width: 5%">published date</th>
			<th style="width: 5%">modified date</th>
		</tr>
	</thead>
  
<%
for (MediaInfo mediaInfo : mediaList) {
%>
	<tr>
		<td><%=mediaInfo.getTitle()%></td>
		<td><%=mediaInfo.getContentInfo()%></td>
		<td><%=mediaInfo.getSpeaker()%></td>
		<td><%=mediaInfo.getAuthor()%></td>
		<td><%=mediaInfo.getCategoryName()%></td>
		<td><img src="<%=mediaInfo.getMediaImageUrl()%>" alt="" style="width: 72; height : 72; max-height: 100%; max-width: 100%;" align="left"></td>
		<td><a href="/media/action?key=<%=mediaInfo.getMediaFileUrl()%>">action</a></td>
		<td><a href="<%=mediaInfo.getMediaLinkUrl()%>">shared url</a></td>
		<td><%=mediaInfo.getViewCount()%></td>
		<td><%=mediaInfo.getDuration()%></td>
		<td><%=mediaInfo.getPublishedDate()%></td>
		<td><%=mediaInfo.getPublishedDate()%></td>
		<td><button class=buttoninsert onClick="editMedia('<%=mediaInfo.getMediaId()%>')">Edit</button></td>
	</tr>
	
	<tr id="<%=mediaInfo.getMediaId()%>" hidden>
		<form action="<%=blobstoreService.createUploadUrl("/admin/upload")%>" method="post" enctype="multipart/form-data">
			<td><input type="text" name="title" value="<%=mediaInfo.getTitle()%>" ></td>
			<td><input type="text" name="content" value="<%=mediaInfo.getContentInfo()%>"></td>
			<td><input type="text" name="speaker" value="<%=mediaInfo.getSpeaker()%>"></td>
			<td><input type="text" name="author" value="<%=mediaInfo.getAuthor()%>"></td>
			<td><select name="categoryId" id="categorydropdown" onChange=setCategory(this,this.id)></select></td>
			<td><input type="file" name="imageFile"></td>
			<td><input type="file" name="mediaFile"></td>
			<td><input type="submit" value="add"></td>
		</form>
	</tr>

			<%-- <table id="myTable2" class="module-table-body2">
				
				<tbody id="contests_table1">
					<tr class="trtable2listitem">
						<td id="test1"><input type="text" name="title" value="<%=mediaInfo.getTitle()%>" ></td>
						<td style="width: 25%"><input type="text" name="content" value="<%=mediaInfo.getContentInfo()%>"></td>
						<td style="width: 6%"><input type="text" name="speaker" value="<%=mediaInfo.getSpeaker()%>"></td>
						<td style="width: 6%"><input type="text" name="author" value="<%=mediaInfo.getAuthor()%>"></td>
						<td style="width: 6%"><select name="categoryId" id="categorydropdown" onChange=setCategory(this,this.id)></select></td>
						<td style="width: 12%"><input type="file" name="imageFile"></td>
						<td style="width: 12%"><input type="file" name="mediaFile"></td>
						<td style="width: 4%"><input type="submit" value="add"></td>
					</tr>
				</tbody>
			</table> --%>
		
	
<%
}
%>			
</table>

		<%-- <form action="<%=blobstoreService.createUploadUrl("/admin/upload")%>"
			method="post" enctype="multipart/form-data">
			
			<table id="myTable2" class="module-table-body2">
				<thead>
					<tr>
						<th class="trtable2" style="width: 12%">Media Title</th>
						<th class="trtable2" style="width: 25%">Media Content</th>
						<th class="trtable2" style="width: 6%">Speaker</th>
						<th class="trtable2" style="width: 6%">Author</th>
						<th class="trtable2" style="width: 10%">Category</th>
						<th class="trtable2" style="width: 6%">Image Url</th>
						<th class="trtable2" style="width: 6%">Media Link</th>
					</tr>
				</thead>
				<tbody id="contests_table1">
					<tr class="trtable2listitem">
						<td id="test1"><input type="text" name="title"></td>
						<td style="width: 25%"><input type="text" name="content"></td>
						<td style="width: 6%"><input type="text" name="speaker"></td>
						<td style="width: 6%"><input type="text" name="author"></td>
						<td style="width: 6%"><select name="categoryId" id="categorydropdown" onChange=setCategory(this,this.id)></select></td>
						<td style="width: 12%"><input type="file" name="imageFile"></td>
						<td style="width: 12%"><input type="file" name="mediaFile"></td>
						<td style="width: 4%"><input type="submit" value="add"></td>
					</tr>
				</tbody>
			</table>
		</form> --%>

		</div>
		<!-- End .module-table-body -->
	</div>
	<!-- End .module -->
	<!-- <div>
		
	</div> -->
</body>
</html>
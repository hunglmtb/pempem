<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tbs.server.model.Media"%>
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
	Object rs = request.getAttribute("mediaList");
	List<Media> mediaList = new ArrayList<Media>();
	String notificationText = "";
	if (rs != null) {
		mediaList.addAll((ArrayList<Media>) rs);
	} else {
		notificationText = "no mediaList";
	}
	
	CategoryFactory categoryFactory = CategoryFactory.getInstance();
	List<Pair<String, String>> list = categoryFactory.getCategoryPairList();
	String dropDownList = UtilView.getInstance().buildDropDownList(list);
	
	//paginator 
	String paginator = (String)request.getAttribute("paginator");
	
	Object mPage = request.getAttribute("page");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Media Manager</title>
<script type="text/javascript" src="/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="/js/jquery.pagination.js"></script>
<script type="text/javascript" src="/js/moment-with-locales.js"></script>
<script type="text/javascript" src="/js/media-manger.js" charset="UTF-8"></script>
<script type="text/javascript">//<![CDATA[ 
                                          
function addFunction(i) {
	var objectUrl;
	$("#mediaFile"+i).change(function(e){
	    var file = e.currentTarget.files[0];
	   
	    $("#filename"+i).text(file.name);
	    $("#filetype"+i).text(file.type);
	    $("#filesize"+i).text(file.size);
	    
	    objectUrl = URL.createObjectURL(file);
	    $("#audio"+i).prop("src", objectUrl);
	});
	
	var after = '<div><p>Select a .mp3 file</p><audio id="audio'+i+'"></audio>'+
				'<p><label>File Name:</label> <span id="filename'+i+'"></span></p>'+
				'<p><label>File Size:</label> <span id="filesize'+i+'"></span></p>'+
				'<p><label>Song Duration:</label> <span id="duration'+i+'"></span></p></div>';
	$(after).insertAfter("#mediaFile"+i);
	
	$("#audio"+i).on("canplaythrough", function(e){
	    var seconds = e.currentTarget.duration;
	    var duration = moment.duration(seconds, "seconds");
	    
	    var time = "";
	    var hours = duration.hours();
	    if (hours > 0) { time = hours + ":" ; }
	    
	    time = time + duration.minutes() + ":" + duration.seconds();
	    $("#duration"+i).text(time);
	    //$("#inputduration"+i).val(time);
	    //$("#duration").val(time);
	    $( "input[name='duration']" ).each(function(j) {
	    	if(i==j){
			    $(this).val(time);
	    	}
		});
	    
	    URL.revokeObjectURL(objectUrl);
	});

}

$(function(){
	$( "input[name='mediaFile']" ).each(function(i) {
	    $(this).attr('id', "mediaFile"+i);
	    addFunction(i);
	    // You can also add more code here if you wish to manipulate each IMG element further
	});
});//]]>  

</script>

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
			
			//out.print("<div><p>add result : " + mPage+ "<p/></div>");;

		%>
			
<table id="myTable" class="gridtable">
    <thead>
		<tr>
			<th style="width: 10%">Media Title</th>
			<th style="width: 25%">Nội dung</th>
			<th style="width: 4%">Speaker</th>
			<th style="width: 5%">Author</th>
			<th style="width: 10%">Category</th>
			<th style="width: 15%">Image</th>
			<th style="width: 8%">shared Link</th>
			<th style="width: 5%">view count</th>
			<th style="width: 5%">duration</th>
			<th style="width: 5%">published date</th>
			<th style="width: 5%">modified date</th>
		</tr>
	</thead>
  
<%
	int index = 0;
  	for (Media mediaInfo : mediaList) {
		String categoryKeyString = mediaInfo.getCategoryKeyString();
  %>
	<tr id="view<%=mediaInfo.getKeyString() %>">
		<td><a href="/media/action?key=<%=mediaInfo.getMediaFileUrl()%>"><%=mediaInfo.getTitle()%></a></td>
		<td><%=mediaInfo.getContentInfo()%></td>
		<td><%=mediaInfo.getSpeaker()%></td>
		<td><%=mediaInfo.getAuthor()%></td>
		<td><%= categoryFactory.getCategoryName(mediaInfo.getCategoryKeyString(),list) %></td>
		<td><img src="<%=mediaInfo.getMediaImageUrl()%>" alt="" style="width: 72; height : 72; max-height: 100%; max-width: 100%;" align="left"></td>
		<td><a href="<%=mediaInfo.getMediaLinkUrl()%>">shared url</a></td>
		<td><%=mediaInfo.getViewCount()%></td>
		<td><%=mediaInfo.getDuration()%></td>
		<td><%=mediaInfo.getPublishedDate()%></td>
		<td><%=mediaInfo.getPublishedDate()%></td>
		<td><button class=buttoninsert onClick="editMedia('<%=mediaInfo.getKeyString()%>','<%=categoryKeyString%>')">Edit</button></td>
	</tr>
	
	<tr id="<%=mediaInfo.getKeyString()%>" style="background-color: #9370D8;" hidden>
		<form action="<%=blobstoreService.createUploadUrl("/admin/upload")%>" method="post" enctype="multipart/form-data">
			<input type="text" name="mediaKey" value="<%=mediaInfo.getKeyString()%>" hidden/>
			<input type="text" name="page" value="<%=mPage%>" style="display: none;"/>
			<input type="text" name="duration" value="none" style="display: none;"/>
			<td><textarea name="title" wrap="virtual"><%=mediaInfo.getTitle()%></textarea><br/><input type="file" name="mediaFile"></td>
			<td><textarea name="content" rows="8" cols="48" wrap="virtual"><%=mediaInfo.getContentInfo()%></textarea></td>
			<td><textarea name="speaker" wrap="virtual"><%=mediaInfo.getSpeaker()%></textarea></td>
			<td><textarea name="author" wrap="virtual"><%=mediaInfo.getAuthor()%></textarea></td>
			<td><select name="categoryId" id="select<%=mediaInfo.getKeyString()%>" value="<%=categoryKeyString%>"><%=dropDownList%><select></td>
			<td><img src="<%=mediaInfo.getMediaImageUrl()%>" alt="" style="width: 72; height : 72; max-height: 100%; max-width: 100%;" align="left"><br/><input type="file" name="imageFile"></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td><input type="submit" value="done"></td>
		</form>
			<td><button onClick="cancelEditMedia('<%=mediaInfo.getKeyString()%>')">cancel</button></td>
	</tr>

<%
	index++;
	}
%>
			
<tr style="background-color: #DD22EE;"><%=paginator%></tr>	
	<tr style="background-color: #DDA0DD;">
		<form
			action="<%=blobstoreService.createUploadUrl("/admin/upload")%>"
			method="post" enctype="multipart/form-data">
			
		<input type="text" name="page" value="<%=mPage%>" style="display: none;"/>
		<input type="text" name="duration" value="none" style="display: none;"/>
		<td><textarea name="title" wrap="virtual"></textarea><br/><input type="file" name="mediaFile"></td>
		<td><textarea name="content" rows="6" cols="45" wrap="virtual"></textarea></td>
		<td><textarea name="speaker" wrap="virtual"></textarea></td>
		<td><textarea name="author" wrap="virtual"></textarea></td>
		<td><select name="categoryId" id="categorydropdown"></select></td>
		<td><input type="file" name="imageFile"></td>
		<td></td>
		<td><input type="submit" value="add"></td>
		</form>
	</tr>	
</table>

<!-- End .module-table-body -->
</div>


<!-- End .module -->

</body>
</html>
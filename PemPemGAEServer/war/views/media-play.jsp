<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<% String mediaKey = request.getAttribute("mediaKey").toString(); %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Media Play</title>
<link rel="stylesheet" href="/css/styles.css" type="text/css">
</head>
<body>
	<div class="module">
		<audio controls> <source src="/media/play?key=<%= mediaKey %> " type="audio/mpeg"> play media. </audio>
		<!-- End .module-table-body -->
	</div>
	<!-- End .module -->
</body>
</html>
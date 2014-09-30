<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>



<%
	Object mediaUrl = request.getAttribute("mediaUrl");
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>description</title>
</head>

<body style="margin: 0px;">

	<div id="fb-root"></div>
	<script>
		(function(d, s, id) {
			var js, fjs = d.getElementsByTagName(s)[0];
			if (d.getElementById(id))
				return;
			js = d.createElement(s);
			js.id = id;
			js.src = "http://connect.facebook.net/vi_VN/all.js#xfbml=1&appId=493384610740881";
			fjs.parentNode.insertBefore(js, fjs);

		}(document, 'script', 'facebook-jssdk'));
		
		window.onload = function() {
			Android.finishLoading();

		}

	</script>
	<div class="fb-comments" data-href="<%=mediaUrl%>" data-width="470"></div>
	<br />
	<div>comment</div>

</body>
</html>
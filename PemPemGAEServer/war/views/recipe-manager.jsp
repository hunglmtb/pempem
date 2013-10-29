<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="com.google.appengine.api.datastore.KeyFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.ivc.romando.appengine.model.Recipe"%>
<%@page import="com.ivc.romando.appengine.model.IngredientItem"%>
<%@page import="com.ivc.romando.appengine.model.CookingWayStep"%>
<%@ page import ="com.ivc.romando.appengine.util.Util"%>
<%
	List<Recipe> listRecipe= (List<Recipe>)request.getAttribute("listRecipe");
	String sortdir = request.getAttribute("sortdir").toString();
	String permission = (String)request.getAttribute("permission");
	
	String sortby = request.getAttribute("sortby").toString();
	
	int pageSize = 0,numberOfPage = 0, totalRecord = 0, pageNumber = 1;
	pageNumber = Integer.parseInt(request.getAttribute("page").toString());
	totalRecord = Integer.parseInt(request.getAttribute("totalRecord").toString());
	numberOfPage = Integer.parseInt(request.getAttribute("numberOfPage").toString());
	pageSize = Integer.parseInt(request.getAttribute("pageSize").toString());
	int index = 1;
	if(pageNumber == 1){
		index = 1;
	}else{
		index = pageSize * (pageNumber - 1) + 1;	
	}
	
	String linkPage = "";
	int adjacents = 1; // How many adjacent pages should be shown on each side

	//Draw page link
	if(numberOfPage > 1){
		linkPage = "<div class='pagination' id='Pagination'>";	
		//previous button
		if (pageNumber > 1){ 
			linkPage += "<a href='#' onclick='changePage("+ (pageNumber-1) + ",\"" + sortby + "\",\"" + sortdir + "\")'>戻る</a>";
		}else{
			linkPage += "<span class=\"disabled\">戻る</span>";
		}
		
		//pages	
		if (numberOfPage < 4 + (adjacents * 2))	//not enough pages to bother breaking it up
		{	
			for (int i = 1; i <= numberOfPage; i++)
			{
				if (i == pageNumber)
					linkPage += "<span class=\"current\">" + i + "</span>";
				else
					linkPage += "<a href='#' onclick='changePage(" + i + ",\"" + sortby + "\",\"" + sortdir + "\")'>" + i + "</a>";					
			}
		}
		else if(numberOfPage > 2 + (adjacents * 2))	//enough pages to hide some
		{
	//close to beginning; only hide later pages
	if(pageNumber < 1 + (adjacents * 2))		
	{
		for (int i = 1; i < 2 + (adjacents * 2); i++)
		{
			if (i == pageNumber)
				linkPage += "<span class=\"current\">" + i + "</span>";
			else
				linkPage += "<a href='#' onclick='changePage(" + i + ",\"" + sortby + "\",\"" + sortdir + "\")'>" + i + "</a>";						
		}
			linkPage += "<span class=\"elipses\">...</span>";
			//linkPage += "<a href='#' onclick='changePage(" + (numberOfPage-1) + ")'>" + (numberOfPage - 1) + "</a>";
			linkPage += "<a href='#' onclick='changePage(" + numberOfPage + ",\"" + sortby + "\",\"" + sortdir + "\")'>" + numberOfPage + "</a>";		
	}
	//in middle; hide some front and some back
	else if(numberOfPage - (adjacents * 2) > pageNumber && pageNumber > (adjacents * 2))
	{
  				linkPage += "<a href='#' onclick='changePage(1"+ ",\"" + sortby + "\",\"" + sortdir + "\")'>1</a>";
 				//linkPage += "<a href='#' onclick='changePage(2)'>2</a>";
 				linkPage += "<span class=\"elipses\">...</span>";
		for (int i = pageNumber - adjacents; i <= pageNumber + adjacents; i++)
		{
			if (i == pageNumber)
				linkPage += "<span class=\"current\">" + i + "</span>";
			else
				linkPage += "<a href='#' onclick='changePage("+ i + ",\"" + sortby + "\",\"" + sortdir + "\")'>" + i + "</a>";						
			}
				linkPage += "<span class=\"elipses\">...</span>";
			//linkPage += "<a href='#' onclick='changePage("+ (numberOfPage-1) + ")'>" + (numberOfPage - 1) + "</a>";
			linkPage += "<a href='#' onclick='changePage("+ numberOfPage + ",\"" + sortby + "\",\"" + sortdir + "\")'>" + numberOfPage + "</a>";		
		}
	//close to end; only hide early pages
	else
	{
 				linkPage += "<a href='#' onclick='changePage(1"+ ",\"" + sortby + "\",\"" + sortdir + "\")'>1</a>";
 				//linkPage += "<a href='#' onclick='changePage(2)'>2</a>";
 				linkPage += "<span class=\"elipses\">...</span>";
		for (int i = numberOfPage - (1 + (adjacents * 2)); i <= numberOfPage; i++)
		{
			if (i == pageNumber)
				linkPage += "<span class=\"current\">" + i + "</span>";
			else
				linkPage += "<a href='#' onclick='changePage("+ i + ",\"" + sortby + "\",\"" + sortdir + "\")'>" + i + "</a>";							
				}
			}
		}
		//next button
		if (pageNumber < numberOfPage) 
			linkPage += "<a href='#' onclick='changePage("+ (pageNumber+1) + ",\"" + sortby + "\",\"" + sortdir + "\")'>次へ</a>";
		else
			linkPage += "<span class=\"disabled\">次へ</span>";
			linkPage += "</div>\n";		
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="/asset/css/table.css" />
<link rel="stylesheet" type="text/css" href="/asset/css/jquery.fancybox.css" />
<link type="text/css" rel="stylesheet" href="/asset/css/styles.css" />
<script type="text/javascript" src="/asset/js/jquery-1.8.3.js"></script>
<script type="text/javascript" language="javascript" src="/asset/js/jquery.fancybox.js"></script>
<script type="text/javascript" language="javascript" src="/asset/js/jquery.fancybox.pack.js"></script>
<title>Recipe Management</title>
</head>
<body>
	<div class = "link-header-user">
	<% if (permission.equals(Util.ADMIN)){%>
		<a href="/user/manager/list">ユーザーマネージャ</a>
	<%}%>
	</div>
	<form action="/recipe/manager" method="get" id="pageForm">
		<input type="hidden" id="page" name="page" value="${page}" />
		<input type="hidden" id="sortby" name="sortby" value="${sortby}" />
		<input type="hidden" id="sortdir" name="sortdir" value="${sortdir}" />
		<div id="table-content">
			<table id="table-style" cellspacing="0" class="sortable">
				<thead>
					<tr>
						<th width="6%" nowrap="nowrap">管理番号</th>
						<th width="6%" nowrap="nowrap">
							<a href="/recipe/manager?sortby=history&sortdir=<%=sortdir.equals("desc") ? "asc" : "desc"%>">受賞暦</a></th>
						<th width="10%" nowrap="nowrap">写真・レシピ名</th>
						<th width="5%" nowrap="nowrap">レシピ名</th>
						<th width="7%" nowrap="nowrap">
							<a href="/recipe/manager?sortby=nickhome&sortdir=<%=sortdir.equals("desc") ? "asc" : "desc"%>">ニックネーム</a></th>
						<th width="5%" nowrap="nowrap">
							<a href="/recipe/manager?sortby=sortgenre&sortdir=<%=sortdir.equals("desc") ? "asc" : "desc"%>">何料理</a></th>
						<th width="5%" nowrap="nowrap">何人分</th>
						<th width="15%" nowrap="nowrap">材料</th>
						<th width="30%" nowrap="nowrap">作り方</th>
						<th width="6%" nowrap="nowrap">
							<a href="/recipe/manager?sortby=createdate&sortdir=<%=sortdir.equals("desc") ? "asc" : "desc"%>">投稿年月日</a></th>
						<th width="5%" nowrap="nowrap">
							<a href="/recipe/manager?sortby=numberoflike&sortdir=<%=sortdir.equals("desc") ? "asc" : "desc"%>">いいね数</a></th>
					</tr>
				</thead>
				<tbody>
					<%
						if(listRecipe ==null || listRecipe.size()<0){
					%>
					<tr>
						<td colspan="11">レシピなし！！</td>
					</tr>
					<%
						} else{
												for(Recipe recipe : listRecipe){
													String recipeID = KeyFactory.keyToString(recipe.getKey());
													int indexRecipe = recipe.getIndex();
													String history_of_award = recipe.getHistoryAward();
													String image_src = "/recipe/" + recipeID + "/image/1?apiKey=agtrdGNhbWNsb3VuZHILCxIEVXNlchjQAgw";
													String recipe_name = recipe.getTitle();
													String nick_home = recipe.getNickName();
													String name_of_cuisine = recipe.getRecipeGenre().getName();							
													String for_persons = Integer.toString(recipe.getPersons());
													List<IngredientItem> ingredientList = recipe.getIngredientItemListRef().getModelList();
													List<CookingWayStep> cookingWay = recipe.getCookingStepListRef().getModelList();								
													String registerDate = new SimpleDateFormat("yyyy/MM/dd").format(recipe.getCreatedDate());
													String number_of_like = Integer.toString(recipe.getLikes());
													String href="/recipe/popup/" + recipeID;
					%>
					<tr>
						<td style="text-align: center;"><%=indexRecipe%> <br>
						<%if(!permission.equals(Util.PERMISSION_A)){%>
							 <a class="fancybox fancybox.iframe" href="<%=href%>"> 
								<img class="button-detail1" src="/asset/css/images/button-fb-a.png" style="width: 20px; height: 15px; padding-top: 10px;">
							</a>
						<%}else{%>
						<a href="#" onClick="alert('許可されない!')"><img  class="button-detail1" src="/asset/css/images/button-fb-a.png" style="width: 20px; height: 15px; padding-top: 10px;"></a>
						<%}%>
						</td>
						<td><%=history_of_award == null ? "N/A" : history_of_award%></td>
						<td><img alt="recipe img" src="<%=image_src%>" width="150"></td>
						<td><%=recipe_name%></td>
						<td><%=nick_home%></td>
						<td><%=name_of_cuisine%></td>
						<td><%=for_persons%></td>
						<td>
							<ul>
								<%
									for(IngredientItem ingre : ingredientList){
								%>
								<li style="text-align: left; padding-left: 5px;"><%=ingre.getName()%>:<%=ingre.getQuantity()%></li>
								<%
									}
								%>
							</ul>
						</td>
						<td>
							<ol>
								<%
									for(CookingWayStep cooking : cookingWay){
								%>
								<li style="text-align: left; padding-left: 5px;"><%=cooking.getStepOrder()%>.
									<%=cooking.getExplanation()%></li>
								<%
									}
								%>
							</ol>
						</td>
						<td><%=registerDate%></td>
						<td><%=number_of_like%></td>
					</tr>
					<%
						}
																									}
					%>
				</tbody>

			</table>
			<div style="float: right;margin-right: 20px;"><%out.print(linkPage);%></div>
		</div>
	</form>
	<script type="text/javascript" language="javascript">
		function changePage(value1,sortby,sortdir) {
			$("#page").val(value1);
			$("#sortby").val(sortby);
			$("#sortdir").val(sortdir);
			$("#pageForm").submit();
		};

		$(document).ready(function() {
			$(".fancybox").fancybox({
				'width' : 570,
				'beforeClose': function() { parent.location.reload(true); },
        	    'closeClick': true
			});
			
		});
		
		
	</script>
</body>
</html>
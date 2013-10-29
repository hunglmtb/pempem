<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ivc.romando.appengine.util.Util"%>

<%
	String[] arpermissionA = (String[]) request.getAttribute(Util.PERMISSION_A);
	String[] arpermissionB = (String[]) request.getAttribute(Util.PERMISSION_B);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Manager</title>
<link type="text/css" rel="stylesheet" href="/asset/css/table.css" />
<script type="text/javascript">
			function addPermissionA() {
				var accountA = document.getElementById("addPermissionA").value;
				if(accountA != ""){
					window.document.location.href = '/user/manager/add/'+ accountA +'/1';
				}
			}
			function addPermissionB() {
				var accountB = document.getElementById("addPermissionB").value;
				if(accountB != ""){
					window.document.location.href =  '/user/manager/add/'+ accountB +'/2';
				}
			}
</script>
</head>
<body>
	<div class = "link-header-recipe">
		<a href="/recipe/manager" style="margin-right:10px;">レシピマネージャー</a>
	</div>
	<div class = "addPermissA">
		A権の許可を加える: <input type="text" id="addPermissionA"
			name="addPermissionA">
			<button onclick="addPermissionA()">加える</button>
			</div>
		<div>
		<table cellspacing="0" class="sortable">
			<thead>
				<tr>
					<th nowrap="nowrap" style="width:13%"><div style="text-align:center">#</div></th>
					<th nowrap="nowrap" style="width:20%">A権のアカウント</th>
					<th nowrap="nowrap" style="width:13%">アクション</th>
				</tr>
			</thead>
			<tbody id="contests_table1">
				<%
					if (arpermissionA != null && arpermissionA.length > 0) {
						for (int i = 0; i < arpermissionA.length ; i++) {
						%>
						<tr class="trtable2listitem">
							<td><div style="text-align:center"><%=i + 1%></div></td>
							<td><div style="text-align:center"><%=arpermissionA[i]%></div></td>
							<td><div style="text-align:center"><a href="/user/manager/delete/<%=arpermissionA[i]%>/<%=Util.PERMISSION_A_TYPE%>"> 削除する</a></div></td>
						</tr>
						<%
						}
					} else {
				%>
				<tr class="trtable2listitem">
					<td colspan="3"><div style="text-align: center">データなし!</div></td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>
	</div>
	<div class="addPermissB">
		B権の許可を加える : <input type="text" id="addPermissionB" name="addPermissionB">
		<button onclick="addPermissionB()">加える</button>
	</div>
	<div>
		<table cellspacing="0" class="sortable">
			<thead>
				<tr>
					<th class="trtable2" style="width:13%"><div style="text-align:center">#</div></th>
					<th class="trtable2" style="width:20%">B権のアカウント</th>
					<th class="trtable2" style="width:13%">アクション</th>
				</tr>
			</thead>
			<tbody id="contests_table1">
				<%
					if (arpermissionB != null && arpermissionB.length > 0) {
						for (int i = 0; i < arpermissionB.length ; i++) {
						%>
						<tr class="trtable2listitem">
							<td><div style="text-align:center"><%=i + 1%></div></td>
							<td><div style="text-align:center"><%=arpermissionB[i]%></div></td>
							<td><div style="text-align:center"><a href="/user/manager/delete/<%=arpermissionB[i]%>/<%=Util.PERMISSION_B_TYPE%>"> 削除する</a></div></td>
						</tr>
						<%
						}
					} else {
				%>
				<tr class="trtable2listitem">
					<td colspan="3"><div style="text-align: center">データなし!</div></td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>
	</div>
</body>
</html>
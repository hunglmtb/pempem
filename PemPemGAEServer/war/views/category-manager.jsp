<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Category Manager</title>
<script type="text/javascript" src="/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="/js/jquery.pagination.js"></script>
<script type="text/javascript" src="/js/listuser.js"></script>
<link rel="stylesheet" href="/css/styles.css" type="text/css">
</head>
<body onload='formLoad()'>
	<button class=buttoninsert onClick="displaymenu()">Back</button>
	<div class="module">
		<div class="module-table-body">
			<table id="myTable" class="module-table-body">
				<thead>
					<tr>
						<th style="width: 13%">ユーザーID</th>
						<th style="width: 14%">シリアル番号</th>
						<th style="width: 16%">日付</th>
						<th style="width: 15%">まとめて払いID</th>
					</tr>
				</thead>
				<tbody id="contests_table"></tbody>
			</table>
			<div class="bottom_content" id="bottom">
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
			</div>
			<table id="myTable2" class="module-table-body2">
				<thead>
					<tr>
						<th class="trtable2" style="width: 13%">Category Id</th>
						<th class="trtable2" style="width: 14%">Category Name</th>
						<th class="trtable2" style="width: 16%">Created Date</th>
						<th class="trtable2" style="width: 15%">まとめて払いID</th>
					</tr>
				</thead>
				<tbody id="contests_table1">
					<tr class="trtable2listitem">
						<td id="test1" nowrap onClick="addInput(this,1)"></td>
						<td id="test2" nowrap onClick="addInput(this,2)"></td>
						<td id="test3" nowrap onClick="addInput(this,3)"></td>
						<td id="test4" nowrap onClick="addInput(this,4)"></td>
						<td class=bntdelete><input type='button' name='add' value='add'
							class="buttonserch" onClick="callscreeninsert()" />
							<input type='button' name='search' value='search'
							class="buttonserch" onClick="callscreensearch()" />
							</td>
					</tr>
				</tbody>
			</table>
		</div>
		<!-- End .module-table-body -->
	</div>
	<!-- End .module -->
</body>
</html>
var params = {};
var parts = location.search.substring(1).split('&');
for ( var i = 0; i < parts.length; i++) {
	var nv = parts[i].split('=');
	nv[1] = nv[1].split("+").join(" ");
	params[nv[0]] = decodeURI(nv[1]) || "";
}
var members;
var numberFistPage;
/**
 * <p>Callback function that displays the content.
 * Gets called every time the user clicks on a pagination link.</p>
 *
 * @param {int}page_index New Page index
 * @param {jQuery} jq the container with the pagination links as a jQuery object
 */
function pageselectCallback(page_index, jq) {
	// Get number of elements per pagionation page from form
	var items_per_page = 30;
	var max_elem = Math.min((page_index + 1) * items_per_page, members.length);
	// Iterate through a selection of the content and build an HTML string
	var i = page_index * items_per_page;
	numberFistPage = i;
	createTable(i, max_elem);
	// Prevent click eventpropagation
	return false;
}

/**
 * <p>The form contains fields for many pagiantion optiosn so you can
 * quickly see the resuluts of the different options.
 * This function creates an option object for the pagination function.
 * This will be be unnecessary in your application where you just set
 * the options once.</p>
 */
function getOptionsFromForm() {
	var opt = {
		callback : pageselectCallback
	};
	// Collect options from the text fields - the fields are named like their option counterparts
	$("input:text")
			.each(
					function() {
						opt[this.name] = this.className.match(/numeric/) ? parseInt(this.value)
								: this.value;
					});
	// Avoid html injections in this demo
	var htmlspecialchars = {
		"&" : "&amp;",
		"<" : "&lt;",
		">" : "&gt;",
		'"' : "&quot;"
	};
	$.each(htmlspecialchars, function(k, v) {
		opt.prev_text = 'Prev';
		opt.next_text = 'Next';
	});
	return opt;
}

/**
 * <p>create row on table user search.</p>
 * @param data
 */
function createRow(data) {
	// Create pagination element with options from form
	members = data;
	var optInit = getOptionsFromForm();
	$("#Pagination").pagination(data.length, optInit);
	// Event Handler for for button
	$("#setoptions").click(function() {
		var opt = getOptionsFromForm();
		// Re-create pagination content with new parameters
		$("#Pagination").pagination(members.length, opt);
	});
}

/**
 * <p>submit form.</p>
 * @param i
 */
function submitform(i) {
	var tmp = i - numberFistPage;
	$("#contests_table").find("tr:eq(" + tmp + ")").hide();
}

/**
 * <p>create table search user.</p>
 * @param i
 * @param max_elem
 */
function createTable(i, max_elem) {
	$("#contests_table").empty();
	var strRow = "";
	var jsonTR;
	if(members !=null && members!= ""){
		for ( var rowIdx = i; rowIdx < max_elem; rowIdx++) {
			jsonTR = members[rowIdx];
			if (rowIdx % 2 == 0) {
				strRow = "<tr class=new>";
			} else {
				strRow = "<tr class=odd>";
			}
			strRow += "<td>" + jsonTR.macaddress + "</td><td>" + jsonTR.serial
					+ "</td><td>" + jsonTR.date + "</td><td>" + jsonTR.purchase
					+ "</td>";
			strRow += "<td class = bntdelete><input type= 'button' id ="
					+ members[rowIdx].macaddress
					+ " name='修正' value='修正' class= 'buttonedit' onclick= 'reply_click("
					+ rowIdx + ")'/></td>";
			strRow += "</tr>";
			// Create new JQUERY ROW
			var newRow = $(strRow);
			// Get DOM Table and append new row.
			$("#contests_table").append(newRow);
		}
	}else{
		strRow = "<tr class=new ></tr>";
		strRow += "<td class = bntdelete>" + "" + "</td><td class = bntdelete>" + ""
		+ "</td><td class = bntdelete>" + "" + "</td><td class = bntdelete>" + ""
		+ "</td>";
		strRow += "<td class = bntdelete><input style='display:none' type= 'button' name='修正' value='修正' class= 'buttonedit' /></td>";
		strRow += "</tr>";
		// Create new JQUERY ROW
		var newRow = $(strRow);
		// Get DOM Table and append new row.
		$("#contests_table").append(newRow);
	}
	
}

/**
 * <p>function load data display list user.</p>
 */
function formLoad() {
	$.get('search?macaddress=' + params.macaddress + '&serial=' + params.serial
			+ '&date=' + params.date + '&purchase=' + params.purchase + '',
			function(data) {
				if(data!=null && data!=""){
					createRow(data);
				}else{
					createTable(0,30);	
				}
			});
}

/**
 * <p>call page edit user.</p>
 * @param rowIdx
 */
function reply_click(rowIdx) {
	window.document.location.href = "/BundleBoxService/webapps/myapp/html/usermanager/edituser.html?macaddress="
			+ members[rowIdx].macaddress
			+ "&serial="
			+ members[rowIdx].serial
			+ "&date="
			+ members[rowIdx].date
			+ "&purchase="
			+ members[rowIdx].purchase;
}
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
 * <p>create row on table user manager.</p>
 * @param data
 */
function createRow(data) {
	if (data.respondCode=="SUCCESS") {
		// Create pagination element with options from form
		members = data.respondData;
		var optInit = getOptionsFromForm();
		$("#Pagination").pagination(data.length, optInit);
		// Event Handler for for button
		$("#setoptions").click(function() {
			var opt = getOptionsFromForm();
			// Re-create pagination content with new parameters
			$("#Pagination").pagination(members.length, opt);
		});
	}
	else{
		alert("error: "+data.respondMessage);
	}
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
 * <p>create table user.</p>
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
			strRow += "<td>" + jsonTR.categoryId + "</td><td>" + jsonTR.categoryName
			+ "</td><td>" + jsonTR.date + "</td><td>" + jsonTR.purchase
			+ "</td>";
			
			strRow += "<td class = bntdelete><input type= 'button' id ="
				+ 'edit'+ members[rowIdx].categoryKeyString
				+ " name='edit' value='edit' class= 'buttonedit' onclick= 'reply_click(this,"
				+ members[rowIdx].categoryKeyString + ")'/>"
				
				+"<input type= 'button' id ="
				+ members[rowIdx].categoryKeyString
				+ " name='delete' value='delete' class= 'buttonedit' onclick= 'delete_click("
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
		strRow += "<td class = bntdelete><input style='display:none' type= 'button' name='edit' value='edit' class= 'buttonedit' /></td>";
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
	var url = '/category/admin/get';
	
	requestUrl(url);
}
/**
 * <p>close tab input text.</p>
 * @param elm
 */
function closeInput(elm) {
	var td = elm.parentNode;
	var value = elm.value;
	td.removeChild(elm);
	td.innerHTML = value;
}
/**
 * <p>call page edit user.</p>
 * @param rowIdx
 */
function reply_click(element,id) {
	if (element.value=='edit') {
		
		element.value="submit";
	}
	else{
		element.value="edit";
	}
}
/**
 * <p>call page edit user.</p>
 * @param rowIdx
 */
function delete_click(rowIdx) {
	
	var url ="/category/delete?categorykeystring=" + members[rowIdx].categoryKeyString +'';
	
	requestUrl(url);
}

/**
 * <p>add input text in row table.</p>
 * @param elm
 */
function addInput(elm,index) {
	if (elm.getElementsByTagName('input').length > 0)
		return;
	var value = elm.innerHTML;
	elm.innerHTML = '';
	var input = document.createElement('input');
	input.setAttribute('type', 'text');
	if(index == 2 || index == 4){
		input.setAttribute('maxlength', 30);
	}else{
		input.setAttribute('maxlength', 20);
	}
	input.setAttribute('value', value);
	input.setAttribute('onBlur', 'closeInput(this)');
	elm.appendChild(input);
	input.focus();
}

/**
 * <p>call screen insert data.</p>
 */

function callscreeninsert() {
	var categoryid = test1.innerHTML;
	var categoryname = test2.innerHTML;
	var url = '/category/add?categoryid=' + categoryid + '&categoryname=' + categoryname + '';

	requestUrl(url);
}
function showDataTable(data) {
	if(data!=null && data!=""){
		createRow(data);
	}else{
		createTable(0,30);	
	}
}
/**
 * <p>call screen insert data.</p>
 */

function callscreensearch() {
	var categoryid = test1.innerHTML;
	var categoryname = test2.innerHTML;
	var url = '/category/search?categoryid=' + categoryid + '&categoryname=' + categoryname + '';
	
	requestUrl(url);
}

function requestUrl(url) {
	url=encodeURI(url);
	$.get(url,function(data) {
		showDataTable(data);
	});
}
function displaymenu() {
	window.document.location.href = "/BundleBoxService";
}
/**
 * <p>call screen search result.</p>
 */
function callscreensearch() {
	var macaddress = test1.innerHTML;
	var serial = test2.innerHTML;
	var date =test3.innerHTML;
	var purchase = test4.innerHTML;
	var url ="/BundleBoxService/webapps/myapp/html/usermanager/searchuser.html?macaddress="
		+ macaddress
		+ "&serial="
		+ serial
		+ "&date="
		+ date
		+ "&purchase="
		+ purchase;
	url=encodeURI(url);
	window.document.location.href = url;
}
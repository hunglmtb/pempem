var members;
var numberFistPage;
var categories;
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
			

			var url = '/media?key=';
			url=encodeURI(url);
			
			strRow += "<td>" + jsonTR.title + "</td><td>" + jsonTR.contentInfo
			+ "</td><td>" + jsonTR.speaker + "</td><td>" + jsonTR.author
			+ "</td><td>" + getCategoryNameById(jsonTR.categoryId)
			+ "</td><td><a href=\""+jsonTR.mediaImageUrl+"\">image</a>"
			+ "</td><td><audio controls> <source src=\"/media/play?key="+jsonTR.mediaFileUrl+"\" type=\"audio/mpeg\"> play media. </audio>"
			+ "</td><td><a href=\""+jsonTR.mediaLinkUrl+"\">shared url</a></td>"
			+ "</td><td>"+jsonTR.viewCount
			+ "</td><td>"+jsonTR.duration
			+ "</td><td>"+jsonTR.publishedDate
			+ "</td><td>"+jsonTR.modifiedDate+"</td>";

			strRow += "<td class = bntdelete><input type= 'button' id ="
				+ members[rowIdx].macaddress
				+ " name='edit' value='edit' class= 'buttonedit' onclick= 'reply_click("
				+ rowIdx + ")'/>"

				+"<input type= 'button' id ="
				+ members[rowIdx].mediaKeyString
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
	url=encodeURI(url);
	
	$.get(url,function(data) {
		if(data!=null && data!=""){
			if (data.respondCode=="SUCCESS") {
				categories = data.respondData;
				var url2 = '/media/admin/all?limit='+10+"&offset="+0;
				requestUrl(url2);
				
				initDropDown('#categorydropdown');
			}
			else{
				alert("error: "+data.respondMessage);
			}
		}
		else{
			alert("error: none respond ");
		}
	});
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
function reply_click(rowIdx) {
	var url ="/BundleBoxService/webapps/myapp/html/usermanager/edituser.html?macaddress="
		+ members[rowIdx].macaddress
		+ "&serial="
		+ members[rowIdx].serial
		+ "&date="
		+ members[rowIdx].date
		+ "&purchase="
		+ members[rowIdx].purchase;
	url=encodeURI(url);
	window.document.location.href = url;
}
/**
 * <p>call page edit user.</p>
 * @param rowIdx
 */
function delete_click(rowIdx) {

	var url ="/media/delete?mediakeystring=" + members[rowIdx].mediaId +'';

	requestUrl(url);
}

function getCategoryNameById(categoryId) {
	if (categoryId!=null&&categoryId!='') {
		var category;
		for ( var i = 0; i < categories.length; i++) {
			category = categories[i];
			if (category.categoryId==categoryId) {
				return category.categoryName;
			}
		}
	}
}


/**
 * <p>add input text in row table.</p>
 * @param elm
 */
function addInput(td,index) {
	elm=(td.firstElementChild||elem.firstChild);
//	elm = td.getElementsByTagName('span');
	/*if (elm.length > 0)
		return;*/
	
	var value = elm.innerHTML;
	elm.innerHTML = '';
	var input = document.createElement('input');
	input.setAttribute('type', 'text');
	switch (index) {
	case 2:
		input.setAttribute('maxlength', 600);
		break;
	case 6:
	case 7:
		input.setAttribute('maxlength', 500);
		break;	
	default:
		input.setAttribute('maxlength', 100);
		break;
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
	var title = getValue(test1);
	var contentInfo = getValue(test2);
	var speaker = getValue(test3);
	var author = getValue(test4);
	
	//get category
	var e = document.getElementById("categorydropdown");
	var categoryId = e.options[e.selectedIndex].value;
	//image
	var imageUrl = getValue(test6);
	var imageThumbUrl = getValue(test6);
	var mediaFileUrl = getValue(test7);

	var url = '/media/add';
	var postData = initPostData('0',title,contentInfo,speaker,author,categoryId,imageUrl,imageThumbUrl,mediaFileUrl);

	postUrl(url,postData);
}

/**
 * <p>call screen insert data.</p>
 */

function getValue(element) {
	var child = (element.firstElementChild||element.firstChild);
	return child.innerHTML;
}

function initPostData(mediaKeyString,title,contentInfo,speaker,author,categoryId,mediaImageUrl,mediaImageThumbUrl,mediaFileUrl) {

	var postData = 	'{mediaKeyString:'+mediaKeyString+
	', title: '+title+
	', contentInfo: '+contentInfo+
	', speaker: '+speaker+
	', author: '+author+
	', categoryId: '+categoryId+
	', mediaImageUrl: "'+mediaImageUrl+
	'", mediaImageThumbUrl: "'+mediaImageThumbUrl+
	'", mediaFileUrl: "'+mediaFileUrl+
	'"}';

	return postData;
}

function postUrl(url,postData) {
	url=encodeURI(url);

	$.ajax({
		url: url,
		type: "POST",
		data: postData,
		dataType: "json",
		success: function (result) {
			showDataTable(result);
		},
		error: function (xhr, ajaxOptions, thrownError) {
			alert(xhr.status);
			alert(thrownError);
		}
	});
}



function showDataTable(data) {
	if(data!=null && data!=""){
		createRow(data);
	}else{
		createTable(0,30);	
	}
}

function initDropDown(selector) {
	var category;
	for ( var i = 0; i < categories.length; i++) {
		category = categories[i];
		$(selector).append('<option value="'+category.categoryId+'">'+category.categoryName+'</option>');
	}
}

/**
 * <p>call screen insert data.</p>
 */

function callscreensearch() {
	var mediaid = test1.innerHTML;
	var medianame = test2.innerHTML;
	var url = '/media/search?mediaid=' + mediaid + '&medianame=' + medianame + '';

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
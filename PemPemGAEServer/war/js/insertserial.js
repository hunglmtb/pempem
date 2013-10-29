/**
 * <p>display popup.</p> 
 * @param status
 */
function showpopup(status) {
	if (status == 'insert') {
		jConfirm('are you want to insert data', '', function(r) {
			if(r ==true){
				insertdata();
			}
		});
	}
}

/**
 * <p>show message success or error when import file csv.</p>
 */
function showMessage(){
	var query = window.location.search;
	if(query != null && query != "") {
		if(query.substring(0, 1) == '?') {
			query = query.substring(1);
		}
		var data = query.split(',');
		for(var i = 0; (i < data.length); i++) {
			data[i] = unescape(data[i]);		
			if(data[i] == "error")	{
			jAlert('insert serial csv file error!', '');
			}else{
			jAlert('insert serial csv file successful!', '');
			}
		}
	}
}

/**
 * <p>set event onclick display calendar.</p>
 * @param button_name
 */
function ipress(button_name) {
	document.getElementById(button_name).click();
}
function createSerial() {
	var status="0";
	$.get('insertserial?status=' + status +'', function(data) {
		document.getElementById("test1").innerHTML = data.response;
	});
}

/**
 * <p>send data to servlet.</p>
 */
function insertdata() {
	var status="";
	var serial = test1.innerHTML;
	var date = test2.innerHTML;
	var purchase = test3.innerHTML;
	$.get('insertserial?status=' + status + '&serial=' + serial + '&date=' + date + '&purchase='
			+ purchase + '', function(data) {
		if (data.response != 1) {
			document.getElementById("test1").innerHTML = data.response;
			document.getElementById("test2").innerHTML = "";
			document.getElementById("test3").innerHTML = "";
			jAlert('insert successful!', '');
		} else {
			jAlert('insert error!', '');
		}
	});
}
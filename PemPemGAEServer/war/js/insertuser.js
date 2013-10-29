
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
		for( var i = 0; (i < data.length); i++) {
			data[i] = unescape(data[i]);		
			if(data[i] == "error")	{
			jAlert('insert user csv file data error!', '');
			}else{
			jAlert('insert user csv file successful!', '');
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
/**
 * <p>send data to servlet.</p>
 */
function insertdata() {
	var status="";
	var macaddress = test1.innerHTML;
	var serial = test2.innerHTML;
	var date = test3.innerHTML;
	var purchase = test4.innerHTML;
	$.get('insertdata?status=' + status + '&macaddress=' + macaddress
			+ '&serial=' + serial + '&date=' + date + '&purchase='
			+ purchase + '', function(data) {
		if (data.response == 0) {
			jAlert('insert successful!', '');
		} else if (data.response == 1) {
			jAlert('insert error!', '');
		}
	});
}


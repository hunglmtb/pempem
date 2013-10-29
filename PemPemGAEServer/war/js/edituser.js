var params = {};
var parts = location.search.substring(1).split('&');
for ( var i = 0; i < parts.length; i++) {
	var nv = parts[i].split('=');
	nv[1] = nv[1].split("+").join(" ");
	params[nv[0]] = decodeURI(nv[1]) || "";
}

/**
 * <p>load data display table edit</p>
 */
function formLoad() {
	document.getElementById("test1").innerHTML = params.macaddress;
	document.getElementById("test2").innerHTML = params.serial;
	document.getElementById("test3").innerHTML = params.date;
	document.getElementById("test4").innerHTML = params.purchase;
}

/**
 * <p>display popup.</p> 
 * @param status
 */
function showpopup(status) {
	//display popup update data.
	if (status == 'update') {
		jConfirm('are you want to update data', '', function(r) {
			if(r ==true){
				sendData(status);
			}
		});
	}else{
	//display popup delete data.
		jConfirm('are you want to deleted data', '', function(r) {
			if(r ==true){
				sendData(status);
			}
		});
	}
}

function ipress(button_name) {
	document.getElementById(button_name).click();
}
/**
 * <p>send data to servlet.</p>
 * @param status
 */
function sendData(status) {
	var macaddress = test1.innerHTML;
	var serial = test2.innerHTML;
	var date = test3.innerHTML;
	var purchase = test4.innerHTML;
	//update data.
	if (status == "update") {
		$.get('edituser?status=' + status + '&macaddress=' + macaddress
				+ '&serial=' + serial + '&date=' + date + '&purchase='
				+ purchase + '&oldmac='+ params.macaddress+'', function(data) {
			if (data.response == 0) {
				jAlert('update successful!', '');
			} else if(data.response == 1){
				jAlert('update error!', '');
			}
		});
	} else {
	//delete data.
		$.get('edituser?status=' + status + '&macaddress=' + params.macaddress + '', function(data) {
			if (data.response == 0) {
				jAlert('deleted successful!', '');
			} else if (data.response == 1) {
				jAlert('deleted error!', '');
			}
		});
	}
}
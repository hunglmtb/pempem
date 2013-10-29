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
	document.getElementById("test1").innerHTML = params.serial;
	document.getElementById("test2").innerHTML = params.date;
	document.getElementById("test3").innerHTML = params.purchase;
}

function ipress(button_name) {
	document.getElementById(button_name).click();
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

/**
 * <p>send data to servlet.</p>
 * @param status
 */
function sendData(status) {
	var serial = test1.innerHTML;
	var date = test2.innerHTML;
	var purchase = test3.innerHTML;
	//update data.
	if (status == "update") {
		$.get('editserial?status=' + status + '&serial=' + serial + '&date=' + date + '&purchase='
				+ purchase + '&oldserial='+ params.serial + '&oldpurchase='+ params.purchase + '', function(data) {
			if (data.response == 0) {
				jAlert('update successful!', '');
			} else if(data.response == 1){
				jAlert('update error!', '');
			}
		});
	} else {
	//delete data.
		$.get('editserial?status=' + status + '&serial=' + params.serial + '&purchase=' + params.purchase + '', function(data) {
			if (data.response == 0) {
				jAlert('deleted successful!', '');
			} else if (data.response == 1) {
				jAlert('deleted error!', '');
			}
		});
	}
}
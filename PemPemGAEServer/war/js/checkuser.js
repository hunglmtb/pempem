
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
 * <p>add input text in row table.</p>
 * @param elm
 */
function addInputuser(elm,index) {
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
function addInputserial(elm) {
	if (elm.getElementsByTagName('input').length > 0)
		return;
	var value = elm.innerHTML;
	elm.innerHTML = '';
	var input = document.createElement('input');
	input.setAttribute('type', 'text');
	input.setAttribute('maxlength', 30);
	input.setAttribute('value', value);
	input.setAttribute('onBlur', 'closeInput(this)');
	elm.appendChild(input);
	input.focus();
}
function displaylistuser() {
	window.document.location.href = "/BundleBoxService/webapps/myapp/html/usermanager/listuser.html";
}
function displaylistserial() {
	window.document.location.href = "/BundleBoxService/webapps/myapp/html/serialmanager/listserial.html";
}
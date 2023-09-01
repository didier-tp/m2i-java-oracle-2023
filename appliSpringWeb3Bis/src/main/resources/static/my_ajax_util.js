//subfunction with errCallback as optional callback :
function registerCallbacks(xhr, callback, errCallback) {
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4) {
			if ((xhr.status == 200 || xhr.status == 0)) {
				callback(xhr.responseText);
			}
			else {
				if (errCallback)
					errCallback(xhr.responseText);
			}
		}
	};
}

function setTokenInRequestHeader(xhr){
	var token = sessionStorage.getItem("token");
	xhr.setRequestHeader("Authorization", "Bearer " + token);
}

function makeAjaxGetRequest(url, callback, errCallback) {
	var xhr = new XMLHttpRequest();
	registerCallbacks(xhr, callback, errCallback);
	xhr.open("GET", url, true); 
	setTokenInRequestHeader(xhr);
	xhr.send(null);
}
function makeAjaxDeleteRequest(url, callback, errCallback) {
	var xhr = new XMLHttpRequest();
	registerCallbacks(xhr, callback, errCallback);
	xhr.open("DELETE", url, true); 
	setTokenInRequestHeader(xhr);
	xhr.send(null);
}
function makeAjaxPostRequest(url, jsonData, callback, errCallback) {
	var xhr = new XMLHttpRequest();
	registerCallbacks(xhr, callback, errCallback);
	xhr.open("POST", url, true);
	xhr.setRequestHeader("Content-Type", "application/json");
	setTokenInRequestHeader(xhr);
	xhr.send(jsonData);
}
function makeAjaxPutRequest(url, jsonData, callback, errCallback) {
	var xhr = new XMLHttpRequest();
	registerCallbacks(xhr, callback, errCallback);
	xhr.open("PUT", url, true);
	xhr.setRequestHeader("Content-Type", "application/json");
	setTokenInRequestHeader(xhr);
	xhr.send(jsonData);
}
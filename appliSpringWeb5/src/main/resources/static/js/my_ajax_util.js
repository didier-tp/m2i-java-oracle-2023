//subfunction with errCallback as optional callback :
function registerCallbacks(xhr, callback, errCallback) {
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4) {
			if ((xhr.status == 200 || xhr.status == 204 ||  xhr.status == 201)) {
				callback(xhr.responseText);
			}
			else {
				if (errCallback)
					errCallback(withDefaultErrorMessage(xhr,xhr.responseText));
			}
		}
	};
}

function withDefaultErrorMessage(xhr,errorMessage){
	if(errorMessage==null || errorMessage==""){
		switch(xhr.status){
			case 401:
				return "401/Unauthorized (no authentication (ex: no token))";
			case 403:
				return "403/Forbidden (not enough access)";
			case 500:
				return "500/Internal Server Error";
		}
	}
	else return errorMessage;
}

function setTokenInRequestHeader(xhr){
	let authToken = sessionStorage.getItem("authToken");
	if(authToken!=null && authToken!="")
	   xhr.setRequestHeader("Authorization", "Bearer " + authToken);
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
window.onload=function(){
	document.getElementById("selectUrl").addEventListener("change", function(evt){
		document.getElementById("wsUrlTxt").value = document.getElementById("selectUrl").value
	})
	let authToken = sessionStorage.getItem("authToken"); 
	document.getElementById("divAuthToken").innerHTML="<i>parsed jwt authToken=" + parseJwt(authToken) + "</i>";
}

function parseJwt (token) {
	//console.log("token="+token)
	if(token==null || token == "" || token == "null") return "";
    let base64Url = token.split('.')[1];
    let base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    let jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
    return jsonPayload;
    //return JSON.parse(jsonPayload);
};

function doBasicGet(){

	var wsUrl = document.getElementById("wsUrlTxt").value;


    var callback = function(data){
	   console.log("success data=" + data);
       document.getElementById("spanMsg").innerHTML="<b>"+data+"</b>";
    }

    var errCallback = function(data){
	   console.log("erreur=" + data);
       document.getElementById("spanMsg").innerHTML="<i>"+data+"</i>";
    }

  
	makeAjaxGetRequest(wsUrl,callback,errCallback) ;
	
}
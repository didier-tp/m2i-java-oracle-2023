window.onload=function (){
	displayCurrentToken();
}

function parseJwt (token) {
	if(token==null || token =="" || token == "null") return "";
    let base64Url = token.split('.')[1];
    let base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    let jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
    return jsonPayload;
    //return JSON.parse(jsonPayload);
};

function displayCurrentToken(){
	let jwtToken =  sessionStorage.getItem("authToken");
	var message = parseJwt(jwtToken);
    document.getElementById("spanMessageToken").innerHTML="<b>"+message+"</b>";
    console.log(message);
}

function doLogin(){

	var username = document.getElementById("txtUsername").value;
	var password = document.getElementById("txtPassword").value;

	
	var url = "./rest/api-login/public/login"

    var callback = function(data){
	   console.log("success data=" + data);
       var jwtToken = (JSON.parse(data)).token;
       //tokenGlobal=jwtToken;
       sessionStorage.setItem("authToken",jwtToken);
       var message ="reponse login=" + data ;
       document.getElementById("spanMessageLogin").innerHTML="<b>"+message+"</b>";
       displayCurrentToken();
    }

    var errCallback = function(data){
	   console.log("erreur=" + data);
       var message = (JSON.parse(data)).message;
       sessionStorage.setItem("authToken",null);
       document.getElementById("spanMessageLogin").innerHTML="<b>"+message+"</b>";
    }

    var jsLoginRequestObject = {username , password};
    var jsonData = JSON.stringify(jsLoginRequestObject);

	makeAjaxPostRequest(url,jsonData,callback,errCallback) ;
	
}

function doLogout(){
	 sessionStorage.setItem("authToken",null);
	 document.getElementById("spanMessageLogin").innerHTML="<b>logout</b>";
	 displayCurrentToken();
}
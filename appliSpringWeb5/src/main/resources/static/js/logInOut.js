var keycloak = new Keycloak({
	url: 'https://www.d-defrance.fr/keycloak',
	realm: 'sandboxrealm',
	clientId: 'anywebappclient' 
});

function authCallback(authenticated){
	document.getElementById("spanMsg").innerText=authenticated ? 'authenticated' : 'not authenticated';
	console.log("authToken="+ keycloak.token);
	let tokenParsed = keycloak.tokenParsed;
	console.log("tokenParsed="+JSON.stringify(tokenParsed));
	document.getElementById("username").innerText= tokenParsed.preferred_username;
	document.getElementById("name").innerText= tokenParsed.name;
	document.getElementById("scope").innerText= tokenParsed.scope;
	sessionStorage.setItem("authToken",keycloak.token);
}

function authErrCallback(err) {
	document.getElementById("spanMsg").innerText="not authenticated or oauth2/oidc error";
	console.log('err='+JSON.stringify(err));
	sessionStorage.removeItem("authToken");
}

function initKeycloak(){
    keycloak.init({
		//redirectUri: 'http://localhost:8233/html/logInOut.html', 
		checkLoginIframe: false,
		onLoad: 'check-sso'
   }).then(authCallback).catch(authErrCallback);
}


function onLogin(evt){
	document.getElementById("spanMsg").innerText="onLogin";

	keycloak.login({
			//redirectUri: 'http://localhost:8233/html/logInOut.html', 
			checkLoginIframe: false,
			onLoad: 'login-required',
	}).then(authCallback).catch(authErrCallback);
}

function onLogout(evt){
	//document.getElementById("spanMsg").innerText="onLogout";
	keycloak.logout();
}


window.onload=function(){
	initKeycloak();
	document.getElementById("btnLogin").addEventListener("click",onLogin);
	document.getElementById("btnLogout").addEventListener("click" ,onLogout);
}
window.onload=function(){
	(document.getElementById("btnRecherche"))
	   .addEventListener("click",rechercherDeviseSelonCode);
	     
}

	
function rechercherDeviseSelonCode(){	
	let code = (document.getElementById("inputCode")).value;
	
	let wsUrl = "./api-devise/devise/"+code;
	
	let successCallback = function(responseJson){
		let deviseJs = JSON.parse(responseJson);
		console.log("deviseJs="+deviseJs);
		
		let spanMsgElt = document.getElementById("spanMsg");
		spanMsgElt.innerHTML=responseJson;//...
		
	}
	
	let errCallback = function (erreur){
		console.log("erreur="+erreur);
		let spanMsgElt = document.getElementById("spanMsg");
		spanMsgElt.innerHTML=erreur;//...
	}
	
	makeAjaxGetRequest(wsUrl,successCallback,errCallback);
	
}
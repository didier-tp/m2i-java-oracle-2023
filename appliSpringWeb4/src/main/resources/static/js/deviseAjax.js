var selectedRow = null;
var selectedDevise = null;

window.onload=function(){
	
	(document.getElementById("btnRefreshAll"))
	   .addEventListener("click",refreshAllDevises);
	   
	(document.getElementById("btnAdd"))
	   .addEventListener("click",addDevise); 
	   
	(document.getElementById("btnUpdate"))
	   .addEventListener("click",updateDevise); 
	   
	(document.getElementById("btnReset"))
	   .addEventListener("click",resetDevise); 
	   
	(document.getElementById("btnDelete"))
	   .addEventListener("click",deleteDevise);  
}


function resetDevise(){
	if(selectedRow!=null){
		selectedRow.style.color="black";
		selectedRow=null;
		selectedNews = null;
	}
	displayDeviseJs({code:"" , nom: "" , change : ""});	
}

function updateDevise(){
	let deviseJs = deviseJsFromInput();
	if(deviseJs.code==null || deviseJs.code == "") return;
	if(deviseJs.change==null || deviseJs.change == "") return;
	if(deviseJs.nom==null || deviseJs.nom == "") return;
	let deviseJson = JSON.stringify(deviseJs) ;  
	let wsUrl = "./rest/api-devise/devise";   
	makeAjaxPutRequest(wsUrl,deviseJson,function (responseJson){
		console.log("responseJson="+responseJson);
		refreshAllDevises();
	}); 
}

function deleteDevise(){
	let id = (document.getElementById("inputCode")).value;
	if(id!=null && id!=""){
		let wsUrl = "./rest/api-devise/devise/"+id;
		makeAjaxDeleteRequest(wsUrl,function(responseJson){
			refreshAllDevises();
			});
	}
}

function addDevise(){	
	let deviseJs = deviseJsFromInput();
	if(deviseJs.code==null || deviseJs.code == "") return;
	if(deviseJs.change==null || deviseJs.change == "") return;
	if(deviseJs.nom==null || deviseJs.nom == "") return;
	let deviseJson = JSON.stringify(deviseJs) ;  
	let wsUrl = "./rest/api-devise/devise";   
	makeAjaxPostRequest(wsUrl,deviseJson,function (responseJson){
		console.log("responseJson="+responseJson);
		refreshAllDevises(); //pour rafraîchir le tableau
	});         
}

function deviseJsFromInput(){
	let code = (document.getElementById("inputCode")).value;
	let nom = (document.getElementById("inputNom")).value;
	let change = (document.getElementById("inputChange")).value;
	document.getElementById("spanMsg").innerHTML="";
	let deviseJs = { code : code,
				   nom : nom,
	               change : Number(change) };
	return deviseJs;
}

function displayDeviseJs(deviseJs){
	(document.getElementById("inputCode")).value=deviseJs.code;
	(document.getElementById("inputNom")).value=deviseJs.nom;
	(document.getElementById("inputChange")).value=deviseJs.change;
}

function onSelectRow(evt){
	let clickElt = evt.target;
	if(selectedRow!=null){
		selectedRow.style.color="black";
	}
	let parentRow = clickElt.parentElement;
	selectedRow = parentRow;
	parentRow.style.color="blue";
	let idSelectedDevise=parentRow.firstChild.innerText;
	console.log("idSelectedDevise="+idSelectedDevise);
	rechercherDevise(idSelectedDevise);
}

function rechercherDevise(id){	
	let wsUrl = "./rest/api-devise/devise/"+id;
	
	makeAjaxGetRequest(wsUrl,function(responseJson){
		let deviseJs = JSON.parse(responseJson);
		selectedDevise=deviseJs;
		document.getElementById("spanMsg").innerHTML="selectedDeviseId="+id;
		console.log("deviseJs="+JSON.stringify(deviseJs));
		displayDeviseJs(deviseJs);
		});
}
	
function refreshAllDevises(){	
	let wsUrl = "./rest/api-devise/devise";
	selectedRow = null;
	makeAjaxGetRequest(wsUrl,function(responseJson){
		let deviseJs = JSON.parse(responseJson);
		console.log("deviseJs="+deviseJs);
		
		let bodyElt = document.getElementById("table_body");
		bodyElt.innerHTML="";//vider le tableau avant de le re-remplir
		for(let d of deviseJs){
			let row = bodyElt.insertRow(-1);
			(row.insertCell(0)).innerHTML = d.code;
			(row.insertCell(1)).innerHTML = d.nom;
			(row.insertCell(2)).innerHTML = d.change;
			row.addEventListener("click" , onSelectRow);
		}
		
		resetDevise();
	});
	
}
//************** SPECIF CRUD ********

window.onload=function(){
	//(document.getElementById("inputId")).disabled=true; //if auto_incr
	initListeners(); 
}

function objectFromInput(){
	let code = (document.getElementById("inputId")).value;
	let nom = (document.getElementById("inputNom")).value;
	let change = (document.getElementById("inputChange")).value;
	
	document.getElementById("spanMsg").innerHTML="";
	
	let obj = { code : code,
				   nom : nom,
	               change : Number(change) }
	return obj;
}

function displayObject(obj){
	(document.getElementById("inputId")).value=obj.code;
	(document.getElementById("inputNom")).value=obj.nom;
	(document.getElementById("inputChange")).value=obj.change;
}

function insertRowCells(row,obj){
	(row.insertCell(0)).innerHTML = obj.code;
	(row.insertCell(1)).innerHTML = obj.nom;
	(row.insertCell(2)).innerHTML = obj.change;
}


function blankObject(){
	return {code:"" , nom: "" , change : ""};	
}

function getWsBaseUrl(){
	return "./rest/api-devise/devise";	
}

//obj = object with values to check
//action = "add" or "update" or ...
function canDoAction(action,obj){
	ok=true; //by default
	if(obj.code==null || obj.code == "")
	  ok=false;
	if(obj.change==null || obj.change == "") 
	   ok=false;
	if(obj.nom==null || obj.nom == "") 
	   ok=false;
	return ok;
}



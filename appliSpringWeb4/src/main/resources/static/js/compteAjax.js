//************** SPECIF CRUD ********

window.onload=function(){
	(document.getElementById("inputId")).disabled=true; //if auto_incr
	initListeners(); 
}

function objectFromInput(){
	let numero = (document.getElementById("inputId")).value;
	if(numero=="")numero=null;
	
	let label = (document.getElementById("inputLabel")).value;
	let soldeInitial = (document.getElementById("inputSoldeInitial")).value;
	
	document.getElementById("spanMsg").innerHTML="";
	let obj = { numero : numero,
				label : label,
	            solde : parseFloat(soldeInitial)
	            };
	return obj;
}

function displayObject(obj){
	(document.getElementById("inputId")).value=obj.numero;
	(document.getElementById("inputLabel")).value=obj.label;
	(document.getElementById("inputSoldeInitial")).value=obj.solde;
}

function insertRowCells(row,obj){
	(row.insertCell(0)).innerHTML = obj.numero;
	(row.insertCell(1)).innerHTML = obj.label;
	(row.insertCell(2)).innerHTML = obj.solde;
}


function blankObject(){
	return {numero:"" , label: "" , solde : 0 };	
}

function getWsBaseUrl(){
	return "./rest/api-bank/compte";	
}

//obj = object with values to check
//action = "add" or "update" or ...
function canDoAction(action,obj){
	ok=true; //by default
	if(obj.label==null || obj.label == "")
	  ok=false;
	if(obj.solde==null)
	  ok=false;
	return ok;
}

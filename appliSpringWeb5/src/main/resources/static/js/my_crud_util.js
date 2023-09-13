//************** GENERIC CRUD ********

var selectedRow = null;
var selectedObject = null;

function initListeners(){
	
	(document.getElementById("btnRefreshAll"))
	   .addEventListener("click",refreshAll);
	   
	(document.getElementById("btnAdd"))
	   .addEventListener("click",addNew); 
	   
	(document.getElementById("btnUpdate"))
	   .addEventListener("click",updateSelected); 
	   
	(document.getElementById("btnReset"))
	   .addEventListener("click",resetObject); 
	   
	(document.getElementById("btnDelete"))
	   .addEventListener("click",deleteSelected); 
	    
}

function resetObject(){
	document.getElementById("spanMsg").innerHTML="";
	document.getElementById("spanMsg").style.color="black";
	if(selectedRow!=null){
		selectedRow.style.color="black";
		selectedRow=null;
		selectedObject = null;
	}
	displayObject(blankObject());	
}

function basicErrorCallback(err){
	console.log("err="+err);
	if(err && err.length>256){
		err = err.substring(0,256)
	}
	document.getElementById("spanMsg").innerHTML=err;
	document.getElementById("spanMsg").style.color="red";
}


function updateSelected(){
	let objectJs = objectFromInput();
	if(!canDoAction("update" , objectJs))return;
	let objectJson = JSON.stringify(objectJs) ;  
	let wsUrl = getWsBaseUrl();   
	makeAjaxPutRequest(wsUrl,objectJson,function (responseJson){
		console.log("responseJson="+responseJson);
		//document.getElementById("spanMsg").innerHTML="successfully updated";
		refreshAll();
	},basicErrorCallback); 
}

function deleteSelected(){
	let id = (document.getElementById("inputId")).value;
	if(id!=null && id!=""){
		let wsUrl = getWsBaseUrl() + "/" +id;
		makeAjaxDeleteRequest(wsUrl,function(responseJson){
			refreshAll();
			} , basicErrorCallback);
	}
}

function addNew(){	
	let objectJs = objectFromInput();
	if(!canDoAction("add" , objectJs))return;
	let objectJson = JSON.stringify(objectJs) ;  
	let wsUrl = getWsBaseUrl();   
	makeAjaxPostRequest(wsUrl,objectJson,function (responseJson){
		console.log("responseJson="+responseJson);
		refreshAll(); //pour rafraîchir le tableau avec objet ajouté
	},basicErrorCallback);         
}



function onSelectRow(evt){
	document.getElementById("spanMsg").innerHTML="";
	document.getElementById("spanMsg").style.color="black";
	let clickElt = evt.target;
	if(selectedRow!=null){
		selectedRow.style.color="black";
	}
	let parentRow = clickElt.parentElement;
	selectedRow = parentRow;
	parentRow.style.color="blue";
	let idSelected=parentRow.firstChild.innerText;
	console.log("idSelecte="+idSelected);
	searchById(idSelected);
}

function searchById(id){	
	let wsUrl = getWsBaseUrl()+"/"+id;
	
	makeAjaxGetRequest(wsUrl,function(responseJson){
		let objectJs = JSON.parse(responseJson);
		selectedObject=objectJs;
		document.getElementById("spanMsg").innerHTML="selectedId="+id;
		console.log("objectJs="+JSON.stringify(objectJs));
		displayObject(objectJs);
		},basicErrorCallback);
}


	
function refreshAll(){	
	let wsUrl = getWsBaseUrl();
	let criteriaElt = document.getElementById("inputCriteria");
	if(criteriaElt!=null){
		let criteria = criteriaElt.value;
		if(criteria != null && criteria!= "")
		   wsUrl = wsUrl + "?" + criteria;
	}
	selectedRow = null;
	makeAjaxGetRequest(wsUrl,function(responseJson){
		let objectListJs = JSON.parse(responseJson);
		console.log("objectListJs="+objectListJs);
		
		let bodyElt = document.getElementById("table_body");
		bodyElt.innerHTML="";//vider le tableau avant de le re-remplir
		for(let obj of objectListJs){
			let row = bodyElt.insertRow(-1);
			insertRowCells(row,obj);
			row.addEventListener("click" , onSelectRow);
		}
		
		resetObject();
	},basicErrorCallback);
	
}
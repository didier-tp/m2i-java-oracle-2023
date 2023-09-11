var selectedRow = null;
var selectedNews = null;

window.onload=function(){
	(document.getElementById("inputId")).disabled=true;
	
	(document.getElementById("btnRefreshAll"))
	   .addEventListener("click",refreshAllNews);
	   
	(document.getElementById("btnAdd"))
	   .addEventListener("click",addNews); 
	   
	(document.getElementById("btnUpdate"))
	   .addEventListener("click",updateNews); 
	   
	(document.getElementById("btnReset"))
	   .addEventListener("click",resetNews); 
	   
	(document.getElementById("btnDelete"))
	   .addEventListener("click",deleteNews);  
}

function resetNews(){
	if(selectedRow!=null){
		selectedRow.style.color="black";
		selectedRow=null;
		selectedNews = null;
	}
	displayNewsJs({id:"" , text: "" , date : (new Date()).toISOString()});	
}

function updateNews(){
	let newsJs = newsJsFromInput();
	if(newsJs.text==null || newsJs.text == "") return;
	let newsJson = JSON.stringify(newsJs) ;  
	let wsUrl = "./rest/api-news/news";   
	makeAjaxPutRequest(wsUrl,newsJson,function (responseJson){
		console.log("responseJson="+responseJson);
		//document.getElementById("spanMsg").innerHTML="news updated";
		refreshAllNews();
	}); 
}

function deleteNews(){
	let id = (document.getElementById("inputId")).value;
	if(id!=null && id!=""){
		let wsUrl = "./rest/api-news/news/"+id;
		makeAjaxDeleteRequest(wsUrl,function(responseJson){
			refreshAllNews();
			});
	}
}

function addNews(){	
	let newsJs = newsJsFromInput();
	if(newsJs.text==null || newsJs.text == "") return;
	let newsJson = JSON.stringify(newsJs) ;  
	let wsUrl = "./rest/api-news/news";   
	makeAjaxPostRequest(wsUrl,newsJson,function (responseJson){
		console.log("responseJson="+responseJson);
		refreshAllNews(); //pour rafraîchir le tableau avec nouvelle news ajoutée
	});         
}

function newsJsFromInput(){
	let id = (document.getElementById("inputId")).value;
	if(id=="")id=null;
	let text = (document.getElementById("inputText")).value;
	let date = (document.getElementById("inputDate")).value;
	if(date==null || date==""){
		date = (new Date()).toISOString();
	}
	document.getElementById("spanMsg").innerHTML="";
	let newsJs = { id : id,
				   text : text,
	               date : date };
	return newsJs;
}

function displayNewsJs(newsJs){
	(document.getElementById("inputId")).value=newsJs.id;
	(document.getElementById("inputText")).value=newsJs.text;
	(document.getElementById("inputDate")).value=newsJs.date;
}

function onSelectRow(evt){
	let clickElt = evt.target;
	if(selectedRow!=null){
		selectedRow.style.color="black";
	}
	let parentRow = clickElt.parentElement;
	selectedRow = parentRow;
	parentRow.style.color="blue";
	let idSelectedNews=parentRow.firstChild.innerText;
	console.log("idSelectedNews="+idSelectedNews);
	rechercherNews(idSelectedNews);
}

function rechercherNews(id){	
	let wsUrl = "./rest/api-news/news/"+id;
	
	makeAjaxGetRequest(wsUrl,function(responseJson){
		let newsJs = JSON.parse(responseJson);
		selectedNews=newsJs;
		document.getElementById("spanMsg").innerHTML="selectedNewsId="+id;
		console.log("newsJs="+JSON.stringify(newsJs));
		displayNewsJs(newsJs);
		});
}
	
function refreshAllNews(){	
	let wsUrl = "./rest/api-news/news";
	selectedRow = null;
	makeAjaxGetRequest(wsUrl,function(responseJson){
		let newsJs = JSON.parse(responseJson);
		console.log("newsJs="+newsJs);
		
		let bodyElt = document.getElementById("table_body");
		bodyElt.innerHTML="";//vider le tableau avant de le re-remplir
		for(let n of newsJs){
			let row = bodyElt.insertRow(-1);
			(row.insertCell(0)).innerHTML = n.id;
			(row.insertCell(1)).innerHTML = n.text;
			(row.insertCell(2)).innerHTML = n.date;
			row.addEventListener("click" , onSelectRow);
		}
		
		resetNews();
	});
	
}
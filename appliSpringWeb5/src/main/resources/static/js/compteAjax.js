window.onload=function(){
	rechercherClients(); //remplir liste deroulante avec clients existants
	
	(document.getElementById("btnRechercher"))
	   .addEventListener("click",rechercherComptesSelonSoldeMini);
	   
	(document.getElementById("btnAjout"))
	   .addEventListener("click",ajouterCompte);   
}

function ajouterCompte(){	
	let label = (document.getElementById("inputLabel")).value;
	let soldeInitial = (document.getElementById("inputSoldeInitial")).value;
	//let customerId = (document.getElementById("inputCustomerId")).value;
	let customerId = (document.getElementById("selectCustomerId")).value;
	let compteJs = { label : label,
	                 solde : parseFloat(soldeInitial) ,
	                 customerId : parseInt(customerId)};
	let compteJson = JSON.stringify(compteJs) ;  
	let wsUrl = "./rest/api-bank/compte";   
	makeAjaxPostRequest(wsUrl,compteJson,function (responseJson){
		console.log("responseJson="+responseJson);
		rechercherComptesSelonSoldeMini(); //pour rafraîchir le tableau avec nouveau compte ajouté
	});         
}

function rechercherClients(){	
	let wsUrl = "./rest/api-bank/customer";
	makeAjaxGetRequest(wsUrl,function(responseJson){
		let clientsJs = JSON.parse(responseJson);
		console.log("clientsJs="+clientsJs);
		
		let selectElt = document.getElementById("selectCustomerId");
		//selectElt.innerHTML="";//vider la liste 
		for(let client of clientsJs){
			let option = document.createElement("option");
			option.value=client.id;
			option.innerHTML=JSON.stringify(client);
			selectElt.appendChild(option);
		}
	});
	
}
	
function rechercherComptesSelonSoldeMini(){	
	let soldeMini = (document.getElementById("inputSoldeMini")).value;
	
	let wsUrl = "./rest/api-bank/compte?soldeMini="+soldeMini;
	
	makeAjaxGetRequest(wsUrl,function(responseJson){
		let comptesJs = JSON.parse(responseJson);
		console.log("comptesJs="+comptesJs);
		
		let bodyElt = document.getElementById("table_body");
		bodyElt.innerHTML="";//vider le tableau avant de le re-remplir
		for(let compte of comptesJs){
			let row = bodyElt.insertRow(-1);
			(row.insertCell(0)).innerHTML = compte.numero;
			(row.insertCell(1)).innerHTML = compte.label;
			(row.insertCell(2)).innerHTML = compte.solde;
			(row.insertCell(3)).innerHTML = compte.customerId;
		}
	});
	
}
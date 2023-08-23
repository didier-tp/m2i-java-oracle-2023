window.onload=function(){
	(document.getElementById("btnRechercher"))
	   .addEventListener("click",rechercherCustomers);
	   
	(document.getElementById("btnAjout"))
	   .addEventListener("click",ajouterCustomer);   
}

function ajouterCustomer(){	
	let prenom = (document.getElementById("inputPrenom")).value;
	let nom = (document.getElementById("inputNom")).value;
	let password = (document.getElementById("inputPassword")).value;
	let customerJs = { firstname : prenom,
	                 lastname : nom,
	                 password:password };
	let customerJson = JSON.stringify(customerJs) ;  
	let wsUrl = "./api-bank/customer";   
	makeAjaxPostRequest(wsUrl,customerJson,function (responseJson){
		console.log("responseJson="+responseJson);
		rechercherCustomers(); //pour rafraîchir le tableau avec nouveau customer ajouté
	});         
}
	
function rechercherCustomers(){	
	let wsUrl = "./api-bank/customer";
	
	makeAjaxGetRequest(wsUrl,function(responseJson){
		let customersJs = JSON.parse(responseJson);
		console.log("customerJs="+customersJs);
		
		let bodyElt = document.getElementById("table_body");
		bodyElt.innerHTML="";//vider le tableau avant de le re-remplir
		for(let customer of customersJs){
			let row = bodyElt.insertRow(-1);
			(row.insertCell(0)).innerHTML = customer.id;
			(row.insertCell(1)).innerHTML = customer.firstname;
			(row.insertCell(2)).innerHTML = customer.lastname;
			(row.insertCell(3)).innerHTML = customer.password;
		}
	});
	
}
window.onload=function(){
	(document.getElementById("btnRechercher"))
	   .addEventListener("click",rechercherComptesSelonSoldeMini);
}
	
function rechercherComptesSelonSoldeMini(){	
	let soldeMini = (document.getElementById("inputSoldeMini")).value;
	
	let wsUrl = "./api-bank/compte?soldeMini="+soldeMini;
	
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
		}
	});
	
}
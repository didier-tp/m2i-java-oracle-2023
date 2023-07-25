window.onload=function(){
	
	let wsUrl = "./api-bank/compte";
	
	makeAjaxGetRequest(wsUrl,function(responseJson){
		let comptesJs = JSON.parse(responseJson);
		console.log("comptesJs="+comptesJs);
		
		let bodyElt = document.getElementById("table_body");
		for(let compte of comptesJs){
			let row = bodyElt.insertRow(-1);
			(row.insertCell(0)).innerHTML = compte.numero;
			(row.insertCell(1)).innerHTML = compte.label;
			(row.insertCell(2)).innerHTML = compte.solde;
		}
	});
	
}
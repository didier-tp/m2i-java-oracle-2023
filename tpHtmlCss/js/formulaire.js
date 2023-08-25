function enregistrer(){
    let inputNom = document.querySelector("input[name='nom'] ");
    let valeurSaisieNom = inputNom.value;

    let inputPrenom = document.querySelector("input[name='prenom'] ");
    let valeurSaisiePrenom = inputPrenom.value;

    let personneJs = {
        nom : valeurSaisieNom ,
        prenom : valeurSaisiePrenom 
    }
    let presonneAsJsonString  = JSON.stringify(personneJs);

    let zoneMsg = document.getElementById("msg");
    zoneMsg.innerHTML=presonneAsJsonString;
}
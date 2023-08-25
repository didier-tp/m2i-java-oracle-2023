function enregistrer(){
    let inputNom = document.querySelector("input[name='nom'] ");
    let valeurSaisieNom = inputNom.value;

    let inputPrenom = document.querySelector("input[name='prenom'] ");
    let valeurSaisiePrenom = inputPrenom.value;

    let selectSituation = document.querySelector("select[name='situation'] ");
    let valeurChoiseSituation = selectSituation.value;

    let inputDateNaissance = document.querySelector("input[name='dateNaissance'] ");
    let valeurSaisieDateNaissance = inputDateNaissance.value;

    let personneJs = {
        nom : valeurSaisieNom ,
        prenom : valeurSaisiePrenom ,
        dateNaissance: valeurSaisieDateNaissance,
        situation: valeurChoiseSituation
    }
    let presonneAsJsonString  = JSON.stringify(personneJs);

    let zoneMsg = document.getElementById("msg");
    zoneMsg.innerHTML=presonneAsJsonString;
}
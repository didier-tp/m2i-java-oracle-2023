function enregistrer(){
    let inputPrenom = document.querySelector("input[name='prenom'] ");
    console.log("zonePrenomValide=" + inputPrenom.checkValidity());
    let valeurSaisiePrenom = inputPrenom.value;

    let inputNom = document.querySelector("input[name='nom'] ");
    console.log("zoneNomValide=" + inputNom.checkValidity());
    let valeurSaisieNom = inputNom.value;

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
package com.inetum.appliSpringWeb.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inetum.appliSpringWeb.dao.DaoCompte;
import com.inetum.appliSpringWeb.entity.Compte;

@RestController
@RequestMapping(value="/api-bank/compte" , headers="Accept=application/json")
//@CrossOrigin permet d'ajouter des autorisations "CORS" pour que ce web service
//puisse être appelé en mode ajax depuis d'autres origines/url que http://localhost:8080
@CrossOrigin(origins = "*" , methods = { RequestMethod.GET , RequestMethod.POST})
public class CompteRestCtrl {
	
	//NB: cette version 1 n'utilise pas encore les DTOs 
	
	@Autowired
	private DaoCompte daoCompteJpa;
	
	//exemple de fin d'URL: ./api-bank/compte/1
	@GetMapping("/{numeroCompte}" )
	public ResponseEntity<?> getCompteByNumero(@PathVariable("numeroCompte") Long numeroCompte) {
	    Compte compte = daoCompteJpa.findById(numeroCompte);
	    if(compte!=null)
	    	return new ResponseEntity<Compte>(compte, HttpStatus.OK);
	    else
	    	return new ResponseEntity<String>("{ \"err\" : \"compte not found\"}" ,
	    			           HttpStatus.NOT_FOUND); //NOT_FOUND = code http 404
	}
	
	//exemple de fin d'URL: ./api-bank/compte
	//                      ./api-bank/compte?soldeMini=0
	@GetMapping("" )
	public List<Compte> getComptes(
			 @RequestParam(value="soldeMini",required=false) Double soldeMini){
		if(soldeMini==null)
			return daoCompteJpa.findAll();
		else
			return daoCompteJpa.findBySoldeMini(soldeMini);
	}
	
	//exemple de fin d'URL: ./api-bank/compte
	//appelé en mode POST avec dans la partie invisible "body" de la requête:
	// { "numero" : null , "label" : "compteQuiVaBien" , "solde" : 50.0 }
	// ou bien { "label" : "compteQuiVaBien" , "solde" : 50.0 }
	@PostMapping("" )
	public Compte postCompte(@RequestBody Compte nouveauCompte) {
		Compte compteEnregistreEnBase = daoCompteJpa.insert(nouveauCompte);
		return compteEnregistreEnBase; //on retourne le compte avec clef primaire auto_incrémentée
	}
	
	//exemple de fin d'URL: ./api-bank/compte
	//appelé en mode PUT avec dans la partie invisible "body" de la requête:
	// { "numero" : 5 , "label" : "compte5QueJaime" , "solde" : 150.0 }
	@PutMapping("" )
	public ResponseEntity<?> putCompteToUpdate(@RequestBody Compte compte) {
		    Long numCompteToUpdate = compte.getNumero();
		    Compte compteQuiDevraitExister = daoCompteJpa.findById(numCompteToUpdate);
		    if(compteQuiDevraitExister==null)
		    	return new ResponseEntity<String>("{ \"err\" : \"compte not found\"}" ,
 			           HttpStatus.NOT_FOUND); //NOT_FOUND = code http 404
			daoCompteJpa.update(compte);
			return new ResponseEntity<Compte>(compte , HttpStatus.OK);
	}
	
	
	
	
	
	
	

}

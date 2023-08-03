package com.inetum.appliSpringWeb.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inetum.appliSpringWeb.converter.DtoConverter;
import com.inetum.appliSpringWeb.dto.CompteDto;
import com.inetum.appliSpringWeb.entity.Compte;
import com.inetum.appliSpringWeb.service.ServiceCompte;

@RestController
@RequestMapping(value="/api-bank/compte" , headers="Accept=application/json")
//@CrossOrigin permet d'ajouter des autorisations "CORS" pour que ce web service
//puisse être appelé en mode ajax depuis d'autres origines/url que http://localhost:8080
@CrossOrigin(origins = "*" , methods = { RequestMethod.GET , RequestMethod.POST})
public class CompteRestCtrl {
	
	//cette version 2 utilise  les DTOs 
	
	@Autowired
	private ServiceCompte serviceCompte;
	
	private DtoConverter dtoConverter = new DtoConverter();
	
	//exemple de fin d'URL: ./api-bank/compte/1
	@GetMapping("/{numeroCompte}" )
	public ResponseEntity<?> getCompteByNumero(@PathVariable("numeroCompte") Long numeroCompte) {
	    Compte compte = serviceCompte.rechercherCompteParNumero(numeroCompte);
	    if(compte!=null)
	    	return new ResponseEntity<CompteDto>(
	    			 dtoConverter.compteToCompteDto(compte), HttpStatus.OK);
	    else
	    	return new ResponseEntity<String>("{ \"err\" : \"compte not found\"}" ,
	    			           HttpStatus.NOT_FOUND); //NOT_FOUND = code http 404
	}
	
	//exemple de fin d'URL: ./api-bank/compte/1
	//à déclencher en mode DELETE
	@DeleteMapping("/{numeroCompte}" )
	public ResponseEntity<?> deleteCompteByNumero(@PathVariable("numeroCompte") Long numeroCompte) {
		    
		    if( !serviceCompte.verifierExistanceCompte(numeroCompte))
		    	return new ResponseEntity<String>("{ \"err\" : \"compte not found\"}" ,
		    			           HttpStatus.NOT_FOUND); //NOT_FOUND = code http 404
		    
		    serviceCompte.supprimerCompte(numeroCompte);
		    return new ResponseEntity<String>("{ \"done\" : \"compte deleted\"}" ,HttpStatus.OK); 
		    //ou bien
		    //return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
		}
	
	//exemple de fin d'URL: ./api-bank/compte
	//                      ./api-bank/compte?soldeMini=0
	@GetMapping("" )
	public List<Compte> getComptes(
			 @RequestParam(value="soldeMini",required=false) Double soldeMini){
		if(soldeMini==null)
			return serviceCompte.rechercherTout();
		else
			return serviceCompte.rechercherSelonSoldeMini(soldeMini);
	}
	
	//exemple de fin d'URL: ./api-bank/compte
	//appelé en mode POST avec dans la partie invisible "body" de la requête:
	// { "numero" : null , "label" : "compteQuiVaBien" , "solde" : 50.0 }
	// ou bien { "label" : "compteQuiVaBien" , "solde" : 50.0 }
	@PostMapping("" )
	public CompteDto postCompte(@RequestBody CompteDto nouveauCompte) {
		Compte compteEnregistreEnBase = serviceCompte.sauvegarderCompte(
				              dtoConverter.compteDtoToCompte(nouveauCompte) );
		//on retourne le compte avec clef primaire auto_incrémentée
		return dtoConverter.compteToCompteDto(compteEnregistreEnBase); 
	}
	
	//exemple de fin d'URL: ./api-bank/compte
	//ou bien               ./api-bank/compte/5
	//appelé en mode PUT avec dans la partie invisible "body" de la requête:
	// { "numero" : 5 , "label" : "compte5QueJaime" , "solde" : 150.0 }
	//ou bien {  "label" : "compte5QueJaime" , "solde" : 150.0 }
	@PutMapping({"" , "/{numeroCompte}" })
	public ResponseEntity<?> putCompteToUpdate(@RequestBody CompteDto compteDto , 
			      @PathVariable(value="numeroCompte",required = false ) Long numeroCompte) {
		
		    Long numCompteToUpdate = numeroCompte!=null ? numeroCompte : compteDto.getNumero();
		   
		   
		    if(!serviceCompte.verifierExistanceCompte(numCompteToUpdate))
		    	return new ResponseEntity<String>("{ \"err\" : \"compte not found\"}" ,
 			           HttpStatus.NOT_FOUND); //NOT_FOUND = code http 404
		    
		    if(compteDto.getNumero()==null)
		    	compteDto.setNumero(numCompteToUpdate);
		    
			serviceCompte.sauvegarderCompte(dtoConverter.compteDtoToCompte(compteDto));
			return new ResponseEntity<CompteDto>(compteDto , HttpStatus.OK);
	}
	
	
	
	
	
	
	

}

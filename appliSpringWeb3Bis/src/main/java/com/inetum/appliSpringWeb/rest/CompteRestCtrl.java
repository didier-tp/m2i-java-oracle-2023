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
import com.inetum.appliSpringWeb.dto.CompteL0;
import com.inetum.appliSpringWeb.dto.CompteL1;
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
	/*
	//exemple de fin d'URL: ./api-bank/compte/1
	@GetMapping("/{numeroCompte}" )
	public ResponseEntity<?> getCompteByNumero(@PathVariable("numeroCompte") Long numeroCompte) {
	    CompteDto compteDto = serviceCompte.searchDtoById(numeroCompte);
	    if(compteDto!=null)
	    	return new ResponseEntity<CompteDto>(
	    			 compteDto, HttpStatus.OK); 
	    else
	    	return new ResponseEntity<ApiError>(
	    			           new ApiError(HttpStatus.NOT_FOUND ,"compte not found") ,
	    			           HttpStatus.NOT_FOUND); //NOT_FOUND = code http 404
	}
	*/
	
	//exemple de fin d'URL: ./api-bank/compte/1
	// ou ./api-bank/1?detailLevel=2
	@GetMapping("/{numeroCompte}" )
	public CompteL0 getCompteByNumero(@PathVariable("numeroCompte") Long numeroCompte,
			                           @RequestParam(value="detailLevel",required=false) Integer detailLevel) {
		    return serviceCompte.searchDtoByIdWithDetailLevel(numeroCompte,detailLevel);
		    //en cas de numero de compte pas trouvé
		    //l'exception "NotFoundException" remontée par l'appel à .searchDtoById(...)
		    //est automatiquement gérée par RestResponseEntityExceptionHandler
		    //et  celui ci construit et retourne automatiquement
		    //un ResponseEntity<ApiError> avec le bon status http et le bon message
	}
	
	@GetMapping("/{numeroCompte}/withNumClient" )
	public CompteL1 getCompteWithNumClientByNumero(@PathVariable("numeroCompte") Long numeroCompte) {
		    return serviceCompte.searchDtoExByIdWithNumClient(numeroCompte);
	}
	
	//exemple de fin d'URL: ./api-bank/compte/1
	//à déclencher en mode DELETE
	@DeleteMapping("/{numeroCompte}" )
	public ResponseEntity<?> deleteCompteByNumero(@PathVariable("numeroCompte") Long numeroCompte) {
		    serviceCompte.deleteById(numeroCompte);//retournant NotFoundException
		    return new ResponseEntity<String>("{ \"done\" : \"compte deleted\"}" ,
		    		                          HttpStatus.OK); 
		}
	
	/*
	@DeleteMapping("/{numeroCompte}" )
	public ResponseEntity<?> deleteCompteByNumero(@PathVariable("numeroCompte") Long numeroCompte) {
		    
		    if( !serviceCompte.existById(numeroCompte))
		    	return new ResponseEntity<String>("{ \"err\" : \"compte not found\"}" ,
		    			           HttpStatus.NOT_FOUND); //NOT_FOUND = code http 404
		    
		    serviceCompte.deleteById(numeroCompte);
		    return new ResponseEntity<String>("{ \"done\" : \"compte deleted\"}" ,HttpStatus.OK); 
		    //ou bien
		    //return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
		}
	*/
	
	//exemple de fin d'URL: ./api-bank/compte
	//                      ./api-bank/compte?soldeMini=0
	@GetMapping("" )
	public List<CompteL1> getComptes(
			 @RequestParam(value="soldeMini",required=false) Double soldeMini){
		if(soldeMini==null)
			//return serviceCompte.searchAllDto();
		    return serviceCompte.searchAllDtoEx();
		else
			//return dtoConverter.compteToCompteDto(
			return dtoConverter.compteToCompteDtoEx(
					serviceCompte.rechercherSelonSoldeMini(soldeMini));
	}
	
	/*
	//exemple de fin d'URL: ./api-bank/compte
	//appelé en mode POST avec dans la partie invisible "body" de la requête:
	// { "numero" : null , "label" : "compteQuiVaBien" , "solde" : 50.0 }
	// ou bien { "label" : "compteQuiVaBien" , "solde" : 50.0  }
	@PostMapping("" ) //ANCIENNE VERSION sans CompteDtoEx et .numeroClient 
	public CompteDto postCompte(@RequestBody CompteDto nouveauCompte) {
		Compte compteEnregistreEnBase = serviceCompte.saveOrUpdate(
				              dtoConverter.compteDtoToCompte(nouveauCompte) );
		//on retourne le compte avec clef primaire auto_incrémentée
		return dtoConverter.compteToCompteDtoEx(compteEnregistreEnBase); 
	}
	*/
	
	//exemple de fin d'URL: ./api-bank/compte
		//appelé en mode POST avec dans la partie invisible "body" de la requête:
		// { "numero" : null , "label" : "compteQuiVaBien" , "solde" : 50.0 , "numeroClient" : 1}
		// ou bien { "label" : "compteQuiVaBien" , "solde" : 50.0  , "numeroClient" : null}
		@PostMapping("" ) //NOUVELLE VERSION avec CompteDtoEx et .numeroClient (éventuellement null)
		public CompteL0 postCompte(@RequestBody CompteL1 nouveauCompte) {
		    //on s'appuie ici sur la méthode spécifique ci dessous du serviceCompte
			return serviceCompte.saveOrUpdateCompteDtoEx(nouveauCompte);
		}
	
	/*	
	//exemple de fin d'URL: ./api-bank/compte
	//ou bien               ./api-bank/compte/5
	//appelé en mode PUT avec dans la partie invisible "body" de la requête:
	// { "numero" : 5 , "label" : "compte5QueJaime" , "solde" : 150.0 }
	//ou bien {  "label" : "compte5QueJaime" , "solde" : 150.0 }
	@PutMapping({"" , "/{numeroCompte}" }) //ANCIENNE VERSION SANS DtoEx ni .numeroClient
	public ResponseEntity<?> putCompteToUpdate(@RequestBody CompteDto compteDto , 
			      @PathVariable(value="numeroCompte",required = false ) Long numeroCompte) {
		
		    Long numCompteToUpdate = numeroCompte!=null ? numeroCompte : compteDto.getNumero();
		   
		    serviceCompte.shouldExistById(numCompteToUpdate);//remonte NotFoundException si pas trouvé
		    
		    if(compteDto.getNumero()==null)
		    	compteDto.setNumero(numCompteToUpdate);
		    
			serviceCompte.saveOrUpdate(dtoConverter.compteDtoToCompte(compteDto));
			return new ResponseEntity<CompteDto>(compteDto , HttpStatus.OK);
	}
	*/
		
		//exemple de fin d'URL: ./api-bank/compte
		//ou bien               ./api-bank/compte/5
		//appelé en mode PUT avec dans la partie invisible "body" de la requête:
		// { "numero" : 5 , "label" : "compte5QueJaime" , "solde" : 150.0 , "numeroClient" : null}
		//ou bien {  "label" : "compte5QueJaime" , "solde" : 150.0 , "numeroClient" : 1}
		@PutMapping({"" , "/{numeroCompte}" }) //NOUVELLE VERSION AVEC DtoEx et .numeroClient
		public ResponseEntity<?> putCompteToUpdate(@RequestBody CompteL1 compteDto , 
				      @PathVariable(value="numeroCompte",required = false ) Long numeroCompte) {
			
			    Long numCompteToUpdate = numeroCompte!=null ? numeroCompte : compteDto.getNumero();
			   
			    serviceCompte.shouldExistById(numCompteToUpdate);//remonte NotFoundException si pas trouvé
			    
			    if(compteDto.getNumero()==null)
			    	compteDto.setNumero(numCompteToUpdate);
			    
			    //on s'appuie ici sur la méthode spécifique ci dessous du serviceCompte
				serviceCompte.saveOrUpdateCompteDtoEx(compteDto); 
				
				return new ResponseEntity<CompteL0>(compteDto , HttpStatus.OK);
		}	

}

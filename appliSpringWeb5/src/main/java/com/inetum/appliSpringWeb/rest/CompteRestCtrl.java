package com.inetum.appliSpringWeb.rest;

import java.util.List;

import org.mycontrib.util.generic.dto.DtoByLevelUtil;
import org.mycontrib.util.generic.rest.AbstractGenericRestCtrlWithMapping;
import org.mycontrib.util.generic.service.GenericServiceWithDto;
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

import com.inetum.appliSpringWeb.converter.DtoConverter;
import com.inetum.appliSpringWeb.dto.CompteL0;
import com.inetum.appliSpringWeb.dto.CompteL1;
import com.inetum.appliSpringWeb.dto.CompteL2;
import com.inetum.appliSpringWeb.service.ServiceCompte;

@RestController
@RequestMapping(value="/rest/api-bank/compte" , headers="Accept=application/json")
//@CrossOrigin permet d'ajouter des autorisations "CORS" pour que ce web service
//puisse être appelé en mode ajax depuis d'autres origines/url que http://localhost:8080
@CrossOrigin(origins = "*" , methods = { RequestMethod.GET , RequestMethod.POST})
public class CompteRestCtrl extends AbstractGenericRestCtrlWithMapping<Long,CompteL0>{
	
	@Autowired
	private ServiceCompte serviceCompte;
	
	@Override
	public GenericServiceWithDto<Long> getGenericServiceWithDto() {
		return serviceCompte;
	}
	
	@Override
	public Class<CompteL0> getMainDtoClass() {
		DtoByLevelUtil.INSTANCE.setDtoClassForLevel(CompteL0.class, 1, CompteL1.class);
		DtoByLevelUtil.INSTANCE.setDtoClassForLevel(CompteL0.class, 2, CompteL2.class);
		return CompteL0.class;
	}

	
	private DtoConverter dtoConverter = new DtoConverter();
	
	

	//exemple de fin d'URL: ./api-bank/compte
	//                      ./api-bank/compte?soldeMini=0
	@GetMapping("" )
	public List<CompteL1> getComptes(
			 @RequestParam(value="soldeMini",required=false) Double soldeMini){
		if(soldeMini==null)
			//return serviceCompte.searchAllDto();
		    return serviceCompte.searchAllDto(CompteL1.class);
		else
			//return dtoConverter.compteToCompteDto(
			return dtoConverter.map(
					serviceCompte.rechercherSelonSoldeMini(soldeMini),CompteL1.class);
	}
	
	
	
	//exemple de fin d'URL: ./api-bank/compte
	//appelé en mode POST avec dans la partie invisible "body" de la requête:
	// { "numero" : null , "label" : "compteQuiVaBien" , "solde" : 50.0 , "numeroClient" : 1}
	// ou bien { "label" : "compteQuiVaBien" , "solde" : 50.0  , "numeroClient" : null}
	@PostMapping("" ) //NOUVELLE VERSION avec CompteDtoEx et .numeroClient (éventuellement null)
	public CompteL1 postCompte(@RequestBody CompteL1 nouveauCompte) {
	    //on s'appuie ici sur la méthode spécifique ci dessous du serviceCompte
		return serviceCompte.saveNewFromDto(nouveauCompte);
	}
	
	
		
		//exemple de fin d'URL: ./api-bank/compte
		//ou bien               ./api-bank/compte/5
		//appelé en mode PUT avec dans la partie invisible "body" de la requête:
		// { "numero" : 5 , "label" : "compte5QueJaime" , "solde" : 150.0 , "numeroClient" : null}
		//ou bien {  "label" : "compte5QueJaime" , "solde" : 150.0 , "numeroClient" : 1}
		@PutMapping({"" , "/{id}" }) 
		public CompteL1 putCompteToUpdate(@RequestBody CompteL1 compteDto , 
				      @PathVariable(value="id",required = false ) Long idToUpdate) {
			    if(compteDto.getNumero()==null)	compteDto.setNumero(idToUpdate);
				serviceCompte.updateExistingFromDto(compteDto); //remonte NotFoundException si pas trouvé
				return compteDto;
		}

}

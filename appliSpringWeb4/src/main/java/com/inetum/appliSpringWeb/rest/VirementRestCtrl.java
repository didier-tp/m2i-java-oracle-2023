package com.inetum.appliSpringWeb.rest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inetum.appliSpringWeb.dto.VirementRequest;
import com.inetum.appliSpringWeb.dto.VirementResponse;
import com.inetum.appliSpringWeb.entity.Compte;
import com.inetum.appliSpringWeb.service.ServiceCompte;

@RestController
@RequestMapping(value="/api-bank/virement" , 
                headers="Accept=application/json")
public class VirementRestCtrl {
	
	@Autowired
	private ServiceCompte serviceCompte;
	
	//exemple de fin d'URL: ./api-bank/virement
	//appelé en mode POST avec dans la partie invisible "body" de la requête:
	// { "numCompteDebit" : 1 , "numCompteCredit" : 2 , "montant" : 50.0 }
	@PostMapping("" )
	public VirementResponse postVirement(@RequestBody VirementRequest virementRequest) {
		    VirementResponse virementResponse = new VirementResponse();
		    /*
		    virementResponse.setMontant(virementRequest.getMontant());
		    virementResponse.setNumCompteDebit(virementRequest.getNumCompteDebit());
		    virementResponse.setNumCompteCredit(virementRequest.getNumCompteCredit());
		    */
		    BeanUtils.copyProperties(virementRequest, virementResponse);
		    try {
		        serviceCompte.transferer(virementRequest.getMontant(), 
		        		                 virementRequest.getNumCompteDebit(), 
		        		                 virementRequest.getNumCompteCredit());
				
				virementResponse.setStatus(true);
				virementResponse.setMessage("virement bien effectué");
			} catch (Exception e) {
				//e.printStackTrace();
				virementResponse.setStatus(false);
				virementResponse.setMessage("echec virement " + e.getMessage());
			}	
			return virementResponse;
	}

}

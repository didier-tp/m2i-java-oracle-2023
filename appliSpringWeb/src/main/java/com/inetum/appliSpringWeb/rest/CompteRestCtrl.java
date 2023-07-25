package com.inetum.appliSpringWeb.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inetum.appliSpringWeb.dao.DaoCompte;
import com.inetum.appliSpringWeb.entity.Compte;

@RestController
@RequestMapping(value="/api-bank/compte" , headers="Accept=application/json")
public class CompteRestCtrl {
	
	//NB: cette version 1 n'utilise pas encore les DTOs 
	
	@Autowired
	private DaoCompte daoCompteJpa;
	
	//exemple de fin d'URL: ./api-bank/compte/1
	@GetMapping("/{numeroCompte}" )
	public Compte getCompteByNumero(@PathVariable("numeroCompte") Long numeroCompte) {
	    return daoCompteJpa.findById(numeroCompte);
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
	
	
	
	
	
	
	

}

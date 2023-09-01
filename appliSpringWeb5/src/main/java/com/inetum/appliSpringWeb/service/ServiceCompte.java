package com.inetum.appliSpringWeb.service;

import java.util.List;

import org.mycontrib.util.generic.exception.NotFoundException;
import org.mycontrib.util.generic.service.GenericService;

import com.inetum.appliSpringWeb.dto.CompteL0;
import com.inetum.appliSpringWeb.dto.CompteL1;
import com.inetum.appliSpringWeb.dto.CompteL2;
import com.inetum.appliSpringWeb.entity.Compte;
import com.inetum.appliSpringWeb.entity.Operation;
import com.inetum.appliSpringWeb.exception.BankException;

//Business service / service métier
//avec remontées d'exceptions (héritant de RuntimeException)
public interface ServiceCompte extends GenericService<Compte,Long> {
	//méthode spécifique au métier de la banque 
	void debiterCompte(long numeroCompte , double montant , String message);
	void crediterCompte(long numeroCompte , double montant , String message);
	void transferer(double montant, long numCptDeb , long numCptCred) throws BankException;
	//...
	
	//méthodes déléguées aux DAOs le CRUD:
	//Compte searchById(long numeroCompte); //hérité de GenericService
	//Compte saveOrUpdate(Compte compte);//hérité de GenericService
	
	Compte rechercherCompteAvecOperationsParNumero(long numeroCompte); //sans lazy exception
	List<Operation> operationsDuCompteQueJaime(long numeroCompte);//ici ou bien sur dao operation
	
	List<Compte> rechercherComptesDuClient(long numeroCustomer);
	//...
	
	
	List<Compte> rechercherSelonSoldeMini(Double soldeMini);
	
	//void deleteById(Long numeroCompte);//hérité de GenericService
	//boolean existById(Long numeroCompte);//hérité de GenericService
	//searchAll() et searchAllDto(dtoClass) hérité de GenericService
}

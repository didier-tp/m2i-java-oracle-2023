package com.inetum.appliSpringWeb.service;

import java.util.List;

import com.inetum.appliSpringWeb.dto.CompteDto;
import com.inetum.appliSpringWeb.entity.Compte;
import com.inetum.appliSpringWeb.entity.Operation;
import com.inetum.appliSpringWeb.exception.BankException;

//Business service / service métier
//avec remontées d'exceptions (héritant de RuntimeException)
public interface ServiceCompte extends GenericService<Compte,Long,CompteDto> {
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
	
	//void deleteById(Long numeroCompte);//hérité de GenericService
	//boolean existById(Long numeroCompte);//hérité de GenericService
	//List<Compte> searchAll();//hérité de GenericService
	List<Compte> rechercherSelonSoldeMini(Double soldeMini);
}

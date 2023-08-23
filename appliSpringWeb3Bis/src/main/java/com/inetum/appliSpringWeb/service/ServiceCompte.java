package com.inetum.appliSpringWeb.service;

import java.util.List;

import com.inetum.appliSpringWeb.dto.CompteL0;
import com.inetum.appliSpringWeb.dto.CompteL1;
import com.inetum.appliSpringWeb.dto.CompteL2;
import com.inetum.appliSpringWeb.entity.Compte;
import com.inetum.appliSpringWeb.entity.Operation;
import com.inetum.appliSpringWeb.exception.BankException;
import com.inetum.appliSpringWeb.exception.NotFoundException;

//Business service / service métier
//avec remontées d'exceptions (héritant de RuntimeException)
public interface ServiceCompte extends GenericService<Compte,Long,CompteL0> {
	//méthode spécifique au métier de la banque 
	void debiterCompte(long numeroCompte , double montant , String message);
	void crediterCompte(long numeroCompte , double montant , String message);
	void transferer(double montant, long numCptDeb , long numCptCred) throws BankException;
	//...
	
	//méthodes déléguées aux DAOs le CRUD:
	//Compte searchById(long numeroCompte); //hérité de GenericService
	//Compte saveOrUpdate(Compte compte);//hérité de GenericService
	//CompteDto searchDtoById(long numeroCompte);//hérité de GenericService (detailLevel à 0)
	public CompteL0 searchDtoByIdWithDetailLevel(long numeroCompte,Integer detailLevel)throws NotFoundException ;
	CompteL1 searchDtoExByIdWithNumClient(long numeroCompte)throws NotFoundException;
	CompteL2 searchDtoEx2ByIdWithClientAndOperations(long numeroCompte)throws NotFoundException;
	
	Compte rechercherCompteAvecOperationsParNumero(long numeroCompte); //sans lazy exception
	List<Operation> operationsDuCompteQueJaime(long numeroCompte);//ici ou bien sur dao operation
	
	List<Compte> rechercherComptesDuClient(long numeroCustomer);
	//...
	
	//void deleteById(Long numeroCompte);//hérité de GenericService
	//boolean existById(Long numeroCompte);//hérité de GenericService
	//List<Compte> searchAll();//hérité de GenericService
	List<Compte> rechercherSelonSoldeMini(Double soldeMini);
	
	CompteL1 saveOrUpdateCompteDtoEx(CompteL1 compteDtoEx);
	List<CompteL1> searchAllDtoEx();
}

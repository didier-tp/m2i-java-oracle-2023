package com.inetum.appliSpringWeb.service;

import java.util.List;

import com.inetum.appliSpringWeb.entity.Compte;
import com.inetum.appliSpringWeb.exception.BankException;

//Business service / service métier
//avec remontées d'exceptions (héritant de RuntimeException)
public interface ServiceCompte {
	//méthode spécifique au métier de la banque 
	void debiterCompte(long numeroCompte , double montant , String message);
	void crediterCompte(long numeroCompte , double montant , String message);
	void transferer(double montant, long numCptDeb , long numCptCred) throws BankException;
	//...
	
	//méthodes déléguées aux DAOs le CRUD:
	Compte rechercherCompteParNumero(long numeroCompte);
	List<Compte> rechercherComptesDuClient(long numeroCustomer);
	//...
	Compte sauvegarderCompte(Compte compte);
	void supprimerCompte(long numeroCompte);
	boolean verifierExistanceCompte(long numeroCompte);
}

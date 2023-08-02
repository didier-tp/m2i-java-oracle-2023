package com.inetum.appliSpringWeb.service;

import java.util.List;

import com.inetum.appliSpringWeb.entity.Compte;

//Business service / service métier
//avec remontées d'exceptions (héritant de RuntimeException)
public interface ServiceCompte {
	//méthode spécifique au métier de la banque 
	void transferer(double montant, long numCptDeb , long numCptCred);
	//...
	
	//méthodes déléguées aux DAOs le CRUD:
	Compte rechercherCompteParNumero(long numeroCompte);
	List<Compte> rechercherComptesDuClient(long numeroCustomer);
	//...
	Compte sauvegarderCompte(Compte compte);
	void supprimerCompte(long numeroCompte);
	boolean verifierExistanceCompte(long numeroCompte);
}

package com.inetum.appliSpringWeb.service;

import java.util.List;

import com.inetum.appliSpringWeb.entity.Customer;

//Business service / service métier
//avec remontées d'exceptions (héritant de RuntimeException)
public interface ServiceCustomer {
	//méthode spécifique au métier de la banque 
	boolean checkCustomerPassword(long customerId,String password);
	String resetCustomerPassword(long customerId);//genere et affecte nouveau mot de passe temp.
	//...
	//méthodes déléguées aux DAOs le CRUD:
	Customer rechercherCustomerParId(long idCustomer); //retournant le Customer seulement
	Customer rechercherCustomerAvecComptesParNumero(long idCustomer); //sans lazy exception
	
	//utile pour voir d'eventuels doublons:
	List<Customer> rechercherCustomerSelonPrenomEtNom(String prenom,String nom); 

	//...
	Customer sauvegarderCustomer(Customer customer);
	void supprimerCustomer(long idCustomer);
	boolean verifierExistanceCustomer(long idCustomer);
}

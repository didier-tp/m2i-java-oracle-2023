package com.inetum.appliSpringWeb.init;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.inetum.appliSpringWeb.dao.DaoCompte;
import com.inetum.appliSpringWeb.dao.DaoCustomer;
import com.inetum.appliSpringWeb.dao.DaoOperation;
import com.inetum.appliSpringWeb.entity.Compte;
import com.inetum.appliSpringWeb.entity.Customer;
import com.inetum.appliSpringWeb.entity.Operation;

/*
 * classe utilitaire
 * qui initialise un jeu de données
 * au démarrage de l'application
 * Utile en mode développement si mode 
 * spring.jpa.hibernate.ddl-auto=create-drop
 */

@Component
@Profile("init")
public class InitDataSet {
	
	@Autowired
	private DaoCompte daoCompteJpa;
	
	@Autowired
	private DaoOperation daoOperationJpa;
	
	@Autowired
	private DaoCustomer daoCustomerJpa;
	
	@PostConstruct
	public void initData() {
		
		Customer customer1 = daoCustomerJpa.save(new Customer(null,"jean"  , "Aimare", "pwd1"));
		daoCustomerJpa.save(new Customer(null,"jean"  , "Bon", "pwd1"));
		daoCustomerJpa.save(new Customer(null,"jean"  , "Bon", "pwd1Bis"));
		daoCustomerJpa.save(new Customer(null,"alex"  , "Therieur", "pwd2"));
		
		Compte compteAa = new Compte(null,"compte_Aa" , 70.0);
		compteAa.setCustomer(customer1);
		compteAa = daoCompteJpa.save(compteAa);
		
		Operation op1CompteA = daoOperationJpa.save(
	    		new Operation(null,-3.2 , "achat bonbons" , new Date() , compteAa));
		
    	daoCompteJpa.save(new Compte(null,"compte_Bbb" , 80.0));
    	daoCompteJpa.save(new Compte(null,"compte_Cc" , -70.0));
    	daoCompteJpa.save(new Compte(null,"compte_Dd" , -80.0));
    	daoCompteJpa.save(new Compte(null,"compte_EEe" , 20.0));
	}

}

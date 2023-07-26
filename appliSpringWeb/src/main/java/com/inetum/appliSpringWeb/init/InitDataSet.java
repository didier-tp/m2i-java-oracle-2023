package com.inetum.appliSpringWeb.init;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inetum.appliSpringWeb.dao.DaoCompte;
import com.inetum.appliSpringWeb.dao.DaoOperation;
import com.inetum.appliSpringWeb.entity.Compte;
import com.inetum.appliSpringWeb.entity.Operation;

/*
 * classe utilitaire
 * qui initialise un jeu de données
 * au démarrage de l'application
 * Utile en mode développement si mode 
 * spring.jpa.hibernate.ddl-auto=create-drop
 */

@Component
//@Profile("init")
public class InitDataSet {
	
	@Autowired
	private DaoCompte daoCompteJpa;
	
	@Autowired
	private DaoOperation daoOperationJpa;
	
	@PostConstruct
	public void initData() {
		Compte compteAa = daoCompteJpa.insert(new Compte(null,"compte_Aa" , 70.0));
		
		Operation op1CompteA = daoOperationJpa.insert(
	    		new Operation(null,-3.2 , "achat bonbons" , new Date() , compteAa));
		
    	daoCompteJpa.insert(new Compte(null,"compte_Bb" , 80.0));
    	daoCompteJpa.insert(new Compte(null,"compte_Cc" , -70.0));
    	daoCompteJpa.insert(new Compte(null,"compte_Dd" , -80.0));
    	daoCompteJpa.insert(new Compte(null,"compte_EE" , 20.0));
	}

}

package com.inetum.appliSpringWeb.init;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.inetum.appliSpringWeb.dao.DaoCompte;
import com.inetum.appliSpringWeb.entity.Compte;

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
	
	@PostConstruct
	public void initData() {
		daoCompteJpa.insert(new Compte(null,"compte_Aa" , 70.0));
    	daoCompteJpa.insert(new Compte(null,"compte_Bb" , 80.0));
	}

}

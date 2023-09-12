package com.inetum.appliSpringWeb.init;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.inetum.appliSpringWeb.dao.DaoCompte;
import com.inetum.appliSpringWeb.dao.DaoCustomer;
import com.inetum.appliSpringWeb.dao.DaoDevise;
import com.inetum.appliSpringWeb.dao.DaoNews;
import com.inetum.appliSpringWeb.dao.DaoOperation;
import com.inetum.appliSpringWeb.entity.Compte;
import com.inetum.appliSpringWeb.entity.Customer;
import com.inetum.appliSpringWeb.entity.Devise;
import com.inetum.appliSpringWeb.entity.News;
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
	private DaoDevise daoDeviseJpa;
	
	
	@Autowired
	private DaoOperation daoOperationJpa;
	
	@Autowired
	private DaoCustomer daoCustomerJpa;
	
	@Autowired
	private DaoNews daoNewsJpa;
	
	@PostConstruct
	public void initData() {
		
		Customer customer1 = daoCustomerJpa.save(new Customer(null,"jean"  , "Aimare", "pwd1"));
		Customer customer2 = daoCustomerJpa.save(new Customer(null,"jean"  , "Bon", "pwd1"));
		daoCustomerJpa.save(new Customer(null,"alain"  , "Therieur", "pwd2"));
		daoCustomerJpa.save(new Customer(null,"alex"  , "Therieur", "pwd2"));
		
		Compte compteAa = new Compte(null,"compte_Aa" , 70.0);
		compteAa.setCustomer(customer1);
		compteAa = daoCompteJpa.save(compteAa);
		
		Operation op1CompteA = daoOperationJpa.save(
	    		new Operation(null,-3.2 , "achat bonbons" , new Date() , compteAa));
		
		Operation op2CompteA = daoOperationJpa.save(
	    		new Operation(null,-3.0 , "achat croissants" , new Date() , compteAa));
		
		Compte compteBbb = new Compte(null,"compte_Bbb" , 80.0);
		compteBbb.setCustomer(customer1);
		compteBbb = daoCompteJpa.save(compteBbb);
		
		Compte compteCc = new Compte(null,"compte_Cc" , -70.0);
		compteCc.setCustomer(customer2);
		compteCc = daoCompteJpa.save(compteCc);
		
		Compte compteDd =new Compte(null,"compte_Dd" , -80.0);
		compteDd.setCustomer(customer2);
		compteDd = daoCompteJpa.save(compteDd);
		
    	daoCompteJpa.save(new Compte(null,"compte_EEe" , 20.0));
    	
    	daoDeviseJpa.save(new Devise("EUR","Euro" , 1.0));
    	daoDeviseJpa.save(new Devise("USD","Dollar" , 1.087));
    	daoDeviseJpa.save(new Devise("JPY","Yen" , 158.73));
    	daoDeviseJpa.save(new Devise("GBP","Livre" , 0.86));
    	
    	daoNewsJpa.save(new News(null,"News 1" ));
    	daoNewsJpa.save(new News(null,"News 2" ));
	}

}

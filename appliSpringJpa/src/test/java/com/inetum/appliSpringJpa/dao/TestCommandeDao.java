package com.inetum.appliSpringJpa.dao;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.inetum.appliSpringJpa.entity.Commande;
import com.inetum.appliSpringJpa.entity.LigneCommande;
import com.inetum.appliSpringJpa.entity.Produit;

@SpringBootTest // classe interprétée par JUnit et SpringBoot
//@ActiveProfiles({"h2"}) //application-h2.properties
public class TestCommandeDao {
	
	Logger logger = LoggerFactory.getLogger(TestCommandeDao.class);
	
	@Autowired 
	private DaoCommande daoCommandeJpa;
	
	@Autowired 
	private DaoLigneCommande daoLigneCommandeJpa;
	
	@Autowired 
	private DaoProduit daoProduitJpa;
	
	@Test
	public void testCommandes() {
		Produit produitA = daoProduitJpa.insert(new Produit(null,"produit_A" , 50.0));
		Produit produitB = daoProduitJpa.insert(new Produit(null,"produit_B" , 60.0));
		Produit produitC = daoProduitJpa.insert(new Produit(null,"produit_C" , 70.0));
    	
		logger.debug("liste produits=" + daoProduitJpa.findAll());
		/*
		//V1 (sans Commande.addLigne(produit,qte):
		Commande cmde1 = daoCommandeJpa.insert(new Commande(null,new Date() ));
		LigneCommande ligne1Cmde1 = new LigneCommande(cmde1,1,produitA,2);
		LigneCommande ligne1Cmde2 = new LigneCommande(cmde1,2,produitB,1);
		cmde1.getMapLignesCommande().put(1, ligne1Cmde1);
		cmde1.getMapLignesCommande().put(2, ligne1Cmde2);
		daoCommandeJpa.update(cmde1);
		*/
		
		//V2 (avec Commande.addLigne(produit,qte):
		Commande cmde1 = daoCommandeJpa.insert(new Commande(null,new Date() ));
		cmde1.addLigne(produitA, 2); cmde1.addLigne(produitA, 1);
		daoCommandeJpa.update(cmde1);
		
		Commande cmde2 = daoCommandeJpa.insert(new Commande(null,new Date() ));
		cmde2.addLigne(produitA, 1); cmde2.addLigne(produitC, 3);
		daoCommandeJpa.update(cmde2);
    		
		logger.debug("liste commandes=" + daoCommandeJpa.findAll());
		
	}

}

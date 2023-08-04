package com.inetum.appliSpringWeb.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.inetum.appliSpringWeb.entity.Compte;
import com.inetum.appliSpringWeb.entity.Operation;

@SpringBootTest // classe interprétée par JUnit et SpringBoot
//@ActiveProfiles({"oracle"}) //pour prendre en compte application-oracle.properties
@ActiveProfiles({"perf", "profil2QueJaime"})
public class TestServiceCompte {
	
	Logger logger = LoggerFactory.getLogger(TestServiceCompte.class);
	
	@Autowired
	private ServiceCompte  serviceCompte; //à tester
	
	@Test
	public void testBonTransfert() {
		Compte cptA = serviceCompte.saveOrUpdate(new Compte(null,"compteA" , 50.0));
		Compte cptB = serviceCompte.saveOrUpdate(new Compte(null,"compteB" , 100.0));
		logger.trace("avant bon virement: cptA = " + cptA.getSolde() 
		                           + " et cptB = " + cptB.getSolde());
		serviceCompte.transferer(20, cptA.getNumero(), cptB.getNumero());
		Compte cptA_apres = serviceCompte.rechercherCompteAvecOperationsParNumero(cptA.getNumero());
		Compte cptB_apres = serviceCompte.rechercherCompteAvecOperationsParNumero(cptB.getNumero());
		logger.trace("apres bon virement: cptA_apres = " + cptA_apres.getSolde() 
                                   + " et cptB_apres = " + cptB_apres.getSolde());
		assertEquals(cptA.getSolde() - 20 , cptA_apres.getSolde() , 0.0001);
		assertEquals(cptB.getSolde() + 20 , cptB_apres.getSolde() , 0.0001);
		
		logger.trace("pour le fun, on affiche la liste des operations de cptA_apres:");
		for(Operation op : cptA_apres.getOperations()) {
			logger.trace("\t" + op.toString());
		}
	}
	
	@Test
	public void testMauvaisTransfert() {
		Compte cptA = serviceCompte.saveOrUpdate(new Compte(null,"compteAa" , 50.0));
		Compte cptB = serviceCompte.saveOrUpdate(new Compte(null,"compteBb" , 100.0));
		logger.trace("avant mauvais virement: cptA = " + cptA.getSolde() 
		                           + " et cptB = " + cptB.getSolde());
		try {
			serviceCompte.transferer(20, cptA.getNumero(), -2L); 
			//-2 = numero d'un compte à créditer qui n'existe pas
		} catch (Exception e) {
			logger.trace("exception normale en cas de mauvais virement "
					     + e.getMessage());
		}
		Compte cptA_apres = serviceCompte.searchById(cptA.getNumero());
		Compte cptB_apres = serviceCompte.searchById(cptB.getNumero());
		logger.trace("apres mauvais virement: cptA_apres = " + cptA_apres.getSolde() 
                                       + " et cptB_apres = " + cptB_apres.getSolde());
		assertEquals(cptA.getSolde()  , cptA_apres.getSolde() , 0.0001);
		assertEquals(cptB.getSolde()  , cptB_apres.getSolde() , 0.0001);
	}
	
	@Test
	public void testTransfertAnnuleSiPasAssezArgent() {
		Compte cptA = serviceCompte.saveOrUpdate(new Compte(null,"compteAaa" , 50.0));
		Compte cptB = serviceCompte.saveOrUpdate(new Compte(null,"compteBbb" , 100.0));
		logger.trace("avant virement sans assez argent: cptA = " + cptA.getSolde() 
		                           + " et cptB = " + cptB.getSolde());
		try {
			serviceCompte.transferer(2000, cptA.getNumero(), cptB.getNumero()); 
		} catch (Exception e) {
			logger.trace("exception normale en cas de virement annulé "
					     + e.getMessage());
		}
		Compte cptA_apres = serviceCompte.searchById(cptA.getNumero());
		Compte cptB_apres = serviceCompte.searchById(cptB.getNumero());
		logger.trace("apres virement sans assez argent: cptA_apres = " + cptA_apres.getSolde() 
                                       + " et cptB_apres = " + cptB_apres.getSolde());
		assertEquals(cptA.getSolde()  , cptA_apres.getSolde() , 0.0001);
		assertEquals(cptB.getSolde()  , cptB_apres.getSolde() , 0.0001);
	}

}

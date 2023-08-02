package com.inetum.appliSpringWeb.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.inetum.appliSpringWeb.entity.Compte;

@SpringBootTest // classe interprétée par JUnit et SpringBoot
//@ActiveProfiles({"oracle"}) //pour prendre en compte application-oracle.properties
public class TestServiceCompte {
	
	Logger logger = LoggerFactory.getLogger(TestServiceCompte.class);
	
	@Autowired
	private ServiceCompte  serviceCompte; //à tester
	
	@Test
	public void testBonTransfert() {
		Compte cptA = serviceCompte.sauvegarderCompte(new Compte(null,"compteA" , 50.0));
		Compte cptB = serviceCompte.sauvegarderCompte(new Compte(null,"compteB" , 100.0));
		logger.trace("avant bon virement: cptA = " + cptA.getSolde() 
		                           + " et cptB = " + cptB.getSolde());
		serviceCompte.transferer(20, cptA.getNumero(), cptB.getNumero());
		Compte cptA_apres = serviceCompte.rechercherCompteParNumero(cptA.getNumero());
		Compte cptB_apres = serviceCompte.rechercherCompteParNumero(cptB.getNumero());
		assertEquals(cptA.getSolde() - 20 , cptA_apres.getSolde() , 0.0001);
		assertEquals(cptB.getSolde() + 20 , cptB_apres.getSolde() , 0.0001);
		logger.trace("apres bon virement: cptA_apres = " + cptA_apres.getSolde() 
                                    + " et cptB_apres = " + cptB_apres.getSolde());
	}

}

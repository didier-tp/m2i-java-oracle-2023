package com.inetum.appliSpringJpa.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.inetum.appliSpringJpa.entity.Compte;

@SpringBootTest // classe interprétée par JUnit et SpringBoot
public class TestCompteDao {
	
	Logger logger = LoggerFactory.getLogger(TestCompteDao.class);
	
	@Autowired //pour initialisation daoCompte
	//qui va référencer un composant Spring existant compatible
	//avec l'interface DaoCompte (DaoCompteJpa avec@Repository)
	private DaoCompte daoCompteJpa;
	
	@Test
	public void experimentationPersistantDetache() {
		//création d'un compte avec solde initial = 50
		
		//relire le compte pour modifier aussitôt son solde en mémoire avec "-20 euros"
		//à l'état détaché et à l'état persistant
		
		Compte compteCc = daoCompteJpa.insert(new Compte(null,"compte_Cc" , 50.0));
		Compte compteCcReluDetache = daoCompteJpa.findById(compteCc.getNumero());
		logger.debug("compteCcReluDetache="+compteCcReluDetache);
		//retirer 20 euros à l'état détaché:
		compteCcReluDetache.setSolde(compteCcReluDetache.getSolde() - 20);
		Compte compteCcEncoreRelu = daoCompteJpa.findById(compteCc.getNumero());
		logger.debug("compteCcEncoreRelu="+compteCcEncoreRelu);
		
		Compte compteCc2 = daoCompteJpa.insert(new Compte(null,"compte_Cc" , 50.0));
		Compte compteCc2ReluDetache = daoCompteJpa.findById(compteCc2.getNumero());
		logger.debug("compteCc2ReluDetache="+compteCc2ReluDetache);
		//retirer 20 euros à l'état persistant:
		daoCompteJpa.trouverEtDebiter(compteCc2.getNumero(), 20); 
		Compte compteCc2EncoreRelu = daoCompteJpa.findById(compteCc2.getNumero());
		logger.debug("compteCc2EncoreRelu="+compteCc2EncoreRelu);
		
		
	}
	
	@Test
	public void testQueries() {
		daoCompteJpa.insert(new Compte(null,"compte_A" , 50.0));
    	daoCompteJpa.insert(new Compte(null,"compte_B" , 80.0));
    	daoCompteJpa.insert(new Compte(null,"compte_C" , 250.0));
    	daoCompteJpa.insert(new Compte(null,"compte_D" , 540.0));
    			
		List<Compte> comptesAvecSoldeMini100 = daoCompteJpa.findBySoldeMini(100.0);
		assertTrue(comptesAvecSoldeMini100.size()>=2);
		
		logger.trace("comptesAvecSoldeMini100="+comptesAvecSoldeMini100);
		
		List<Compte> comptesAvecSoldeMaxi100 = daoCompteJpa.findBySoldeMaxi(100.0);
		assertTrue(comptesAvecSoldeMaxi100.size()>=2);
		
		logger.trace("comptesAvecSoldeMaxi100="+comptesAvecSoldeMaxi100);
	}

}

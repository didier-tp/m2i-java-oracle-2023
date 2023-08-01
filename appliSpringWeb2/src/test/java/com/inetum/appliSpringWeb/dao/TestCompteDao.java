package com.inetum.appliSpringWeb.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.inetum.appliSpringWeb.entity.Compte;

@SpringBootTest // classe interprétée par JUnit et SpringBoot
@ActiveProfiles({"oracle"}) //pour prendre en compte application-oracle.properties
public class TestCompteDao {
	
	Logger logger = LoggerFactory.getLogger(TestCompteDao.class);
	
	@Autowired //pour initialisation daoCompte
	//qui va référencer un composant Spring existant compatible
	//avec l'interface DaoCompte (DaoCompteJpa avec@Repository)
	private DaoCompte daoCompteJpa;
	
	
	@Test
	public void testQueries() {
		daoCompteJpa.save(new Compte(null,"compte_A" , 50.0));
    	daoCompteJpa.save(new Compte(null,"compte_B" , 80.0));
    	daoCompteJpa.save(new Compte(null,"compte_C" , 250.0));
    	daoCompteJpa.save(new Compte(null,"compte_D" , 540.0));
    			
		List<Compte> comptesAvecSoldeMini100 = daoCompteJpa.findBySoldeGreaterThanEqual(100.0);
		assertTrue(comptesAvecSoldeMini100.size()>=2);
		
		logger.trace("comptesAvecSoldeMini100="+comptesAvecSoldeMini100);
		
		List<Compte> comptesAvecSoldeMaxi100 = daoCompteJpa.findBySoldeLessThanEqual(100.0);
		assertTrue(comptesAvecSoldeMaxi100.size()>=2);
		
		logger.trace("comptesAvecSoldeMaxi100="+comptesAvecSoldeMaxi100);
	}

}

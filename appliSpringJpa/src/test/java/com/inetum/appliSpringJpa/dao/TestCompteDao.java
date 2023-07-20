package com.inetum.appliSpringJpa.dao;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.inetum.appliSpringJpa.entity.Compte;

@SpringBootTest // classe interprétée par JUnit et SpringBoot
public class TestCompteDao {
	
	@Autowired //pour initialisation daoCompte
	//qui va référencer un composant Spring existant compatible
	//avec l'interface DaoCompte (DaoCompteJpa avec@Repository)
	private DaoCompte daoCompteJpa;
	
	@Test
	public void testQueries() {
		daoCompteJpa.insert(new Compte(null,"compte_A" , 50.0));
    	daoCompteJpa.insert(new Compte(null,"compte_B" , 80.0));
    	daoCompteJpa.insert(new Compte(null,"compte_C" , 250.0));
    	daoCompteJpa.insert(new Compte(null,"compte_D" , 540.0));
    			
		List<Compte> comptesAvecSoldeMini100 = daoCompteJpa.findBySoldeMini(100.0);
		System.err.println("comptesAvecSoldeMini100="+comptesAvecSoldeMini100);
		
		List<Compte> comptesAvecSoldeMaxi100 = daoCompteJpa.findBySoldeMaxi(100.0);
		System.err.println("comptesAvecSoldeMaxi100="+comptesAvecSoldeMaxi100);
	}

}

package com.inetum.appliSpringWeb.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.inetum.appliSpringWeb.entity.Compte;
import com.inetum.appliSpringWeb.entity.Customer;
import com.inetum.appliSpringWeb.entity.Operation;

@SpringBootTest // classe interprétée par JUnit et SpringBoot
//@ActiveProfiles({"oracle"}) //pour prendre en compte application-oracle.properties
public class TestCompteDao {
	
	Logger logger = LoggerFactory.getLogger(TestCompteDao.class);
	
	@Autowired //pour initialisation daoCompte
	//qui va référencer un composant Spring existant compatible
	//avec l'interface DaoCompte (DaoCompteJpa avec@Repository)
	private DaoCompte daoCompteJpa;
	
	@Autowired
	private DaoOperation daoOperationJpa;
	
	@Autowired
	private DaoCustomer daoCustomerJpa;
	
	@Test 
	public void testComptesAvecCustomer() {
		
		Customer c1 = daoCustomerJpa.save(new Customer(null,"prenom1" , "nom1" , "pwd1"));
		Customer c2 = daoCustomerJpa.save(new Customer(null,"prenom2" , "nom2" , "pwd2"));
		
		Compte compteAdeC1 = new Compte(null,"compteAdeC1" , 70.0);
		compteAdeC1.setCustomer(c1);
		Compte compteBdeC1 = new Compte(null,"compteBdeC1" , 80.0);
		compteBdeC1.setCustomer(c1);
		
		compteAdeC1 = daoCompteJpa.save(compteAdeC1);
		compteBdeC1 = daoCompteJpa.save(compteBdeC1);
		
		Compte compte1deC2 = new Compte(null,"compte1deC2" , 40.0);
		compte1deC2.setCustomer(c2);
		compte1deC2 = daoCompteJpa.save(compte1deC2);
		
		List<Compte> listeComptesDeC1 = daoCompteJpa.findByCustomerId(c1.getId());
		assertTrue(listeComptesDeC1.size()==2);
		logger.debug("comptes de c1 :");
		for(Compte cpt : listeComptesDeC1) {
    		logger.debug("\t" + cpt.toString());
    	}
		
	}
	
	@Test 
	public void testCompteAvecOperations() {
         Compte compteAa = daoCompteJpa.save(new Compte(null,"compte_Aa" , 70.0));
		
		Operation op1CompteA = daoOperationJpa.save(
	    		new Operation(null,-3.2 , "achat bonbons" , new Date() , compteAa));
		Operation op2CompteA = daoOperationJpa.save(
	    		new Operation(null,-3.3 , "achat gateau" , new Date() , compteAa));
		
		Compte compteBb = daoCompteJpa.save(new Compte(null,"compte_Bbb" , 80.0));
    	
    	Operation op1CompteB = daoOperationJpa.save(
	    		new Operation(null,-1.3 , "achat raisonnable" , new Date() , compteBb));
    	
    	//Compte compteARelu = daoCompteJpa.findById(compteAa.getNumero()).orElse(null);
    	Compte compteARelu = daoCompteJpa.findByIdWithOperations(compteAa.getNumero()).orElse(null);
    	logger.debug("compteARelu=" + compteARelu.toString());
    	
    	logger.debug("operations du compteARelu :");
    	for(Operation op : compteARelu.getOperations()) {
    		logger.debug("\t" + op.toString());
    	}
	}
	
	
	@Test
	public void testQueries() {
		daoCompteJpa.save(new Compte(null,"compte_A" , 50.0));
    	daoCompteJpa.save(new Compte(null,"compte_B" , 80.0));
    	daoCompteJpa.save(new Compte(null,"compte_C" , 250.0));
    	daoCompteJpa.save(new Compte(null,"compte_D" , 540.0));
    			
		//List<Compte> comptesAvecSoldeMini100 = daoCompteJpa.findBySoldeGreaterThanEqual(100.0);
		List<Compte> comptesAvecSoldeMini100 = daoCompteJpa.findBySoldeMini(100.0);
		assertTrue(comptesAvecSoldeMini100.size()>=2);
		
		logger.trace("comptesAvecSoldeMini100="+comptesAvecSoldeMini100);
		
		List<Compte> comptesAvecSoldeMaxi100 = daoCompteJpa.findBySoldeLessThanEqual(100.0);
		assertTrue(comptesAvecSoldeMaxi100.size()>=2);
		
		logger.trace("comptesAvecSoldeMaxi100="+comptesAvecSoldeMaxi100);
	}

}

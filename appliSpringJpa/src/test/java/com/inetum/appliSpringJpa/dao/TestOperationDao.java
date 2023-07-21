package com.inetum.appliSpringJpa.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.inetum.appliSpringJpa.entity.Compte;
import com.inetum.appliSpringJpa.entity.Operation;

@SpringBootTest // classe interprétée par JUnit et SpringBoot
public class TestOperationDao {
	
	Logger logger = LoggerFactory.getLogger(TestOperationDao.class);
	
	@Autowired 
	private DaoCompte daoCompteJpa;
	
	@Autowired 
	private DaoOperation daoOperationJpa;
	
	@Test
	public void testSensSecondaire() {
		
		/*
		//sens des liaisons qui ne marche pas bien (pas bien enregistré)
		Compte compteAA = new Compte(null,"compte_AA" , 50.0);
		Operation op1 = new Operation(null,-3.2 , "achat cahier" , new Date());
		op1.setCompte(compteAA); compteAA.addOperation(op1);
		Operation op2 = new Operation(null,-3.2 , "achat cahier" , new Date() , compteAA);
		compteAA.addOperation(op2);
		daoCompteJpa.insert(compteAA);//sauvegarde demandée du coté mappedBy=
		                              //CA NE MARCHE PAS BIEN SAUF SI CASCADE
		*/
		
		Compte compteAA = daoCompteJpa.insert(new Compte(null,"compte_AA" , 50.0));
		Operation op1 = new Operation(null,-3.2 , "achat cahier" , new Date());
		op1.setCompte(compteAA);
		daoOperationJpa.insert(op1);//sauvegarde demandée du principal (pas de mappebBy)
		                            //CA MARCHE TRES TRES TRES TRES TRES BIEN
		
		Operation op2 = new Operation(null,-3.2 , "achat cahier" , new Date() , compteAA);
		daoOperationJpa.insert(op2);
		
		Compte compteAARelu = daoCompteJpa.findCompteWithOperationsById(compteAA.getNumero());
		assertTrue(compteAARelu.getOperations().size()==2);

	}
	
	
	@Test
	public void testCompteEtOperation() {
		Compte compteA = daoCompteJpa.insert(new Compte(null,"compte_A" , 50.0));
		Compte compteB = daoCompteJpa.insert(new Compte(null,"compte_B" , 70.0));
		
    	Operation op1CompteA = daoOperationJpa.insert(
    		new Operation(null,-3.2 , "achat bonbons" , new Date() , compteA));
    	
    	Operation op1CompteB = daoOperationJpa.insert(
        		new Operation(null,-5.2 , "achat gateaux" , new Date() , compteB));
    	
    	Operation op2CompteA = daoOperationJpa.insert(
        		new Operation(null,-13.2 , "achat jouet" , new Date() , compteA));
        	
        	Operation op2CompteB = daoOperationJpa.insert(
            		new Operation(null,-1.2 , "orange" , new Date() , compteB));
    	
    	Operation op1CompteARelu = daoOperationJpa.findById(op1CompteA.getIdOp());
    	
    	logger.debug("op1CompteARelu=" + op1CompteARelu);
    	assertEquals(-3.2 , op1CompteARelu.getMontant() , 0.00001);
    	
    	Compte compteARelu = daoCompteJpa.findCompteWithOperationsById(compteA.getNumero());
    	
    	logger.debug("operations du compteA:");
    	for(Operation op : compteARelu.getOperations() ) {
    		logger.debug("\t" + op.toString());
    	}

	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}

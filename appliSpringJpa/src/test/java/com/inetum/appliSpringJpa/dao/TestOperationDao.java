package com.inetum.appliSpringJpa.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
	public void testCompteEtOperation() {
		Compte compteA = daoCompteJpa.insert(new Compte(null,"compte_A" , 50.0));
    	Operation op1CompteA = daoOperationJpa.insert(
    		new Operation(null,-3.2 , "achat bonbons" , new Date() , compteA));
    	
    	Operation op1CompteARelu = daoOperationJpa.findById(op1CompteA.getIdOp());
    	logger.debug("op1CompteARelu=" + op1CompteARelu);
    	assertEquals(-3.2 , op1CompteARelu.getMontant() , 0.00001);

	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}

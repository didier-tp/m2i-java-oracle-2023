package com.inetum.appliSpringJpa.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.inetum.appliSpringJpa.entity.Client;
import com.inetum.appliSpringJpa.entity.Compte;
import com.inetum.appliSpringJpa.entity.Operation;

@SpringBootTest // classe interprétée par JUnit et SpringBoot
public class TestClientDao {
	
	Logger logger = LoggerFactory.getLogger(TestClientDao.class);
	
	@Autowired 
	private DaoCompte daoCompteJpa;
	
	@Autowired 
	private DaoClient daoClientJpa;
	
	@Test
	public void testClientEtCompte() {
		Client clientX = daoClientJpa.insert(new Client(null,"jean" , "Aimare"));
		Client clientY = daoClientJpa.insert(new Client(null,"axelle" , "Aire"));
		
    	Compte compteA = daoCompteJpa.insert(
    			new Compte(null,"compte_A" , 50.0));
    	
    	
    	
    	Compte compteB = daoCompteJpa.insert(
    			new Compte(null,"compte_B" , 70.0));
        
    	
      	
    	

	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}

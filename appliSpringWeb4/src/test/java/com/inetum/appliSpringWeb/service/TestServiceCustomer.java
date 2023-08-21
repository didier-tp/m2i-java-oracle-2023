package com.inetum.appliSpringWeb.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.inetum.appliSpringWeb.entity.Compte;
import com.inetum.appliSpringWeb.entity.Customer;

@SpringBootTest // classe interprétée par JUnit et SpringBoot
//@ActiveProfiles({"oracle"}) //pour prendre en compte application-oracle.properties
public class TestServiceCustomer {
	Logger logger = LoggerFactory.getLogger(TestServiceCustomer.class);
	
	@Autowired
	private ServiceCustomer  serviceCustomer; //à tester
	
	@Autowired
	private ServiceCompte  serviceCompte; //service annexe pour aider à tester
	
	@Test
	public void testCrudQueJaime() {
		Customer c1 = serviceCustomer.saveOrUpdate(
				new Customer(null,"prenom1" , "nom1" , "pwd1"));
		Customer c2 = serviceCustomer.saveOrUpdate(
				new Customer(null,"prenom2" , "nom2" , "pwd2"));
		
		Compte compteAdeC1 = new Compte(null,"compteAdeC1" , 70.0);
		compteAdeC1.setCustomer(c1);
		Compte compteBdeC1 = new Compte(null,"compteBdeC1" , 80.0);
		compteBdeC1.setCustomer(c1);
		
		compteAdeC1 = serviceCompte.saveOrUpdate(compteAdeC1);
		compteBdeC1 = serviceCompte.saveOrUpdate(compteBdeC1);
		
		Compte compte1deC2 = new Compte(null,"compte1deC2" , 40.0);
		compte1deC2.setCustomer(c2);
		compte1deC2 = serviceCompte.saveOrUpdate(compte1deC2);
		
		Customer c1ReluAvecSesComptes = serviceCustomer.rechercherCustomerAvecComptesParNumero(c1.getId());
		assertTrue(c1ReluAvecSesComptes.getComptes().size()==2);
		logger.debug("c1ReluAvecSesComptes:" + c1ReluAvecSesComptes.toString());
		logger.debug("comptes de c1 :");
		for(Compte cpt : c1ReluAvecSesComptes.getComptes()) {
    		logger.debug("\t" + cpt.toString());
    	}
		
	}
	
	@Test
	public void testSurPassword() {
		Customer c1 = serviceCustomer.saveOrUpdate(
				new Customer(null,"prenom1" , "nom1" , "pwd1"));
		boolean pwdNotOK = serviceCustomer.checkCustomerPassword(c1.getId(), "wrongPwd");
		assertFalse(pwdNotOK);
		boolean pwdOK = serviceCustomer.checkCustomerPassword(c1.getId(), "pwd1");
		assertTrue(pwdOK);
		String resetPwd = serviceCustomer.resetCustomerPassword(c1.getId()); 
		logger.trace("reset password =" + resetPwd);
		pwdOK = serviceCustomer.checkCustomerPassword(c1.getId(), resetPwd);
		assertTrue(pwdOK);
	}
	
	@Test
	public void testFindSpecifique() {
		Customer c1 = serviceCustomer.saveOrUpdate(
				new Customer(null,"jean" , "Bon" , "pwd1"));
		Customer c1Bis = serviceCustomer.saveOrUpdate(
				new Customer(null,"jean" , "Bon" , "pwd1Bis"));
		List<Customer> customers = serviceCustomer.rechercherCustomerSelonPrenomEtNom("jean" , "Bon");
		assertTrue(customers.size()==2);
		logger.trace("pour jean Bon, customers=" + customers);
	}
	
	

}

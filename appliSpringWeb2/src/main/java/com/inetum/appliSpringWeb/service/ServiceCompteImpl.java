package com.inetum.appliSpringWeb.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inetum.appliSpringWeb.dao.DaoCompte;
import com.inetum.appliSpringWeb.dao.DaoOperation;
import com.inetum.appliSpringWeb.entity.Compte;
import com.inetum.appliSpringWeb.entity.Operation;
import com.inetum.appliSpringWeb.exception.BankException;

//@Component
@Service
@Transactional //ici (sur une classe de Service) en tant que bonne pratique
public class ServiceCompteImpl implements ServiceCompte {
	
	Logger logger = LoggerFactory.getLogger(ServiceCompteImpl.class);
	
	@Autowired
	private DaoCompte daoCompte; //dao principal
	
	@Autowired
	private DaoOperation daoOperation; //dao secondaire/annexe

	@Override
	//@Transactional(propagation = Propagation.REQUIRED) //par défaut
	//@Transactional()  //maintenant au dessus de la classe entière
	public void transferer(double montant, long numCptDeb, long numCptCred) throws BankException {
		try {
			/*
			Compte compteDeb = daoCompte.findById(numCptDeb).get();
			compteDeb.setSolde(compteDeb.getSolde() - montant);
			daoCompte.save(compteDeb); //.save() facultatif à l'état persistant
			*/
			this.debiterCompte(numCptDeb, montant,
					          "debit suite au virement vers le compte " + numCptCred);
			/*
			Compte compteCred = daoCompte.findById(numCptCred).get();
			compteCred.setSolde(compteCred.getSolde() + montant);
			daoCompte.save(compteCred); //.save() facultatif à l'état persistant
			*/
			this.crediterCompte(numCptCred, montant,
			          "credit suite au virement depuis compte " + numCptDeb);
			
			//si @Transaction sur classe de service (ce qui est le cas général , bonne pratique)
			//toutes les entités remontées par les DAOs à coup de .findBy...()
			//sont à l'état persistant(es)
			//et donc .save() automatique en cas de transaction réussie (sans exception)
			//et pas de "LazyException" à l'intérieur de la méthode du service
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("echec virement" , e);
			throw new BankException("echec virement" , e);
		}
	};

	@Override
	public Compte rechercherCompteParNumero(long numeroCompte) {
		return daoCompte.findById(numeroCompte).orElse(null);
	}

	@Override
	public List<Compte> rechercherComptesDuClient(long numeroCustomer) {
		return daoCompte.findByCustomerId(numeroCustomer);
	}

	@Override
	public Compte sauvegarderCompte(Compte compte) {
		return daoCompte.save(compte);
	}

	@Override
	public void supprimerCompte(long numeroCompte) {
		daoCompte.deleteById(numeroCompte);
	}

	@Override
	public boolean verifierExistanceCompte(long numeroCompte) {
		return daoCompte.existsById(numeroCompte);
	}

	@Override
	public void debiterCompte(long numeroCompte, double montant, String message) {
		Compte compteDeb = daoCompte.findById(numeroCompte).get();
		compteDeb.setSolde(compteDeb.getSolde() - montant);
		daoCompte.save(compteDeb); //.save() facultatif à l'état persistant (effet de @Transactional)
		
		Operation opDebit = daoOperation.save(
	    		new Operation(null,-montant , message , new Date() , compteDeb));
	}

	@Override
	public void crediterCompte(long numeroCompte, double montant, String message) {
		Compte compteCred = daoCompte.findById(numeroCompte).get();
		compteCred.setSolde(compteCred.getSolde() + montant);
		daoCompte.save(compteCred); //.save() facultatif à l'état persistant (effet de @Transactional)
		
		Operation opCredit = daoOperation.save(
	    		new Operation(null,montant , message , new Date() , compteCred));
		
	}

}

package com.inetum.appliSpringWeb.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inetum.appliSpringWeb.dao.DaoCompte;
import com.inetum.appliSpringWeb.dao.DaoOperation;
import com.inetum.appliSpringWeb.dto.CompteDto;
import com.inetum.appliSpringWeb.entity.Compte;
import com.inetum.appliSpringWeb.entity.Operation;
import com.inetum.appliSpringWeb.exception.BankException;

//@Component
@Service
@Transactional //ici (sur une classe de Service) en tant que bonne pratique
public class ServiceCompteImpl 
    extends AbstractGenericService<Compte,Long,CompteDto>
    implements ServiceCompte {
	
	@Override
	public CrudRepository<Compte,Long> getDao() {
		return this.daoCompte;
	}
	
	@Override
	public Class<CompteDto> getDtoClass() {
		return CompteDto.class;
	}
	
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
			//logger.error("echec virement" , e);
			logger.error("echec virement" + e.getMessage());
			throw new BankException("echec virement" , e);
		}
	};

	

	@Override
	public List<Compte> rechercherComptesDuClient(long numeroCustomer) {
		return daoCompte.findByCustomerId(numeroCustomer);
	}

	


	@Override
	public void debiterCompte(long numeroCompte, double montant, String message) {
		Compte compteDeb = daoCompte.findById(numeroCompte).get();
		double nouveauSoldeTheoriqueApresDebit = compteDeb.getSolde() - montant;
		if(nouveauSoldeTheoriqueApresDebit >= Compte.getDecouvertAutorise()) {
			compteDeb.setSolde(nouveauSoldeTheoriqueApresDebit);
			daoCompte.save(compteDeb); //.save() facultatif à l'état persistant (effet de @Transactional)
			
			Operation opDebit = daoOperation.save(
		    		new Operation(null,-montant , message , new Date() , compteDeb));
		}else {
			throw new BankException("solde insuffisant vis à vis du découvertAutorise=" +
			  Compte.getDecouvertAutorise() + " pour effectuer un debit de " + montant);
		}
	}

	@Override
	public void crediterCompte(long numeroCompte, double montant, String message) {
		Compte compteCred = daoCompte.findById(numeroCompte).get();
		compteCred.setSolde(compteCred.getSolde() + montant);
		daoCompte.save(compteCred); //.save() facultatif à l'état persistant (effet de @Transactional)
		
		Operation opCredit = daoOperation.save(
	    		new Operation(null,montant , message , new Date() , compteCred));
		
	}

	@Override
	public Compte rechercherCompteAvecOperationsParNumero(long numeroCompte) {
		return daoCompte.findByIdWithOperations(numeroCompte).orElse(null);
	}

	@Override
	public List<Operation> operationsDuCompteQueJaime(long numeroCompte) {
		Compte cpt =  daoCompte.findByIdWithOperations(numeroCompte).get();
		return cpt.getOperations();
	}

	
	@Override
	public List<Compte> rechercherSelonSoldeMini(Double soldeMini) {
		return daoCompte.findBySoldeGreaterThanEqual(soldeMini);
	}



	

}

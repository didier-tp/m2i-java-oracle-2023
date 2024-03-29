package com.inetum.appliSpringWeb.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inetum.appliSpringWeb.converter.DtoConverter;
import com.inetum.appliSpringWeb.converter.GenericConverter;
import com.inetum.appliSpringWeb.dao.DaoCompte;
import com.inetum.appliSpringWeb.dao.DaoCustomer;
import com.inetum.appliSpringWeb.dao.DaoOperation;
import com.inetum.appliSpringWeb.dto.CompteDto;
import com.inetum.appliSpringWeb.dto.CompteDtoEx;
import com.inetum.appliSpringWeb.dto.CompteDtoEx2;
import com.inetum.appliSpringWeb.entity.Compte;
import com.inetum.appliSpringWeb.entity.Customer;
import com.inetum.appliSpringWeb.entity.Operation;
import com.inetum.appliSpringWeb.exception.BankException;
import com.inetum.appliSpringWeb.exception.NotFoundException;

//@Component
@Service
@Transactional //ici (sur une classe de Service) en tant que bonne pratique
public class ServiceCompteImpl 
    extends AbstractGenericService<Compte,Long,CompteDto>
    implements ServiceCompte {
	
	private DtoConverter dtoConverter = new DtoConverter();//for specific convert
	
	@Autowired
	private DaoCustomer daoCustomer;//dao annexe/secondaire
	
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

	@Override
	public CompteDtoEx searchDtoExByIdWithNumClient(long numeroCompte) {
		return (CompteDtoEx) searchDtoByIdWithDetailLevel(numeroCompte,1);
	}

	@Override
	public CompteDtoEx2 searchDtoEx2ByIdWithClientAndOperations(long numeroCompte) {
		return (CompteDtoEx2) searchDtoByIdWithDetailLevel(numeroCompte,2);
	}
	
	@Override
	public CompteDto searchDtoByIdWithDetailLevel(long numeroCompte,Integer detailLevel)throws NotFoundException {
		Compte entityCompte = searchById(numeroCompte);
		if(entityCompte==null) 
			throw new NotFoundException("no entity compte found with numero="+numeroCompte);
		return convertWithDetailLevel(entityCompte,detailLevel);
	}
	

	public CompteDto convertWithDetailLevel(Compte entityCompte,Integer detailLevel) {
		if(detailLevel==null) detailLevel=0;
		switch(detailLevel) {
		    case 2:
		    	return dtoConverter.compteToCompteDtoEx2(entityCompte);
		    case 1:
		    	return dtoConverter.compteToCompteDtoEx(entityCompte);
			case 0:
			default:
				return dtoConverter.compteToCompteDto(entityCompte);
		}
	}

	@Override
	public CompteDtoEx saveOrUpdateCompteDtoEx(CompteDtoEx compteDtoEx) {
		// en entree : CompteDtoEx avec .numeroClient
		// a transformer en compteEntity relié à customerEntity si .numeroClient pas null
		Compte compteEntity = GenericConverter.map(compteDtoEx, Compte.class);
		//Compte compteEntity = dtoConverter.compteDtoToCompte(compteDtoEx); //solution 2
		if(compteDtoEx.getNumeroClient()!=null) {
			Customer customerEntity = daoCustomer.findById(compteDtoEx.getNumeroClient()).get();
			compteEntity.setCustomer(customerEntity);
			
		}
		daoCompte.save(compteEntity); //NB: à ce moment là , 
                                      //éventuelle auto_incr de compteEntity.numero
		compteDtoEx.setNumero(compteEntity.getNumero());
		return compteDtoEx; //on retourne le DtoEx sauvegardé
		                    //avec la clef primaire éventuellement autoincrémenté
	}

	@Override
	public List<CompteDtoEx> searchAllDtoEx() {
		return dtoConverter.compteToCompteDtoEx(searchAll());
	}

	

}

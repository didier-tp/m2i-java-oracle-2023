package com.inetum.appliSpringWeb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inetum.appliSpringWeb.dao.DaoCompte;
import com.inetum.appliSpringWeb.entity.Compte;

//@Component
@Service
//@Transactional
public class ServiceCompteImpl implements ServiceCompte {
	
	@Autowired
	private DaoCompte daoCompte;

	@Override
	@Transactional
	public void transferer(double montant, long numCptDeb, long numCptCred) {
		Compte compteDeb = daoCompte.findById(numCptDeb).get();
		compteDeb.setSolde(compteDeb.getSolde() - montant);
		daoCompte.save(compteDeb);
		
		Compte compteCred = daoCompte.findById(numCptCred).get();
		compteCred.setSolde(compteCred.getSolde() + montant);
		daoCompte.save(compteCred);
	}

	@Override
	public Compte rechercherCompteParNumero(long numeroCompte) {
		return daoCompte.findById(numeroCompte).orElse(null);
	}

	@Override
	public List<Compte> rechercherComptesDuClient(long numeroCustomer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Compte sauvegarderCompte(Compte compte) {
		return daoCompte.save(compte);
	}

	@Override
	public void supprimerCompte(long numeroCompte) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean verifierExistanceCompte(long numeroCompte) {
		// TODO Auto-generated method stub
		return false;
	}

}

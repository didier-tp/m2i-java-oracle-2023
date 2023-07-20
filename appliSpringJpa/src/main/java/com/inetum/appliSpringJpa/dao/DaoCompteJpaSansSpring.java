package com.inetum.appliSpringJpa.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.inetum.appliSpringJpa.entity.Compte;
import com.inetum.appliSpringJpa.entity.Employe;

public class DaoCompteJpaSansSpring implements DaoCompte {
	
	private EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


	@Override
	public Compte findById(Long numero) {
		return entityManager.find(Compte.class, numero);
	}

	@Override
	public List<Compte> findAll() {
		return entityManager.createNamedQuery("Compte.findAll", Compte.class)
				            .getResultList();
	}

	@Override
	public List<Compte> findBySoldeMini(double soldeMini) {
		return entityManager
				.createNamedQuery("Compte.findBySoldeMini",Compte.class)
				.setParameter(1,soldeMini)//pour valeur de ?1
				.getResultList();
	}

	@Override
	public List<Compte> findBySoldeMaxi(double soldeMaxi) {
		return entityManager
				.createNamedQuery("Compte.findBySoldeMaxi",Compte.class)
				.setParameter(1,soldeMaxi)//pour valeur de ?1
				.getResultList();
	}

	@Override
	public Compte insert(Compte cpt) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(cpt);// .numero n'est normalement plus null si auto-incr
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw new RuntimeException("echec insert(compte)");
		}
		return cpt; // on retourne l'objet modifié (avec .numro non null)
	}

	@Override
	public void update(Compte cpt) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(cpt);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw new RuntimeException("echec update(compte)");
		}
	}

	@Override
	public void deleteById(Long num) {
		try {
			entityManager.getTransaction().begin();
			
			Compte compteAsupprimer = entityManager.find(Compte.class, num);
			entityManager.remove(compteAsupprimer);
	
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw new RuntimeException("echec deleteById");
		}
		
	}


	@Override
	public Compte findCompteWithOperationsById(Long numero) {
		// TODO Auto-generated method stub
		return null;
	}

}

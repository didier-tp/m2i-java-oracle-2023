package com.inetum.appliSpringJpa.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.inetum.appliSpringJpa.entity.Commande;



@Repository //pour cette classe de DAO soit prise en charge par Spring
@Transactional //pour demander commit/rollback automatiques
public class DaoCommandeJpa extends DaoGenericJpa<Commande,Long> implements DaoCommande {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	public DaoCommandeJpa() {
		super(Commande.class);
	}

	@Override
	public Commande findByIdwithAllLines(Long numero) {
		return entityManager.createNamedQuery("Commande.findByIdwithAllLines",Commande.class)
				.setParameter(1, numero)
				.getSingleResult();
	}

}

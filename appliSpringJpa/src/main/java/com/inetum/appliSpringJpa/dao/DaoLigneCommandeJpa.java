package com.inetum.appliSpringJpa.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.inetum.appliSpringJpa.entity.LigneCommande;
import com.inetum.appliSpringJpa.entity.LigneCommandePK;



@Repository //pour cette classe de DAO soit prise en charge par Spring
@Transactional //pour demander commit/rollback automatiques
public class DaoLigneCommandeJpa extends DaoGenericJpa<LigneCommande,LigneCommandePK>
      implements DaoLigneCommande {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	public DaoLigneCommandeJpa() {
		super(LigneCommande.class);
	}

}

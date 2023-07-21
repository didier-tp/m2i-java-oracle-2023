package com.inetum.appliSpringJpa.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.inetum.appliSpringJpa.entity.Client;



@Repository //pour cette classe de DAO soit prise en charge par Spring
@Transactional //pour demander commit/rollback automatiques
public class DaoClientJpa extends DaoGenericJpa<Client,Long> implements DaoClient {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	public DaoClientJpa() {
		super(Client.class);
	}
	
	@Override
	public Client findClientWithComptesById(Long numero) {
		return entityManager.createNamedQuery("Client.findClientWithComptesById",Client.class)
				.setParameter(1, numero)
				.getSingleResult();
	}

	

}

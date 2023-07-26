package com.inetum.appliSpringWeb.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.inetum.appliSpringWeb.entity.Operation;

@Repository //pour cette classe de DAO soit prise en charge par Spring
@Transactional //pour demander commit/rollback automatiques
public class DaoOperationJpa extends DaoGenericJpa<Operation,Long> 
                      implements DaoOperation {
	
	@PersistenceContext
	private EntityManager entityManager;

	public DaoOperationJpa() {
		super(Operation.class);
	}

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}
}

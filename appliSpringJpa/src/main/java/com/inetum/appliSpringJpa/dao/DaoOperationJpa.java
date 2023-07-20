package com.inetum.appliSpringJpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.inetum.appliSpringJpa.entity.Operation;

@Repository //pour cette classe de DAO soit prise en charge par Spring
@Transactional //pour demander commit/rollback automatiques
public class DaoOperationJpa implements DaoOperation {
	
	    //NB: @PersistenceContext permet d'initialiser l'objet technique
		//entityManager à partir d'une configuration
		// src/main/resources/META-INF/persistence.xml
		// ou bien config spring équivalente dans src/main/resources/application.properties
		@PersistenceContext
		private EntityManager entityManager;


	@Override
	public Operation findById(Long idOp) {
		return entityManager.find(Operation.class, idOp);
	}

	@Override
	public List<Operation> findAll() {
		return entityManager.createQuery("SELECT op FROM Operation op" , 
				                         Operation.class)
				            .getResultList();
	}

	@Override
	public Operation insert(Operation op) {
		entityManager.persist(op);
		return op;
	}

	@Override
	public void update(Operation op) {
		entityManager.merge(op);
	}

	@Override
	public void deleteById(Long idOp) {
		Operation op = entityManager.find(Operation.class, idOp);
		entityManager.remove(op);
	}

}

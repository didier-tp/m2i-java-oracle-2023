package com.inetum.appliSpringJpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.inetum.appliSpringJpa.entity.Client;



@Repository //pour cette classe de DAO soit prise en charge par Spring
@Transactional //pour demander commit/rollback automatiques
public class DaoClientJpa implements DaoClient {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Client findById(Long numero) {
		return entityManager.find(Client.class, numero);
	}

	@Override
	public List<Client> findAll() {
		return entityManager.createNamedQuery("Client.findAll", Client.class)
	            .getResultList();
	}

	@Override
	public Client insert(Client c) {
		entityManager.persist(c);
		return c;
	}

	@Override
	public void update(Client c) {
		entityManager.merge(c);
	}

	@Override
	public void deleteById(Long num) {
		Client client= entityManager.find(Client.class, num);
		entityManager.remove(client);
	}

}

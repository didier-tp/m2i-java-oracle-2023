package com.inetum.appliSpringJpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.inetum.appliSpringJpa.entity.Client;



public class DaoGenericJpa<E,PK> implements DaoGeneric<E,PK> {
	
	private Class<E> entityClass; //ex: Client.class ou Compte.class
	
	public DaoGenericJpa(Class<E> entityClass) {
		this.entityClass = entityClass;
	}
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public E findById(PK id) {
		return entityManager.find(entityClass, id);
	}
	

	@Override
	public List<E> findAll() {
		return entityManager.createQuery("SELECT  FROM " + entityClass.getSimpleName(),
				entityClass)
	            .getResultList();
	}

	@Override
	public E insert(E e) {
		entityManager.persist(e);
		return e;
	}

	@Override
	public void update(E e) {
		entityManager.merge(e);
	}

	@Override
	public void deleteById(PK id) {
		E e= entityManager.find(entityClass, id);
		entityManager.remove(e);
	}

}

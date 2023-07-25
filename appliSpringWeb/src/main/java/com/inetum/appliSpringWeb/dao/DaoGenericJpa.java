package com.inetum.appliSpringWeb.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;


@Transactional
public abstract class DaoGenericJpa<E,PK> implements DaoGeneric<E,PK> {
	
	private Class<E> entityClass; //ex: Client.class ou Compte.class
	
	public DaoGenericJpa(Class<E> entityClass) {
		this.entityClass = entityClass;
	}
	
	public abstract EntityManager getEntityManager();

	@Override
	public E findById(PK id) {
		return getEntityManager().find(entityClass, id);
	}
	

	@Override
	public List<E> findAll() {
		return getEntityManager().createQuery("FROM " + entityClass.getSimpleName(),
				entityClass)
	            .getResultList();
	}

	@Override
	public E insert(E e) {
		getEntityManager().persist(e);
		return e;
	}

	@Override
	public void update(E e) {
		getEntityManager().merge(e);
	}

	@Override
	public void deleteById(PK id) {
		E e= getEntityManager().find(entityClass, id);
		getEntityManager().remove(e);
	}

}

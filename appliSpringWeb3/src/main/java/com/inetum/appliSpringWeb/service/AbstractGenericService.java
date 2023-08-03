package com.inetum.appliSpringWeb.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public abstract class AbstractGenericService<E,ID,DTO> 
                        implements GenericService<E,ID,DTO> {
	private CrudRepository<E,ID> dao=null ; //dao principal
	
	public AbstractGenericService(CrudRepository<E,ID> dao ) {
		this.dao = dao;
	}
	
	public E searchById(ID id) {
		return dao.findById(id).orElse(null);
	}
	public E saveOrUpdate(E entity) {
		return dao.save(entity);
	}
	public void deleteById(ID id) {
		dao.deleteById(id);
	}
	public boolean existById(ID id) {
		return dao.existsById(id);
	}
	public List<E> searchAll() {
		return (List<E>) dao.findAll();
	}
}

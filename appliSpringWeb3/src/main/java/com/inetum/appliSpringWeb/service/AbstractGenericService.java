package com.inetum.appliSpringWeb.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public abstract class AbstractGenericService<E,ID,DTO> 
                        implements GenericService<E,ID,DTO> {
		
	public abstract CrudRepository<E,ID> getDao(); //dao principal
	
	public E searchById(ID id) {
		return getDao().findById(id).orElse(null);
	}
	public E saveOrUpdate(E entity) {
		return getDao().save(entity);
	}
	public void deleteById(ID id) {
		getDao().deleteById(id);
	}
	public boolean existById(ID id) {
		return getDao().existsById(id);
	}
	public List<E> searchAll() {
		return (List<E>) getDao().findAll();
	}
}

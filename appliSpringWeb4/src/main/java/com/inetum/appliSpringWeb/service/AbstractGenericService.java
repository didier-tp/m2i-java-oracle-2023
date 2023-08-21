package com.inetum.appliSpringWeb.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.inetum.appliSpringWeb.converter.GenericConverter;
import com.inetum.appliSpringWeb.exception.NotFoundException;

public abstract class AbstractGenericService<E,ID,DTO> 
                        implements GenericService<E,ID,DTO> {
		
	public abstract CrudRepository<E,ID> getDao(); //dao principal
	public abstract Class<DTO> getDtoClass(); //dao principal
	
	public E searchById(ID id) {
		return getDao().findById(id).orElse(null);
	}
	
	/*
	public DTO searchDtoById(ID id) {
		E e = this.searchById(id);
		return e==null?null:GenericConverter.map(e,getDtoClass());
		//ex: dtoClass = CompteDto.class
	}*/
	
	public DTO searchDtoById(ID id) throws NotFoundException {
		E e = this.searchById(id);
		if(e!=null) 
			return GenericConverter.map(e,getDtoClass());
		else 
			throw new NotFoundException("entity not found for id=" + id);
	}
	
	public E saveOrUpdate(E entity) {
		return getDao().save(entity);
	}
	
	public void deleteById(ID id) throws NotFoundException{
		/*if(!(getDao().existsById(id))) 
			throw new NotFoundException("no entity to delete for id=" + id);
		*/
		shouldExistById(id);
		/*else*/
		getDao().deleteById(id);
	}
	
	public boolean existById(ID id) {
		return getDao().existsById(id);
	}
	
	public void shouldExistById(ID id) throws NotFoundException {
		if(!(getDao().existsById(id))) 
			throw new NotFoundException("no entity exists for id=" + id);
	}
	
	public List<E> searchAll() {
		return (List<E>) getDao().findAll();
	}
	
	public List<DTO> searchAllDto() {
		return GenericConverter.map(this.searchAll(), 
                                    getDtoClass()); //ex: dtoClass = CompteDto.class
	}
}

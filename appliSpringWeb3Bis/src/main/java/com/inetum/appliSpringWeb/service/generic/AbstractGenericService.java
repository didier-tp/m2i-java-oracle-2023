package com.inetum.appliSpringWeb.service.generic;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import com.inetum.appliSpringWeb.converter.GenericConverter;
import com.inetum.appliSpringWeb.exception.NotFoundException;

public abstract class AbstractGenericService<E,ID,DTO> 
                        implements GenericService<E,ID,DTO> {
		
	
	//éléments spécifiques à la sous classe
	//à définir via les méthodes abstraites getMainDao() et getMainDtoClass()
	//qui seront automatiquement appelées par @PostConstruct init()
	private CrudRepository<E,ID> mainDao; //dao principal 
	private Class<DTO> mainDtoClass; //classe du dto principal (de niveau L0)
	
	@Autowired
	public GenericConverter genericConverter;
	
	public abstract CrudRepository<E,ID>  getMainDao(); 
	public abstract Class<DTO> getMainDtoClass();
	
	
	@PostConstruct
	public void init() {
		//System.out.println("init AbstractGenericService ...");
		this.mainDao = this.getMainDao();
		this.mainDtoClass = this.getMainDtoClass();
	}
	
	public E searchById(ID id) {
		return mainDao.findById(id).orElse(null);
	}
	
	/*
	public DTO searchDtoById(ID id) {
		E e = this.searchById(id);
		return e==null?null:GenericConverter.map(e,mainDtoClass());
		//ex: dtoClass = CompteDto.class
	}*/
	
	public DTO searchDtoById(ID id) throws NotFoundException {
		E e = this.searchById(id);
		if(e!=null) 
			return genericConverter.map(e,mainDtoClass);
		else 
			throw new NotFoundException("entity not found for id=" + id);
	}
	
	public E saveOrUpdate(E entity) {
		return mainDao.save(entity);
	}
	
	public void deleteById(ID id) throws NotFoundException{
		/*if(!(mainDao.existsById(id))) 
			throw new NotFoundException("no entity to delete for id=" + id);
		*/
		shouldExistById(id);
		/*else*/
		mainDao.deleteById(id);
	}
	
	public boolean existById(ID id) {
		return mainDao.existsById(id);
	}
	
	public void shouldExistById(ID id) throws NotFoundException {
		if(!(mainDao.existsById(id))) 
			throw new NotFoundException("no entity exists for id=" + id);
	}
	
	public List<E> searchAll() {
		return (List<E>) mainDao.findAll();
	}
	
	public List<DTO> searchAllDto() {
		return genericConverter.map(this.searchAll(), 
                                    mainDtoClass); //ex: dtoClass = CompteDto.class
	}
}

package org.mycontrib.util.generic.service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.PostConstruct;

import org.mycontrib.util.generic.converter.AbstractGenericConverter;
import org.mycontrib.util.generic.dto.WithId;
import org.mycontrib.util.generic.exception.ConflictException;
import org.mycontrib.util.generic.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

public abstract class AbstractGenericService<E,ID  extends Serializable> 
                        implements GenericService<E,ID> {
		
	//éléments spécifiques à la sous classe
	//à définir via les méthodes abstraites getMainDao() et getMainDtoClass()
	//qui seront automatiquement appelées par @PostConstruct init()
	protected CrudRepository<E,ID> mainDao; //dao principal 
	protected Class<E> mainEntityClass; //classe de l'entité peristante principale
	
	@Autowired
	public AbstractGenericConverter genericConverter;
	
	public abstract CrudRepository<E,ID>  getMainDao(); 
	
	public abstract void initEntityRelationShipsFromDtoBeforeSave(E entity,Object dto);
	
	//NB: this method is no more abstract but may be redefined
	public Class<E> getMainEntityClass(){
		try {
	    	   //System.out.println(getClass().getSimpleName());
	    	   ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();  
	    	   //System.out.println("parameterizedType="+parameterizedType.toString());
	    	   Type typeE = parameterizedType.getActualTypeArguments()[0];
	    	   //System.out.println(typeE.toString());
	    	   if(!typeE.toString().equals("E")){
	    		   return(Class<E>) typeE;
	    		   //System.out.println("mainEntityClass="+typeE.getSimpleName());
	    	   }
		} catch (Exception e) {
			//e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return  null;//by default
	}
	
	@PostConstruct
	public void init() {
		//System.out.println("init AbstractGenericService ...");
		this.mainDao = this.getMainDao();
		this.mainEntityClass = this.getMainEntityClass();
	}
	
	public E searchEntityById(ID id) {
		return mainDao.findById(id).orElse(null);
	}

	
	@Override
	public <D extends WithId<ID>> D searchDtoById(ID id, Class<D> dtoClass) throws NotFoundException {
		E e = this.searchEntityById(id);
		if(e!=null) 
			return genericConverter.map(e,dtoClass);
		else 
			throw new NotFoundException("entity not found for id=" + id);
	}
	
	
	public E saveOrUpdateEntity(E entity) {
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
	
	public List<E> searchAllEntities() {
		return (List<E>) mainDao.findAll();
	}
	


	@Override
	public <D extends WithId<ID>> List<D> searchAllDto(Class<D> dtoClass) {
		return genericConverter.map(this.searchAllEntities(), 
                dtoClass); //ex: dtoClass = CompteL0.class
	}


	@Override
	public <DTO extends WithId<ID>> DTO saveNewFromDto(DTO dto) throws ConflictException {
		        ID id =  dto.getId();
		        if(id!=null){
		        	if(this.existById(id)) {
		        		throw new ConflictException("another entity already exists for id=" + id);
		        	}
		        }
		
				E entity = genericConverter.map(dto, mainEntityClass);
				initEntityRelationShipsFromDtoBeforeSave(entity,dto);
				
				E savedEntity = mainDao.save(entity); //NB: à ce moment là , éventuelle auto_incr de la clef primaire
				//on retourne un dto refletant l'entité sauvegardée
                //avec la clef primaire éventuellement autoincrémentée:
				return (DTO) genericConverter.map(savedEntity, dto.getClass());            
	}


	@Override
	public <DTO  extends WithId<ID>> void updateExistingFromDto(DTO dto) throws NotFoundException {
        shouldExistById(dto.getId());//may throw NotFoundException

		E entity = genericConverter.map(dto, mainEntityClass);
		initEntityRelationShipsFromDtoBeforeSave(entity,dto);//or ...
		E savedEntity = mainDao.save(entity);
	}
	
	@Override
	public <DTO> DTO asDto(E entity, Class<DTO> dtoClass) throws NotFoundException {
		if(entity==null) throw new NotFoundException("entity not found");
		return genericConverter.map(entity, dtoClass);
	}
	
	@Override
	public <DTO> List<DTO> asDtoList(List<E> entityList, Class<DTO> dtoClass) {
		return  genericConverter.map(entityList, dtoClass);
	}

	
}

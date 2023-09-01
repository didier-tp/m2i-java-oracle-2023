package org.mycontrib.util.generic.service;

import java.io.Serializable;
import java.util.List;

import org.mycontrib.util.generic.exception.NotFoundException;

/*
 * interface générique complete
 * exposant ID, DTO et Entites
 */
public interface GenericService<E,ID extends Serializable>
        extends InternalGenericService<E,ID>,
                GenericServiceWithDto<ID>{
	//cumul de méthodes par héritage multiple
	
	public <DTO> DTO asDto(E entity,Class<DTO> dtoClass) throws NotFoundException;
	public <DTO> List<DTO> asDtoList(List<E> entityList,Class<DTO> dtoClass);
}

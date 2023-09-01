package org.mycontrib.util.generic.service;

import java.io.Serializable;
import java.util.List;

import org.mycontrib.util.generic.dto.WithId;
import org.mycontrib.util.generic.exception.ConflictException;
import org.mycontrib.util.generic.exception.NotFoundException;

/*
 * interface générique partielle 
 * avec méthodes exposant des DTOs
 */
public interface GenericServiceWithDto<ID extends Serializable> 
               extends BasicGenericService<ID>{
   
	public <DTO extends WithId<ID>> DTO searchDtoById(ID id,Class<DTO> dtoClass) throws NotFoundException;
	public <DTO extends WithId<ID>> List<DTO> searchAllDto(Class<DTO> dtoClass);
	
	public <DTO extends WithId<ID>> DTO saveNewFromDto(DTO dto)throws ConflictException;
	public <DTO extends WithId<ID>> void updateExistingFromDto(DTO dto)throws NotFoundException;
 
}

package org.mycontrib.util.generic.service;

import java.util.List;

import org.mycontrib.util.generic.dto.WithId;
import org.mycontrib.util.generic.exception.ConflictException;
import org.mycontrib.util.generic.exception.NotFoundException;

/*
 * interface générique partielle 
 * avec méthodes exposant des DTOs
 */
public interface GenericServiceWithDto<ID> 
               extends BasicGenericService<ID>{
   
	public <DTO> DTO searchDtoById(ID id,Class<DTO> dtoClass) throws NotFoundException;
	public <DTO> List<DTO> searchAllDto(Class<DTO> dtoClass);
	
	public <DTO extends WithId> DTO saveNewFromDto(DTO dto)throws ConflictException;
	public <DTO extends WithId> void updateExistingFromDto(DTO dto)throws NotFoundException;
 
}

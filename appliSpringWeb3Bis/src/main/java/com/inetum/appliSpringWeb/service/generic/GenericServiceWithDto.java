package com.inetum.appliSpringWeb.service.generic;

import java.util.List;

import com.inetum.appliSpringWeb.exception.NotFoundException;

/*
 * interface générique partielle 
 * avec méthodes exposant des DTOs
 */
public interface GenericServiceWithDto<ID,DTO> 
               extends BasicGenericService<ID>{
   
	public List<DTO> searchAllDto();
    public DTO searchDtoById(ID id) throws NotFoundException;
    
    //variante à prévoir en version spécifique : 
    //public DTO_EX searchDtoWithDetailsById(ID id);
    
   
    // public DTO saveOrUpdateDto(DTO dto);
    // possible mais pleins de variantes envisageables
    // et donc pas très adapté à une version générique 
}

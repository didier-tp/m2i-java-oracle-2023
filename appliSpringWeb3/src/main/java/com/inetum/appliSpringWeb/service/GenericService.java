package com.inetum.appliSpringWeb.service;

import java.util.List;

/*
 * eventuelle alternative:
 *   interface BasicGenericService<E,ID>
 * et
 *   interface GenericServiceWithDto<E,ID,DTO> héritant de BasicGenericService<E,ID>
 */
public interface GenericService<E,ID,DTO> {
    public E searchById(ID id);
    
    public DTO searchDtoById(ID id);
    //variante à prévoir en version spécifique : 
    //public DTO_EX searchDtoWithDetailsById(ID id);
    
    public E saveOrUpdate(E entity);
    // public DTO saveOrUpdateDto(DTO dto);
    // possible mais pleins de variantes envisageables
    // et donc pas très adapté à une version générique
    
    public void deleteById(ID id);
    public boolean existById(ID id);
    public List<E> searchAll();
    public List<DTO> searchAllDto();
}

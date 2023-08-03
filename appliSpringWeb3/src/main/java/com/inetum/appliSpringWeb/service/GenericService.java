package com.inetum.appliSpringWeb.service;

import java.util.List;

public interface GenericService<E,ID,DTO> {
    public E searchById(ID id);
    //public DTO searchById(ID id);
    public E saveOrUpdate(E entity);
    
    public void deleteById(ID id);
    public boolean existById(ID id);
    public List<E> searchAll();
}

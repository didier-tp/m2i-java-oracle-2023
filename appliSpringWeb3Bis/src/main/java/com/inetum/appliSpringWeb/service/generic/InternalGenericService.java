package com.inetum.appliSpringWeb.service.generic;

import java.util.List;

/*
 * interface générique partielle avec méthodes 
 * exposant les entités persistantes 
 * (plutôt à usage interne/privé mais tout de même accessibles)
 */
public interface InternalGenericService<E,ID> 
                 extends BasicGenericService<ID>{
	 public E searchById(ID id);
	 public List<E> searchAll();
	 public E saveOrUpdate(E entity);
}

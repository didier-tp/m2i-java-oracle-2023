package org.mycontrib.util.generic.service;

import java.io.Serializable;
import java.util.List;

/*
 * interface générique partielle avec méthodes 
 * exposant les entités persistantes 
 * (plutôt à usage interne/privé mais tout de même accessibles)
 */
public interface InternalGenericService<E,ID extends Serializable> 
                 extends BasicGenericService<ID>{
	 public E searchEntityById(ID id);
	 public List<E> searchAllEntities();
	 public E saveOrUpdateEntity(E entity);
}

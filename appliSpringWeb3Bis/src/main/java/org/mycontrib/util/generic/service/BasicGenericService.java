package org.mycontrib.util.generic.service;

import org.mycontrib.util.generic.exception.NotFoundException;
/*
 * interface genrique partielle avec méthodes
 * n'exposant que des ids (pas d' entité ni DTO)
 */
public interface BasicGenericService<ID> {
	public void deleteById(ID id) throws NotFoundException;
    public boolean existById(ID id);
    void shouldExistById(ID id) throws NotFoundException;
}

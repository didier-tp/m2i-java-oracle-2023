package com.inetum.appliSpringWeb.service.generic;

import com.inetum.appliSpringWeb.exception.NotFoundException;
/*
 * interface genrique partielle avec méthodes
 * n'exposant que des ids (pas d' entité ni DTO)
 */
public interface BasicGenericService<ID> {
	public void deleteById(ID id) throws NotFoundException;
    public boolean existById(ID id);
    void shouldExistById(ID id) throws NotFoundException;
}

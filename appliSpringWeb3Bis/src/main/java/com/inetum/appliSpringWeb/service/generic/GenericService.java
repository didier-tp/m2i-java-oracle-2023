package com.inetum.appliSpringWeb.service.generic;

/*
 * interface générique complete
 * exposant ID, DTO et Entites
 */
public interface GenericService<E,ID,DTO>
        extends InternalGenericService<E,ID>,
                GenericServiceWithDto<ID,DTO>{
	//cumul de méthodes par héritage multiple
}

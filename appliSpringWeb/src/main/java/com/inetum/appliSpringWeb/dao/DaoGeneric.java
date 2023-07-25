package com.inetum.appliSpringWeb.dao;

import java.util.List;

//E = type d'entité persistante (Client ou Compte)
//PK= type de clef primaire (Long ou Integer ou String)
public interface DaoGeneric<E,PK> {
	E findById(PK id);
    List<E> findAll();
    E insert(E e); 
    void update(E e);
    void deleteById(PK num);
}

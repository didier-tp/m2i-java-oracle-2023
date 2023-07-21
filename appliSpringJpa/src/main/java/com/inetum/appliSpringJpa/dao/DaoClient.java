package com.inetum.appliSpringJpa.dao;

import java.util.List;

import com.inetum.appliSpringJpa.entity.Client;



/*
 * DAO = Data Access Object
 * avec méthode CRUD
 * et throws RuntimeException implicites
 */

public interface DaoClient extends DaoGeneric<Client,Long> {    
     Client findClientWithComptesById(Long numero);
     //findAll() , deleteById() , ... héritées de DaoGeneric
}

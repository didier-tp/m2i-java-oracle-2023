package com.inetum.appliSpringWeb.dao;

import com.inetum.appliSpringWeb.entity.Operation;

/*
 * DAO = Data Access Object
 * avec méthode CRUD
 * et throws RuntimeException implicites
 */

public interface DaoOperation extends DaoGeneric<Operation,Long> {
   
}

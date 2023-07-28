package com.inetum.appliSpringJpa.dao;

import com.inetum.appliSpringJpa.entity.Produit;

/*
 * DAO = Data Access Object
 * avec méthode CRUD
 * et throws RuntimeException implicites
 */

public interface DaoProduit extends DaoGeneric<Produit,Long> {
}

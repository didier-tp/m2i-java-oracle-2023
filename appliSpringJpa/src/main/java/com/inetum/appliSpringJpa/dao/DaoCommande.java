package com.inetum.appliSpringJpa.dao;

import com.inetum.appliSpringJpa.entity.Commande;

/*
 * DAO = Data Access Object
 * avec méthode CRUD
 * et throws RuntimeException implicites
 */

public interface DaoCommande extends DaoGeneric<Commande,Long> {
}

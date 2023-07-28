package com.inetum.appliSpringJpa.dao;

import com.inetum.appliSpringJpa.entity.LigneCommande;
import com.inetum.appliSpringJpa.entity.LigneCommandePK;

/*
 * DAO = Data Access Object
 * avec méthode CRUD
 * et throws RuntimeException implicites
 */

public interface DaoLigneCommande extends DaoGeneric<LigneCommande,LigneCommandePK> {
}

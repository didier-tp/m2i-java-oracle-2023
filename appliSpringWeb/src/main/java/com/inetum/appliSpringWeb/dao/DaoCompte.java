package com.inetum.appliSpringWeb.dao;

import java.util.List;

import com.inetum.appliSpringWeb.entity.Compte;

/*
 * DAO = Data Access Object
 * avec méthode CRUD
 * et throws RuntimeException implicites
 */

public interface DaoCompte extends DaoGeneric<Compte,Long> {
     List<Compte> findBySoldeMini(double soldeMini);
     List<Compte> findBySoldeMaxi(double soldeMaxi);
     //findAll() , deleteById() , ... héritées de DaoGeneric
}

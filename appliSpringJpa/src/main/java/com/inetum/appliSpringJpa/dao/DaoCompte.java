package com.inetum.appliSpringJpa.dao;

import java.util.List;

import com.inetum.appliSpringJpa.entity.Compte;

/*
 * DAO = Data Access Object
 * avec méthode CRUD
 * et throws RuntimeException implicites
 */

public interface DaoCompte extends DaoGeneric<Compte,Long> {
     Compte findCompteWithOperationsById(Long numero);
     List<Compte> findComptesOfClient(Long numClient);
     List<Compte> findBySoldeMini(double soldeMini);
     List<Compte> findBySoldeMaxi(double soldeMaxi);
     //findAll() , deleteById() , ... héritées de DaoGeneric
     void trouverEtDebiter(Long numCompte,double montantDebit);
}

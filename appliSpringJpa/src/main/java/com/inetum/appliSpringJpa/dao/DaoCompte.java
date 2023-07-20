package com.inetum.appliSpringJpa.dao;

import java.util.List;

import com.inetum.appliSpringJpa.entity.Compte;

/*
 * DAO = Data Access Object
 * avec méthode CRUD
 * et throws RuntimeException implicites
 */

public interface DaoCompte {
     Compte findById(Long numero);
     Compte findCompteWithOperationsById(Long numero);
     List<Compte> findAll();
     List<Compte> findBySoldeMini(double soldeMini);
     List<Compte> findBySoldeMaxi(double soldeMaxi);
     Compte insert(Compte cpt); //en retour compte avec numero auto_incrémenté
     void update(Compte cpt);
     void deleteById(Long cpt);
}

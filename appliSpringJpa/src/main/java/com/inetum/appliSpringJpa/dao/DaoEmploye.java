package com.inetum.appliSpringJpa.dao;

import java.util.List;

import com.inetum.appliSpringJpa.entity.Employe;

/*
 * DAO = Data Access Object
 * avec méthode CRUD
 * et throws RuntimeException implicites
 */

public interface DaoEmploye {
     Employe findById(Long numero);
     List<Employe> findAll();
     //....
     Employe insert(Employe emp); //en retour employe avec numero auto_incrémenté
     void update(Employe emp);
     void deleteById(Long num);
}

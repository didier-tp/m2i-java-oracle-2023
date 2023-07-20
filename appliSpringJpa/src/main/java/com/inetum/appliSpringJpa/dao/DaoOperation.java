package com.inetum.appliSpringJpa.dao;

import java.util.List;

import com.inetum.appliSpringJpa.entity.Operation;

/*
 * DAO = Data Access Object
 * avec méthode CRUD
 * et throws RuntimeException implicites
 */

public interface DaoOperation {
     Operation findById(Long idOp);
     List<Operation> findAll();
     Operation insert(Operation op); //en retour Operation avec numero auto_incrémenté
     void update(Operation op);
     void deleteById(Long op);
}

package com.inetum.appliSpringJpa.dao;

import java.util.List;

import com.inetum.appliSpringJpa.entity.Client;



/*
 * DAO = Data Access Object
 * avec méthode CRUD
 * et throws RuntimeException implicites
 */

public interface DaoClient {
     Client findById(Long numero);
     Client findClientWithComptesById(Long numero);
     List<Client> findAll();
     Client insert(Client c); //en retour client avec numero auto_incrémenté
     void update(Client c);
     void deleteById(Long num);
}

package com.inetum.appliSpringWeb.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inetum.appliSpringWeb.entity.Compte;

/*
 * DAO = Data Access Object
 * avec méthode CRUD
 * et throws RuntimeException implicites
 */

public interface DaoCompte extends JpaRepository<Compte,Long> {
	 /*
    .save()
    .findAll() 
    .findById()
    .deleteById() 
    héritées de JpaRepository / CrudRepository
    */
	//Les trois méthodes suivantes seront codées automatiquement (req SQL / JPAQL)
	//selon les conventions de nommage de SpringData (findBy...)
     List<Compte> findBySoldeGreaterThanEqual(double soldeMini); //ancien nom = findBySoldeMini
     List<Compte> findBySoldeLessThanEqual(double soldeMaxi);//ancien nom = findBySoldeMaxi
     
     List<Compte> findByCustomerId(Long idCustomer);//without NamedQuery
     //findByCustomerId=findBy Customer Id , .customer exists in Compte.class , .id exists in Customer.class
     //automatic query from method name = SELECT cpt FROM COMPTE cpt WHERE cpt.customer.id = ?1
     
     //codé via @NamedQuery(name="Compte.findBySoldeMini")
     List<Compte> findBySoldeMini(double soldeMini); 
     
     //codé via @NamedQuery(name="Compte.findByIdWithOperations")
     Optional<Compte> findByIdWithOperations(Long numCompte);
    
}

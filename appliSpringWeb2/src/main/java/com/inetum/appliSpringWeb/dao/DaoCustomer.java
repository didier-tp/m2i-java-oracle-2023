package com.inetum.appliSpringWeb.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inetum.appliSpringWeb.entity.Customer;

/*
 * DAO = Data Access Object
 * avec méthode CRUD
 * et throws RuntimeException implicites
 */

public interface DaoCustomer extends JpaRepository<Customer,Long> {
	 /*
    .save()
    .findAll() 
    .findById()
    .deleteById() 
    héritées de JpaRepository / CrudRepository
    */
	List<Customer> findByFirstnameAndLastname(String firstName, String lastName);//via convention nom de méthode
	
	Optional<Customer> findByIdWithComptes(Long customerId);//via @NamedQuery "Customer.findByIdWithComptes"
    
}

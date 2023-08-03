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
	
	//via convention nom de méthode
	List<Customer> findByFirstnameAndLastname(String firstName, String lastName);
	
	//via @NamedQuery "Customer.findByIdWithComptes"
	Optional<Customer> findByIdWithComptes(Long customerId);
    
}

package com.inetum.appliSpringWeb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inetum.appliSpringWeb.dao.DaoCompte;
import com.inetum.appliSpringWeb.dao.DaoCustomer;
import com.inetum.appliSpringWeb.dto.CustomerL0;
import com.inetum.appliSpringWeb.entity.Customer;
import com.inetum.appliSpringWeb.service.generic.AbstractGenericService;

@Service
@Transactional
public class ServiceCustomerImpl 
    extends AbstractGenericService<Customer, Long, CustomerL0>
    implements ServiceCustomer {
	
	@Override
	public CrudRepository<Customer, Long> getMainDao() {
		return this.daoCustomer;
	}

	@Override
	public Class<CustomerL0> getMainDtoClass() {
		return CustomerL0.class;
	}
	
	Logger logger = LoggerFactory.getLogger(ServiceCustomerImpl.class);
	
	
	@Autowired
	private DaoCustomer daoCustomer; //dao principal
	
	@Autowired
	private DaoCompte daoCompte;  //dao secondaire/annexe
	

	@Override
	public boolean checkCustomerPassword(long customerId, String password) {
		Customer customer = daoCustomer.findById(customerId).orElse(null);
		if(customer==null || password==null) return false;
		return password.equals(customer.getPassword());
	}

	@Override
	public String resetCustomerPassword(long customerId) {
		Customer customer = daoCustomer.findById(customerId).get();
		String pwd = "tempPwd"; //à améliorer
		customer.setPassword(pwd);
		daoCustomer.save(customer);
		return pwd;
	}

	
	@Override
	public Customer rechercherCustomerAvecComptesParNumero(long idCustomer) {
		return daoCustomer.findByIdWithComptes(idCustomer).orElse(null);
	}

	@Override
	public List<Customer> rechercherCustomerSelonPrenomEtNom(String prenom, String nom) {
		return daoCustomer.findByFirstnameAndLastname(prenom, nom);
	}

	
	
}

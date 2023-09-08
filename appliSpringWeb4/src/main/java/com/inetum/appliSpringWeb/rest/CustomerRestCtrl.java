package com.inetum.appliSpringWeb.rest;

import java.util.List;

import org.mycontrib.util.generic.rest.AbstractGenericRestCtrl;
import org.mycontrib.util.generic.service.GenericServiceWithDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inetum.appliSpringWeb.converter.DtoConverter;
import com.inetum.appliSpringWeb.converter.GenericConverter;
import com.inetum.appliSpringWeb.dto.CompteL0;
import com.inetum.appliSpringWeb.dto.CustomerL0;
import com.inetum.appliSpringWeb.entity.Customer;
import com.inetum.appliSpringWeb.service.ServiceCustomer;

@RestController
@RequestMapping(value="/rest/api-bank/customer" , headers="Accept=application/json")
//@CrossOrigin permet d'ajouter des autorisations "CORS" pour que ce web service
//puisse être appelé en mode ajax depuis d'autres origines/url que http://localhost:8080
@CrossOrigin(origins = "*" , methods = { RequestMethod.GET , RequestMethod.POST})
public class CustomerRestCtrl extends AbstractGenericRestCtrl<Long,CustomerL0>{
	
	
	@Autowired
	private ServiceCustomer serviceCustomer;
	
	@Override
	public GenericServiceWithDto<Long> getGenericServiceWithDto() {
		return serviceCustomer;
	}
	
	@Override
	public Class<CustomerL0> getMainDtoClass() {
		return CustomerL0.class;
	}
	
	@Autowired
	private GenericConverter genericConverter;
	
	@Autowired
	private DtoConverter dtoConverter;
	
	
	
	//exemple de fin d'URL: ./api-bank/customer
	//                      ./api-bank/customer?firstname=jean&lastname=Bon
	@GetMapping("" )
	public List<CustomerL0> getCustomers(
			 @RequestParam(value="firstname",required=false) String firstname,
			 @RequestParam(value="lastname",required=false) String lastname){
		if(firstname==null || lastname ==null)
			return serviceCustomer.searchAllDto(CustomerL0.class);
		else
			return genericConverter.map(
					serviceCustomer.rechercherCustomerSelonPrenomEtNom(firstname, lastname),
					CustomerL0.class);
	}
	
	//exemple de fin d'URL: ./api-bank/customer
	//appelé en mode POST avec dans la partie invisible "body" de la requête:
	// { "id" : null , "firstname" : "jean" , "lastname" : "Bon" , "password" : "pwd1" }
	// ou bien { "firstname" : "monPrenom" , "lastname" : "nomNom" , "password" : "monPwd"}
	@PostMapping("" )
	public CustomerL0 postCustomer(@RequestBody CustomerL0 customerDto) {
		return serviceCustomer.saveNewFromDto(customerDto);
	}
	
	//exemple de fin d'URL: ./api-bank/customer
	//ou bien               ./api-bank/customer/5
	//appelé en mode PUT avec dans la partie invisible "body" de la requête:
	//{ "id" : 5 , "firstname" : "monPrenom" , "lastname" : "nomNom" , "password" : "nouveauPwd"}
	// ou bien { "firstname" : "monPrenom" , "lastname" : "nomNom" , "password" : "nouveauPwd"}
	@PutMapping({"" , "/{id}" })
	public CustomerL0 putCustomerToUpdate(@RequestBody CustomerL0 customerDto , 
			      @PathVariable(value="id",required = false ) Long idToUpdate) {
		if(customerDto.getId()==null)	customerDto.setId(idToUpdate);
		serviceCustomer.updateExistingFromDto(customerDto); //remonte NotFoundException si pas trouvé
		return customerDto;
	}

}

package com.inetum.appliSpringWeb.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.inetum.appliSpringWeb.dto.CustomerDto;
import com.inetum.appliSpringWeb.entity.Customer;
import com.inetum.appliSpringWeb.service.ServiceCustomer;

@RestController
@RequestMapping(value="/api-bank/customer" , headers="Accept=application/json")
//@CrossOrigin permet d'ajouter des autorisations "CORS" pour que ce web service
//puisse être appelé en mode ajax depuis d'autres origines/url que http://localhost:8080
@CrossOrigin(origins = "*" , methods = { RequestMethod.GET , RequestMethod.POST})
public class CustomerRestCtrl {
	
	
	@Autowired
	private ServiceCustomer serviceCustomer;
	
	private DtoConverter dtoConverter = new DtoConverter();
	
	//exemple de fin d'URL: ./api-bank/customer/1
	@GetMapping("/{id}" )
	public ResponseEntity<?> getCustomerByNumero(@PathVariable("id") Long idCustomer) {
	    CustomerDto customerDto = serviceCustomer.searchDtoById(idCustomer);
	    if(customerDto!=null)
	    	return new ResponseEntity<CustomerDto>(
	    			customerDto, HttpStatus.OK); 
	    else
	    	return new ResponseEntity<String>("{ \"err\" : \"customer not found\"}" ,
	    			           HttpStatus.NOT_FOUND); //NOT_FOUND = code http 404
	}
	
	//exemple de fin d'URL: ./api-bank/customer/1
	//à déclencher en mode DELETE
	@DeleteMapping("/{id}" )
	public ResponseEntity<?> deleteCustomerByNumero(@PathVariable("id") Long idCustomer) {
		    
		    if( !serviceCustomer.existById(idCustomer))
		    	return new ResponseEntity<String>("{ \"err\" : \"customer not found\"}" ,
		    			           HttpStatus.NOT_FOUND); //NOT_FOUND = code http 404
		    
		    serviceCustomer.deleteById(idCustomer);
		    return new ResponseEntity<String>("{ \"done\" : \"customer deleted\"}" ,HttpStatus.OK); 
		    //ou bien
		    //return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
		}
	
	//exemple de fin d'URL: ./api-bank/customer
	//                      ./api-bank/customer?firstname=jean&lastname=Bon
	@GetMapping("" )
	public List<CustomerDto> getCustomers(
			 @RequestParam(value="firstname",required=false) String firstname,
			 @RequestParam(value="lastname",required=false) String lastname){
		if(firstname==null || lastname ==null)
			return serviceCustomer.searchAllDto();
		else
			return GenericConverter.map(
					serviceCustomer.rechercherCustomerSelonPrenomEtNom(firstname, lastname),
					CustomerDto.class);
	}
	
	//exemple de fin d'URL: ./api-bank/customer
	//appelé en mode POST avec dans la partie invisible "body" de la requête:
	// { "id" : null , "firstname" : "jean" , "lastname" : "Bon" , "password" : "pwd1" }
	// ou bien { "firstname" : "monPrenom" , "lastname" : "nomNom" , "password" : "monPwd"}
	@PostMapping("" )
	public CustomerDto postCompte(@RequestBody CustomerDto nouveauCustomer) {
		Customer customerEnregistreEnBase = serviceCustomer.saveOrUpdate(
				              GenericConverter.map(nouveauCustomer,Customer.class) );
		//on retourne le compte avec clef primaire auto_incrémentée
		return GenericConverter.map(customerEnregistreEnBase,CustomerDto.class); 
	}
	
	//exemple de fin d'URL: ./api-bank/customer
	//ou bien               ./api-bank/customer/5
	//appelé en mode PUT avec dans la partie invisible "body" de la requête:
	//{ "id" : 5 , "firstname" : "monPrenom" , "lastname" : "nomNom" , "password" : "nouveauPwd"}
	// ou bien { "firstname" : "monPrenom" , "lastname" : "nomNom" , "password" : "nouveauPwd"}
	@PutMapping({"" , "/{id}" })
	public ResponseEntity<?> putCustomerToUpdate(@RequestBody CustomerDto customerDto , 
			      @PathVariable(value="id",required = false ) Long idCustomer) {
		
		    Long idCustomerToUpdate = idCustomer!=null ? idCustomer : customerDto.getId();
		   
		   
		    if(!serviceCustomer.existById(idCustomerToUpdate))
		    	return new ResponseEntity<String>("{ \"err\" : \"Customer not found\"}" ,
 			           HttpStatus.NOT_FOUND); //NOT_FOUND = code http 404
		    
		    if(customerDto.getId()==null)
		    	customerDto.setId(idCustomerToUpdate);
		    
			serviceCustomer.saveOrUpdate(GenericConverter.map(customerDto,Customer.class));
			return new ResponseEntity<CustomerDto>(customerDto , HttpStatus.OK);
	}
	

}

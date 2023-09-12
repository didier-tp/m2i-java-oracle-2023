package com.inetum.appliSpringWeb.rest;

import java.util.List;

import org.mycontrib.util.generic.exception.NotFoundException;
import org.mycontrib.util.generic.rest.AbstractGenericRestCtrl;
import org.mycontrib.util.generic.service.GenericServiceWithDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inetum.appliSpringWeb.dto.DeviseL0;
import com.inetum.appliSpringWeb.service.ServiceDevise;

@RestController
@RequestMapping(value="/rest/api-devise/devise" , headers="Accept=application/json")
public class DeviseRestCtrl extends AbstractGenericRestCtrl<String,DeviseL0>{
	
	@Autowired
	private ServiceDevise serviceDevise;
	
	@Override
	public GenericServiceWithDto<String> getGenericServiceWithDto() {
		return serviceDevise;
	}
	
	@Override
	public Class<DeviseL0> getMainDtoClass() {
		return DeviseL0.class;
	}
	
	// URL= ./rest/api-devise/devise/EUR_or_other_id
	//   or ./rest/api-devise/devise/EUR?detailLevel=1ou2ouAutre
	@GetMapping("/{id}")
	public DeviseL0 getDtoById(@PathVariable("id") String id,
			@RequestParam(value="detailLevel",required=false) Integer detailLevel) throws NotFoundException {
		return super.internalGetDtoById(id,detailLevel); //may throwing NotFoundException
	}
		

	// URL= ./rest/api-devise/devise/EUR_or_other_id
	// appelé en mode DELETE
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteDtoById(@PathVariable("id") String id) {
		return super.internalDeleteDtoById(id);
	}
	
	//exemple de fin d'URL: ./rest/api-devise/devise
	@GetMapping("" )
	public List<DeviseL0> getDevises(){
		return serviceDevise.searchAllDto(DeviseL0.class);
	}
	
	@PostMapping("" ) 
	public DeviseL0 postDevise(@RequestBody DeviseL0 nouvelleDevise) {
		return serviceDevise.saveNewFromDto(nouvelleDevise);
	}
	
	@PutMapping("" ) 
	public DeviseL0 putDevise(@RequestBody DeviseL0 deviseToUpdate) {
		serviceDevise.updateExistingFromDto(deviseToUpdate);
		return  deviseToUpdate;
	}
}

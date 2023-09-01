package com.inetum.appliSpringWeb.rest;

import java.util.List;

import org.mycontrib.util.generic.rest.AbstractGenericRestCtrl;
import org.mycontrib.util.generic.service.GenericServiceWithDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inetum.appliSpringWeb.dto.DeviseL0;
import com.inetum.appliSpringWeb.service.ServiceDevise;

@RestController
@RequestMapping(value="/api-devise/devise" , headers="Accept=application/json")
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
	
	//exemple de fin d'URL: ./api-devise/devise
	@GetMapping("" )
	public List<DeviseL0> getDevises(){
		return serviceDevise.searchAllDto(DeviseL0.class);
	}
}

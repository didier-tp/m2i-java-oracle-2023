package com.inetum.appliSpringWeb.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inetum.appliSpringWeb.dto.DeviseL0;

@RestController
@RequestMapping(value="/api-devise/devise" , headers="Accept=application/json")
public class DeviseRestCtrl {
	
	//exemple de fin d'URL: ./api-devise/devise/USD
	//@RequestMapping(value="/{codeDevise}" , method=RequestMethod.GET)
	@GetMapping("/{codeDevise}" )
	public DeviseL0 getDeviseByCode(@PathVariable("codeDevise") String code) {
		DeviseL0 devise = new DeviseL0(code,"devise",1.1);
		//devise.setChange(1.01);
		return devise;
	}

}

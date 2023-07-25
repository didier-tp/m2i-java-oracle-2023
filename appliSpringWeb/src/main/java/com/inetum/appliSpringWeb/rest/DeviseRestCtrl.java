package com.inetum.appliSpringWeb.rest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.inetum.appliSpringWeb.dto.Devise;

@RestController
@RequestMapping(value="/api-devise/devise" , headers="Accept=application/json")
public class DeviseRestCtrl {
	
@RequestMapping(value="/{codeDevise}" , method=RequestMethod.GET)
public Devise getDeviseByCode(@PathVariable("codeDevise") String code) {
	return new Devise(code,"devise",1.1);
}

}

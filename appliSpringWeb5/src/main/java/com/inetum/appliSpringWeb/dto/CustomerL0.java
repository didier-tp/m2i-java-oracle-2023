package com.inetum.appliSpringWeb.dto;

import org.mycontrib.util.generic.dto.WithId;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class CustomerL0 implements WithId<Long> {

	private Long id;
	private String firstname;
	private String lastname;
	
	private String password;

	public CustomerL0(Long id, String firstname, String lastname, String password) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
	}
	
	
	
}

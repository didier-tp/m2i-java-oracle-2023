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
	
}

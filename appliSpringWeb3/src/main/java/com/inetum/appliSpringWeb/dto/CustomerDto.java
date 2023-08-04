package com.inetum.appliSpringWeb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class CustomerDto {

	private Long id;
	private String firstname;
	private String lastname;
	
	private String password;

}

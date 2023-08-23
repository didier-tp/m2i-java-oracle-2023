package com.inetum.appliSpringWeb.converter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DtoConverterSpringConfig {
	
	@Bean
	public DtoConverter dtoConverter() {
		return DtoConverter.INSTANCE;
	}
	
	/*
	 pour pouvoir faire ceci dans classes de type @Service ou @RestController :
	 
	 @Autowired 
	 private DtoConverter dtoConverter;
	 */

}

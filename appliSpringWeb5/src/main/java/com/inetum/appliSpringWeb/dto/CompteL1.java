package com.inetum.appliSpringWeb.dto;

import org.mycontrib.util.generic.dto.DtoByLevelUtil;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * CompteL1 au sens "DTO essentiel" (pour post/put et get sans trop de details) 
 */
@Data @NoArgsConstructor
public class CompteL1  extends CompteL0{
    //private Long numeroClient ; //ancien nom (sans convention)
	private Long customerId ;//nouveau nom respectant convention "xxxId"
	
}

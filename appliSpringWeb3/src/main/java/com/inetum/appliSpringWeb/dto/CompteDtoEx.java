package com.inetum.appliSpringWeb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * CompteDtoEx au sens "détaillé / étendu" 
 */
@Data @NoArgsConstructor
public class CompteDtoEx  extends CompteDto{
    private Long numeroClient ;     
}

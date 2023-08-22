package com.inetum.appliSpringWeb.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * CompteDtoEx au sens "détaillé / étendu" 
 */
@Data @NoArgsConstructor
public class CompteDtoEx2  extends CompteDto{
    CustomerDto customer;
    private List<OperationDto> operations;
    
}

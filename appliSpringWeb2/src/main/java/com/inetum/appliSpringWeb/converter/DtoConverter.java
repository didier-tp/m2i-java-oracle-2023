package com.inetum.appliSpringWeb.converter;

import org.springframework.beans.BeanUtils;

import com.inetum.appliSpringWeb.dto.CompteDto;
import com.inetum.appliSpringWeb.entity.Compte;

public class DtoConverter {

	public /*static*/ CompteDto compteToCompteDto(Compte entity) {
		/*return new CompteDto(entity.getNumero() , 
				             entity.getLabel(),
				             entity.getSolde());*/
		
		CompteDto compteDto = new CompteDto();
		
		compteDto.setNumero(entity.getNumero());
		compteDto.setLabel(entity.getLabel());
		compteDto.setSolde(entity.getSolde());
		
		//BeanUtils.copyProperties(entity, compteDto); //compact/écriture concise mais pas rapide
		return compteDto;
	}
	
	public /*static*/ Compte compteDtoToCompte(CompteDto dto) {
		return new Compte(dto.getNumero() , 
	                      dto.getLabel(),
	                      dto.getSolde());
	}

}

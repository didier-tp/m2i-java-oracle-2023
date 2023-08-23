package com.inetum.appliSpringWeb.converter;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.inetum.appliSpringWeb.dto.CompteL0;
import com.inetum.appliSpringWeb.dto.CompteL1;
import com.inetum.appliSpringWeb.dto.CompteL2;
import com.inetum.appliSpringWeb.dto.CustomerL0;
import com.inetum.appliSpringWeb.dto.OperationL0;
import com.inetum.appliSpringWeb.entity.Compte;

//NB: pour un eventuel basculement sur mapStruct ou autre,
//les méthodes de sont pas "static"
//et cet objet sera à la fois accessible via le singleton élémentaire
//DtoConverter.INSTANCE et comme un composant spring injectable

public class DtoConverter {
	
	public static DtoConverter INSTANCE = new DtoConverter();
	
	public /*static*/ List<CompteL0> compteToCompteDto(List<Compte> entityList) {
		return entityList.stream()
				         .map((entity)->compteToCompteDto(entity))
				         .toList();
	}

	public /*static*/ CompteL0 compteToCompteDto(Compte entity) {
		/*return new CompteDto(entity.getNumero() , 
				             entity.getLabel(),
				             entity.getSolde());*/
		
		CompteL0 compteDto = new CompteL0();
		
		compteDto.setNumero(entity.getNumero());
		//compteDto.setLabel(entity.getLabel());
		compteDto.setLabel(entity.getLabel().toUpperCase());
		compteDto.setSolde(entity.getSolde());
		
		//BeanUtils.copyProperties(entity, compteDto); //compact/écriture concise mais pas rapide
		return compteDto;
	}
	
	public /*static*/ CompteL1 compteToCompteDtoEx(Compte entity) {
		Long numClient=entity.getCustomer()!=null?entity.getCustomer().getId():null;
		CompteL1 compteDto = new CompteL1();
		BeanUtils.copyProperties(entity, compteDto); //compact/écriture concise mais pas rapide
		compteDto.setCustomerId(numClient);
		return compteDto;
	}
	
	public /*static*/ CompteL2 compteToCompteDtoEx2(Compte entity) {
		CompteL2 compteDto = new CompteL2();
		BeanUtils.copyProperties(entity, compteDto); //compact/écriture concise mais pas rapide
		compteDto.setCustomer(GenericConverter.CONVERTER.map(entity.getCustomer(), CustomerL0.class));
		compteDto.setOperations(GenericConverter.CONVERTER.map(entity.getOperations(), OperationL0.class));
		return compteDto;
	}
	
	public /*static*/ Compte compteDtoToCompte(CompteL0 dto) {
		return new Compte(dto.getNumero() , 
	                      dto.getLabel(),
	                      dto.getSolde());
	}

	public List<CompteL1> compteToCompteDtoEx(List<Compte> entityList) {
		return entityList.stream()
		       .map((entity)->compteToCompteDtoEx(entity))
		       .toList();
	}
	
	

}

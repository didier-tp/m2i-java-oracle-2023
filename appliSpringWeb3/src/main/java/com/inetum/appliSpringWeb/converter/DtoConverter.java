package com.inetum.appliSpringWeb.converter;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.inetum.appliSpringWeb.dto.CompteDto;
import com.inetum.appliSpringWeb.dto.CompteDtoEx;
import com.inetum.appliSpringWeb.dto.CompteDtoEx2;
import com.inetum.appliSpringWeb.dto.CustomerDto;
import com.inetum.appliSpringWeb.dto.OperationDto;
import com.inetum.appliSpringWeb.entity.Compte;

public class DtoConverter {
	
	public /*static*/ List<CompteDto> compteToCompteDto(List<Compte> entityList) {
		return entityList.stream()
				         .map((entity)->compteToCompteDto(entity))
				         .toList();
	}

	public /*static*/ CompteDto compteToCompteDto(Compte entity) {
		/*return new CompteDto(entity.getNumero() , 
				             entity.getLabel(),
				             entity.getSolde());*/
		
		CompteDto compteDto = new CompteDto();
		
		compteDto.setNumero(entity.getNumero());
		//compteDto.setLabel(entity.getLabel());
		compteDto.setLabel(entity.getLabel().toUpperCase());
		compteDto.setSolde(entity.getSolde());
		
		//BeanUtils.copyProperties(entity, compteDto); //compact/écriture concise mais pas rapide
		return compteDto;
	}
	
	public /*static*/ CompteDtoEx compteToCompteDtoEx(Compte entity) {
		Long numClient=entity.getCustomer()!=null?entity.getCustomer().getId():null;
		CompteDtoEx compteDto = new CompteDtoEx();
		BeanUtils.copyProperties(entity, compteDto); //compact/écriture concise mais pas rapide
		compteDto.setNumeroClient(numClient);
		return compteDto;
	}
	
	public /*static*/ CompteDtoEx2 compteToCompteDtoEx2(Compte entity) {
		CompteDtoEx2 compteDto = new CompteDtoEx2();
		BeanUtils.copyProperties(entity, compteDto); //compact/écriture concise mais pas rapide
		compteDto.setCustomer(GenericConverter.map(entity.getCustomer(), CustomerDto.class));
		compteDto.setOperations(GenericConverter.map(entity.getOperations(), OperationDto.class));
		return compteDto;
	}
	
	public /*static*/ Compte compteDtoToCompte(CompteDto dto) {
		return new Compte(dto.getNumero() , 
	                      dto.getLabel(),
	                      dto.getSolde());
	}

	public List<CompteDtoEx> compteToCompteDtoEx(List<Compte> entityList) {
		return entityList.stream()
		       .map((entity)->compteToCompteDtoEx(entity))
		       .toList();
	}
	
	

}

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

//NB: pour éviter toute boucle infinie (dépendance circulaire),
//cette classe peut éventuellement utiliser GenericMapper.MAPPER 
//mais NE DOIS PAS utiliser GenericConverter.CONVERTER !!!!

public class DtoConverter {
	
	public static DtoConverter INSTANCE = new DtoConverter();
	
	public /*static*/ List<CompteL0> compteListToCompteL0List(List<Compte> entityList) {
		return entityList.stream()
				         .map((entity)->compteToCompteL0(entity))
				         .toList();
	}

	public /*static*/ CompteL0 compteToCompteL0(Compte entity) {
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
	
	public /*static*/ CompteL1 compteToCompteL1(Compte entity) {
		Long numClient=entity.getCustomer()!=null?entity.getCustomer().getId():null;
		CompteL1 compteDto = new CompteL1();
		BeanUtils.copyProperties(entity, compteDto); //compact/écriture concise mais pas rapide
		compteDto.setCustomerId(numClient);
		return compteDto;
	}
	
	public /*static*/ CompteL2 compteToCompteL2(Compte entity) {
		CompteL2 compteDto = new CompteL2();
		BeanUtils.copyProperties(entity, compteDto); //compact/écriture concise mais pas rapide
		compteDto.setCustomer(GenericMapper.MAPPER.map(entity.getCustomer(), CustomerL0.class));
		compteDto.setOperations(GenericMapper.MAPPER.map(entity.getOperations(), OperationL0.class));
		return compteDto;
	}
	
	public /*static*/ Compte compteL0ToCompte(CompteL0 dto) {
		return new Compte(dto.getNumero() , 
	                      dto.getLabel(),
	                      dto.getSolde());
	}

	public List<CompteL1> compteListToCompteL1List(List<Compte> entityList) {
		return entityList.stream()
		       .map((entity)->compteToCompteL1(entity))
		       .toList();
	}
	

}

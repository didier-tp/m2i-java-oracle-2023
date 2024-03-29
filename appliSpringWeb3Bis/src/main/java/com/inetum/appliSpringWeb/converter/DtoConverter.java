package com.inetum.appliSpringWeb.converter;

import java.util.List;

import org.mycontrib.util.generic.converter.GenericMapper;
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
//mais NE DOIS PAS utiliser GenericConverter.CONVERTER
//pour coder les méthodes specifiques xxxToYyy() !!!!

public class DtoConverter {
	
	public static DtoConverter INSTANCE = new DtoConverter();
	
	//NB: xxxToXxxL0() et xxxL0ToXxx() utile que si différence de structure dès le début (ex: .firstname et .prenom)
	
	public  <S,D> D map(S source , Class<D> targetClass) {
		return GenericConverter.CONVERTER.map(source, targetClass); //petite exception à l'absence de micro dépendance bi-directionnelle
	}
	
	public <S,D> List<D> map(List<S> sourceList , Class<D> targetClass){
		return GenericConverter.CONVERTER.map(sourceList, targetClass); //petite exception à l'absence de micro dépendance bi-directionnelle
	}
	
	public  CompteL1 compteToCompteL1(Compte entity) {
		Long numClient=entity.getCustomer()!=null?entity.getCustomer().getId():null;
		CompteL1 compteDto = new CompteL1();
		BeanUtils.copyProperties(entity, compteDto); //compact/écriture concise mais pas rapide
		compteDto.setCustomerId(numClient);
		return compteDto;
	}
	
	public  CompteL2 compteToCompteL2(Compte entity) {
		CompteL2 compteDto = new CompteL2();
		BeanUtils.copyProperties(entity, compteDto); //compact/écriture concise mais pas rapide
		compteDto.setCustomer(GenericMapper.MAPPER.map(entity.getCustomer(), CustomerL0.class));
		compteDto.setOperations(GenericMapper.MAPPER.map(entity.getOperations(), OperationL0.class));
		return compteDto;
	}
	
	



}

package com.inetum.appliSpringWeb.converter;

import java.util.List;

import org.springframework.beans.BeanUtils;

public class GenericConverter {

	//GenericConverter.map(compteEntity,CompteDto.class)
	public static <S,D> D map(S source , Class<D> targetClass) {
		D target  = null;
		try {
			target = targetClass.getDeclaredConstructor().newInstance();
			BeanUtils.copyProperties(source, target);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return target;
	}
	
	//GenericConverter.map(ListeCompteEntity,CompteDto.class)
	public static <S,D> List<D> map(List<S> sourceList , Class<D> targetClass){
		return  sourceList.stream()
			   .map((source)->map(source,targetClass))
			   .toList();
	}

}

package org.mycontrib.util.generic.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
/*
 * GenericMapper = mapper/convertisseur hyper générique utilisant 
 * seulement BeanUtils.copyProperties
 * 
 * NB: pour un eventuel basculement sur mapStruct ou autre,
 * les méthodes de sont pas "static"
 * et cet objet sera à la fois accessible via le singleton élémentaire
 * GenericMapper.MAPPER et comme un composant spring injectable
 */
public class GenericMapper {
	
	public static GenericMapper MAPPER = new GenericMapper();

	//GenericMapper.MAPPER.map(compteEntity,CompteDto.class) sans spring
	//genericMapper.map(compteEntity,CompteDto.class) avec injection spring
	public /*static*/ <S,D> D map(S source , Class<D> targetClass) {
		D target  = null;
		try {
			target = targetClass.getDeclaredConstructor().newInstance();
			BeanUtils.copyProperties(source, target);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return target;
	}
	
	//GenericMapper.MAPPER.map(ListeCompteEntity,CompteDto.class) sans spring
	//genericMapper.map(ListeCompteEntity,CompteDto.class) avec injection spring
	public /*static*/ <S,D> List<D> map(List<S> sourceList , Class<D> targetClass){
		return  sourceList.stream()
			   .map((source)->map(source,targetClass))
			   .collect(Collectors.toList());
	}

}

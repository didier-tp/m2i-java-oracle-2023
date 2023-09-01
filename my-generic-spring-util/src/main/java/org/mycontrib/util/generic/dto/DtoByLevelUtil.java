package org.mycontrib.util.generic.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DtoByLevelUtil {
	
	public static DtoByLevelUtil INSTANCE = new DtoByLevelUtil();
	
	private Map<Class<?>,Map<Integer,Class<?>>> levelDtoClassMapOfMaps = new HashMap<>();
	
	public <ID extends Serializable> 
	    Class<? extends WithId<ID>> getDtoSubClassByLevel(Class<? extends WithId<ID>> mainDtoClass,
	    		                                          Integer detailLevel) {
		if(detailLevel==null) detailLevel=0;
		initMainDtoClassDefaultMapIfNecessary(mainDtoClass);
		Class<? extends WithId<ID>> dtoClass = (Class<? extends WithId<ID>>) 
				 (levelDtoClassMapOfMaps.get(mainDtoClass)).get(detailLevel);
		return (dtoClass!=null)?dtoClass:mainDtoClass;
	}
	
	public <DTO> void setMainDtoClass(Class<DTO> mainDtoClass) {
		Map<Integer, Class<?>> mapLevelDtoClass = new HashMap<>();
		mapLevelDtoClass.put(0, mainDtoClass);
		levelDtoClassMapOfMaps.put(mainDtoClass, mapLevelDtoClass);
	}
	
    public <DTO, D extends DTO> void setDtoClassForLevel(Class<DTO> mainDtoClass, Integer detailLevel , Class<D> dtoClass ) {
    	initMainDtoClassDefaultMapIfNecessary(mainDtoClass);
    	(levelDtoClassMapOfMaps.get(mainDtoClass)).put(detailLevel, dtoClass);
	}
    
    private void initMainDtoClassDefaultMapIfNecessary(Class<?> mainDtoClass) {
    	Map<Integer, Class<?>> mapLevelDtoClass = levelDtoClassMapOfMaps.get(mainDtoClass);
    	if(mapLevelDtoClass==null) {
    		setMainDtoClass(mainDtoClass);
    	}
	}

}

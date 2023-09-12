package org.mycontrib.util.generic.rest;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.mycontrib.util.generic.dto.DtoByLevelUtil;
import org.mycontrib.util.generic.dto.Message;
import org.mycontrib.util.generic.dto.WithId;
import org.mycontrib.util.generic.exception.NotFoundException;
import org.mycontrib.util.generic.service.GenericServiceWithDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class AbstractGenericRestCtrl<ID extends Serializable,
                                                            DTO extends WithId<ID>> {
	
	/*
	 NB: au sein de cette classe abstraite , tous les mappings sont volontairement
	     désactivés (en commentaire) , pour que l'on puisse les redéfinir dans une sous classe
	     (avec par exemple @PreAuthorize("hasRole('ROLE_ADMIN')") en plus)
	     et cela sans engendrer des ambiguités de mapping
	 */
	
	private GenericServiceWithDto<ID>serviceWithDto;
	private Class<DTO> mainDtoClass;
	
	
	public abstract GenericServiceWithDto<ID> getGenericServiceWithDto();
	public abstract Class<DTO> getMainDtoClass(); //this abstract method
	 //can also call DtoByLevelUtil.INSTANCE.setDtoClassForLevel(mainDtoClass, 1or2, XyzL1or2.class);
	
	@PostConstruct
	public void init() {
		this.serviceWithDto=getGenericServiceWithDto();
		this.mainDtoClass=getMainDtoClass();
	}
	

	public DTO internalGetDtoById(ID id , Integer detailLevel) throws NotFoundException {
		Class<? extends WithId<ID>> dtoClass = DtoByLevelUtil.INSTANCE.getDtoSubClassByLevel(mainDtoClass, detailLevel);
		return (DTO)serviceWithDto.searchDtoById(id,dtoClass); //may throwing NotFoundException
	}
	

	public ResponseEntity<?> internalDeleteDtoById(ID id) {
		serviceWithDto.deleteById(id); //may throw NotFoundException
		return new ResponseEntity<Message>(new Message("suppression bien effectué"), HttpStatus.OK); // OK:200
		// return new ResponseEntity<Message>( HttpStatus.NO_CONTENT); //NO_CONTENT:204
	}

}

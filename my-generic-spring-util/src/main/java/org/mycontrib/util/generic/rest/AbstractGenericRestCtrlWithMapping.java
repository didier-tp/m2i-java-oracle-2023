package org.mycontrib.util.generic.rest;

import java.io.Serializable;

import org.mycontrib.util.generic.dto.WithId;
import org.mycontrib.util.generic.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public abstract class AbstractGenericRestCtrlWithMapping<ID extends Serializable,DTO extends WithId<ID>>  
                    extends AbstractGenericRestCtrl<ID,DTO>{
	
	
	// URL= ./rest/api-xxx/DTO/1_or_other_id
	//   or ./rest/api-xxx/DTO/1?detailLevel=1ou2ouAutre
	@GetMapping("/{id}")
	public DTO getDtoById(@PathVariable("id") ID id,
			@RequestParam(value="detailLevel",required=false) Integer detailLevel) throws NotFoundException {
		return super.internalGetDtoById(id,detailLevel); //may throwing NotFoundException
	}
	

	// URL= ./rest/api-xxx/DTO/1_or_other_id
	// appelé en mode DELETE
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteDtoById(@PathVariable("id") ID id) {
		return super.internalDeleteDtoById(id);
	}


}

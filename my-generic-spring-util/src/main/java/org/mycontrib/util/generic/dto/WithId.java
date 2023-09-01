package org.mycontrib.util.generic.dto;

import java.io.Serializable;

public interface WithId<ID extends Serializable> {
	
	public ID getId(); //NB: may need of @JSonIgnore over implementation
					   //if @Id name is different than "id"
	                   //to avoid duplicate id in JSON

}

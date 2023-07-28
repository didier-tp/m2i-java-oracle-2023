package com.inetum.appliSpringJpa.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
public class LigneCommandePK implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long numCmde;
	private Integer numLigne;
	
}

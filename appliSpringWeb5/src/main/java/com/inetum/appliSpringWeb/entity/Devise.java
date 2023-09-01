package com.inetum.appliSpringWeb.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity

@Getter @Setter @NoArgsConstructor @ToString
public class Devise {
	@Id
	private String code;
	
	private String nom;
	
	private Double change;

	public Devise(String code, String nom, Double change) {
		super();
		this.code = code;
		this.nom = nom;
		this.change = change;
	}
	
	

}

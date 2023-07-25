package com.inetum.appliSpringWeb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * DTO = Data Transfert Object
 * Objet de données transféré à travers le réseau 
 * (ou entre couches logicielles)
 */

@Getter @Setter @ToString @NoArgsConstructor
public class Devise {
	private String code; //ex: USD , EUR
	private String nom ; //ex: dollar, euro
	private Double change; //change pour 1 euro
	
	public Devise(String code, String nom, Double change) {
		super();
		this.code = code;
		this.nom = nom;
		this.change = change;
	}

	
}

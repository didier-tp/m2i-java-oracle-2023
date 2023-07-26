package com.inetum.appliSpringWeb.dto;


import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description="Devise/Monnaie")
public class Devise {
	
	@Schema(description = "code de la devise" , defaultValue = "EUR")
	private String code; //ex: USD , EUR
	
	@Schema(description = "nom de la devise" , defaultValue = "euro")
	private String nom ; //ex: dollar, euro
	
	@Schema(description = "change (nb unité pour 1 euro)" , defaultValue = "1.0")
	private Double change; //change pour 1 euro
	
	public Devise(String code, String nom, Double change) {
		super();
		this.code = code;
		this.nom = nom;
		this.change = change;
	}

	
}

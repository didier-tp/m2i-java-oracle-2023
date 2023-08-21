package com.inetum.appliSpringWeb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * DTO = Data Transfert Object
 * CompteDto sera une représentation partielle simplifiée et adaptée
 * de entity.Compte
 * 
 * CompteDto ici en version "Essentiel / Basic"
 */

//@Getter @Setter @NoArgsConstructor @ToString
@Data @NoArgsConstructor
public class CompteDto {

    private Long numero;
	private String label;
	private Double solde;
	
	public CompteDto(Long numero, String label, Double solde) {
		super();
		this.numero = numero;
		this.label = label;
		this.solde = solde;
	}
	
	

}

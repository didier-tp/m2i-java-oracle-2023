package com.inetum.appliSpringWeb.dto;

import org.mycontrib.util.generic.dto.WithId;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * DTO = Data Transfert Object
 * CompteL0 sera une représentation partielle simplifiée et adaptée
 * de entity.Compte
 * 
 * CompteL0 ici en version "Basic"
 */

//@Getter @Setter @NoArgsConstructor @ToString
@Data @NoArgsConstructor
public class CompteL0 implements WithId<Long> {

    private Long numero;
	private String label;
	private Double solde;
	
	public CompteL0(Long numero, String label, Double solde) {
		super();
		this.numero = numero;
		this.label = label;
		this.solde = solde;
	}

	@Override
	@JsonIgnore
	public Long getId() {
		return this.numero;
	}

}

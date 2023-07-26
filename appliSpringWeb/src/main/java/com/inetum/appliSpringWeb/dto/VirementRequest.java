package com.inetum.appliSpringWeb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor
public class VirementRequest {
	private Long numCompteDebit;
	private Long numCompteCredit;
	private Double montant;

}

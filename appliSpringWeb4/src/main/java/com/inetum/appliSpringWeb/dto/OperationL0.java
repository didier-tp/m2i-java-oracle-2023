package com.inetum.appliSpringWeb.dto;

import java.util.Date;

import org.mycontrib.util.generic.dto.WithId;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * DTO = Data Transfert Object
 * OperationDto sera une représentation partielle simplifiée et adaptée
 * de entity.Operation
 * 
 * OperationDto ici en version "Essentiel / Basic"
 */

//@Getter @Setter @NoArgsConstructor @ToString
@Data @NoArgsConstructor
public class OperationL0 implements WithId<Long>{

    private Long idOp;
	private String label;
	private Double montant;
	private Date date;
	public OperationL0(Long idOp, String label, Double montant, Date date) {
		super();
		this.idOp = idOp;
		this.label = label;
		this.montant = montant;
		this.date = date;
	}
	
	@Override
	@JsonIgnore
	public Long getId() {
		return idOp;
	}
	
	
	
	

}

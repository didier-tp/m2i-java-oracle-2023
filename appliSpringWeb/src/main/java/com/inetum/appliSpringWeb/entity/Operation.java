package com.inetum.appliSpringWeb.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Getter @Setter @NoArgsConstructor
@Table(name="TOperation")
public class Operation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idOp;
	
	private Double montant;
	private String label;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dateOp")
	private Date date;
	
	//@JsonIgnore
	@ManyToOne //Many Operation To one Compte
	  @JoinColumn(name = "num_compte")
	private Compte compte;
	
	//+get/set , toString() , constructeurs

	

	public Operation(Long idOp, Double montant, String label, Date date, Compte compte) {
		super();
		this.idOp = idOp;
		this.montant = montant;
		this.label = label;
		this.date = date;
		this.compte = compte;
	}
	
	public Operation(Long idOp, Double montant, String label, Date date) {
		this(idOp,montant,label,date,null);
	}

	

	

	@Override
	public String toString() {
		return "Operation [idOp=" + idOp + ", montant=" + montant + ", label=" + label + ", date=" + date + "]";
	}

}

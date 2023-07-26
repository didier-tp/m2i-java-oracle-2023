package com.inetum.appliSpringJpa.entity;

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



@Entity
@Table(name="operation")
public class Operation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idOp;
	
	private Double montant;
	private String label;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dateOp")
	private Date date;
	
	@ManyToOne //Many Operation To one Compte
	  @JoinColumn(name = "num_compte")
	private Compte compte;
	
	//+get/set , toString() , constructeurs

	public Operation() {
		super();
	}

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

	public Long getIdOp() {
		return idOp;
	}

	public void setIdOp(Long idOp) {
		this.idOp = idOp;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}
	
	
	

}

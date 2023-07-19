package com.inetum.appliSpringJpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
// bientot import jakarta.persistence.Entity;

@Entity
@NamedQuery(name = "Compte.findAll", query = "SELECT c FROM Compte c")
@NamedQuery(name = "Compte.findBySoldeMini", 
            query = "SELECT c FROM Compte c WHERE c.solde>= ?1")
@NamedQuery(name = "Compte.findBySoldeMaxi", 
            query = "SELECT c FROM Compte c WHERE c.solde<= ?1")
public class Compte {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long numero;
	
	private String label;
	
	private Double solde;
	
	
	

	public Compte(Long numero, String label, Double solde) {
		super();
		this.numero = numero;
		this.label = label;
		this.solde = solde;
	}
	
	

	public Compte() {
		super();
	}



	@Override
	public String toString() {
		return "Compte [numero=" + numero + ", label=" + label + ", solde=" + solde + "]";
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Double getSolde() {
		return solde;
	}

	public void setSolde(Double solde) {
		this.solde = solde;
	}
	
	
	

}

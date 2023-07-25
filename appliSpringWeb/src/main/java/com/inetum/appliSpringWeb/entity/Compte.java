package com.inetum.appliSpringWeb.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NamedQuery(name = "Compte.findAll", query = "SELECT c FROM Compte c")
@NamedQuery(name = "Compte.findBySoldeMini", 
            query = "SELECT c FROM Compte c WHERE c.solde>= ?1")
@NamedQuery(name = "Compte.findBySoldeMaxi", 
            query = "SELECT c FROM Compte c WHERE c.solde<= ?1")
@Getter @Setter @NoArgsConstructor
public class Compte {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long numero;
	
	private String label;
	
	private Double solde;
	
	
	

	public Compte(Long numero, String label, Double solde) {
		super();
		this.numero = numero;
		this.label = label;
		this.solde = solde;
	}



	@Override
	public String toString() {
		return "Compte [numero=" + numero + ", label=" + label + ", solde=" + solde + "]";
	}
	
	
	

}

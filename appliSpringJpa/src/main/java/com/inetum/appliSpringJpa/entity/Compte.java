package com.inetum.appliSpringJpa.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
// bientot import jakarta.persistence.Entity;

@Entity
@NamedQuery(name = "Compte.findAll", query = "SELECT c FROM Compte c")
@NamedQuery(name = "Compte.findBySoldeMini", 
            query = "SELECT c FROM Compte c WHERE c.solde>= ?1")
@NamedQuery(name = "Compte.findBySoldeMaxi", 
            query = "SELECT c FROM Compte c WHERE c.solde<= ?1")
@NamedQuery(name = "Compte.findCompteWithOperationsById", 
        query = "SELECT c FROM Compte c LEFT JOIN FETCH c.operations op WHERE c.numero = ?1")
@NamedQuery(name="Compte.findComptesOfClient" ,
             query="SELECT c FROM Compte c WHERE c.client.numero = ?1")
public class Compte {

	/*
	 NB: dans une query JPQL le mot clef fetch permet de remonter tous les 
	 éléments de la collection en mémoire (un peu comme un "eager" sur demande)
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long numero;
	
	private String label;
	
	private Double solde;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "compte" , cascade = CascadeType.ALL)
	//@OneToMany(fetch = FetchType.EAGER, mappedBy = "compte")
	private List<Operation> operations; //+get/set
	
	public void addOperation(Operation op) {
		if(this.operations == null) this.operations = new ArrayList<>();
		this.operations.add(op);
	}
	
	@ManyToOne()
	@JoinColumn(name = "numero_client")
	private Client client;//+get/set
	
	

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



	public List<Operation> getOperations() {
		return operations;
	}



	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}



	public Client getClient() {
		return client;
	}



	public void setClient(Client client) {
		this.client = client;
	}
	
	
	
	
	

}

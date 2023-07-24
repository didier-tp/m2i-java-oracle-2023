package com.inetum.appliSpringJpa.entity;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
// bientot import jakarta.persistence.Entity;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c")
@NamedQuery(name = "Client.findClientWithComptesById", 
query = "SELECT c FROM Client c LEFT JOIN FETCH c.comptes cpt WHERE c.numero = ?1")
@DiscriminatorValue(value = "Client") //valeur pour colonne "typePersonne"
public class Client extends Personne{
	
	
	//@OneToMany(fetch = FetchType.LAZY , mappedBy = "client")
	@ManyToMany(fetch = FetchType.LAZY , mappedBy = "clients")
	private List<Compte> comptes;//+get/set
	
	
	@Override
	public String toString() {
		return "Client " + super.toString();
	}

	public Client() {
		super();
	}

	public Client(Long numero, String prenom, String nom) {
		super(numero,prenom,nom);
	}
	
	

	public List<Compte> getComptes() {
		return comptes;
	}

	public void setComptes(List<Compte> comptes) {
		this.comptes = comptes;
	}

	

}

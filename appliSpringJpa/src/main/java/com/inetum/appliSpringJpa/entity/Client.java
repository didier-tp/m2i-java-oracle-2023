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
//@DiscriminatorValue(value = "Client") //valeur pour colonne "typePersonne" 
//valeur par défaut = "Client" nom de la classe courante 
public class Client extends Personne{
	
	
	//@OneToMany(fetch = FetchType.LAZY , mappedBy = "client")
	@ManyToMany(fetch = FetchType.LAZY , mappedBy = "clients")
	private List<Compte> comptes;//+get/set
	
	
	private String telephone="0102030405"; //numero de téléphone par défaut
	
	
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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	

}

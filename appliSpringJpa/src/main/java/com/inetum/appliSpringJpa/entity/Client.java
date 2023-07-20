package com.inetum.appliSpringJpa.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
// bientot import jakarta.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c")
@NamedQuery(name = "Client.findClientWithComptesById", 
query = "SELECT c FROM Client c LEFT JOIN FETCH c.comptes cpt WHERE c.numero = ?1")
public class Client {
	
	//@Id : id / primary_key
	//@GeneratedValue : pour que la valeur auto incrémentée par la base 
	//remonte bien en mémoire dans l'objet java lors du entityManger.persist()
	//.numero null puis plus null
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long numero;
	
	@Column(name="prenom" , length=64)
	private String prenom;
	
	
	private String nom;
	
	@OneToMany(fetch = FetchType.LAZY , mappedBy = "client")
	private List<Compte> comptes;//+get/set
	
	
	@Override
	public String toString() {
		return "Employe [numero=" + numero + ", prenom=" + prenom + ", nom=" + nom + "]";
	}

	public Client() {
		super();
	}

	public Client(Long numero, String prenom, String nom) {
		super();
		this.numero = numero;
		this.prenom = prenom;
		this.nom = nom;
	}
	
	public Long getNumero() {
		return numero;
	}
	public void setNumero(Long numero) {
		this.numero = numero;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<Compte> getComptes() {
		return comptes;
	}

	public void setComptes(List<Compte> comptes) {
		this.comptes = comptes;
	}

	

}

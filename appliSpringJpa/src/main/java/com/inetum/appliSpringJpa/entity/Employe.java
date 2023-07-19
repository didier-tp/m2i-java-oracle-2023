package com.inetum.appliSpringJpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
// bientot import jakarta.persistence.Entity;

@Entity
@NamedQuery(name = "Employe.findByPrenom", 
            query = "SELECT e FROM Employe e WHERE e.prenom= ?1")
//@Table(name="employe")
public class Employe {
	
	//@Id : id / primary_key
	//@GeneratedValue : pour que la valeur auto incrémentée par la base 
	//remonte bien en mémoire dans l'objet java lors du entityManger.persist()
	//.numero null puis plus null
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long numero;
	
	@Column(name="prenom" , length=64)
	private String prenom;
	
	
	private String nom;
	
	private String email;
	
	
	
	@Override
	public String toString() {
		return "Employe [numero=" + numero + ", prenom=" + prenom + ", nom=" + nom + "]";
	}

	public Employe() {
		super();
	}

	public Employe(Long numero, String prenom, String nom) {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	

}

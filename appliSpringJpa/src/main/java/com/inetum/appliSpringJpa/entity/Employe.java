package com.inetum.appliSpringJpa.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
// bientot import jakarta.persistence.Entity;

@Entity
@NamedQuery(name = "Employe.findByPrenom", 
            query = "SELECT e FROM Employe e WHERE e.prenom= ?1")
//@Table(name="employe")
@DiscriminatorValue(value = "Employe") //valeur pour colonne "typePersonne"
public class Employe extends Personne{
	
	
	private String email;
	
	
	@Override
	public String toString() {
		return "Employe " + super.toString() ; 
	}

	public Employe() {
		super();
	}

	public Employe(Long numero, String prenom, String nom) {
		super(numero,prenom, nom);
	}
	
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	

}

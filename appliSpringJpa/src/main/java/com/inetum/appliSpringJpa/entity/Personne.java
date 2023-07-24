package com.inetum.appliSpringJpa.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="typePersonne" , discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "Personne") //valeur pour colonne "typePersonne"
public class Personne {
	
			
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		//@GeneratedValue(strategy = GenerationType.SEQUENCE)
		private Long numero;
		
		@Column(name="prenom" , length=64)
		private String prenom;
		
		
		private String nom;
		
		
		public Personne() {
			super();
		}


		public Personne(Long numero, String prenom, String nom) {
			super();
			this.numero = numero;
			this.prenom = prenom;
			this.nom = nom;
		}


		@Override
		public String toString() {
			return "Personne [numero=" + numero + ", prenom=" + prenom + ", nom=" + nom + "]";
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
		
		

}

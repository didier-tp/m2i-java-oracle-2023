package com.inetum.appliSpringWeb.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity

@NamedQuery(name = "Compte.findBySoldeMini", 
            query = "SELECT c FROM Compte c WHERE c.solde>= ?1")
@NamedQuery(name = "Compte.findByIdWithOperations" ,
		    query = "SELECT c FROM Compte c LEFT JOIN FETCH c.operations WHERE c.numero= ?1")
//NB: le mot clef FETCH permet de remonter tout de suite en mémoire
//les éléments de la collection des operations (même en mode LAZY imposé par spring-data)
@Getter @Setter @NoArgsConstructor
public class Compte {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long numero;
	
	/*
	 NB: strategy = GenerationType.IDENTITY convient bien à 
	     H2,MYSQL/MariaDB,POSTGRESQL et ORACLE>=12 (Oracle12cDialect)
	     
	     strategy = GenerationType.SEQUENCE est obligatoire pour
	     ORACLE<=11 (OracleDialect) , au lieu de préciser cela dans les 
	     annotations de la classe entity.Compte.java , 
	     on peut préciser cela dans le fichier 
	     src/main/resources/oracle-orm.xml (configuration xml prioritaire sur config @...)
	     avec spring.jpa.mapping-resources=oracle-orm.xml
	     dans src/main/resources/application-oracle.properties (config d'un profile SpringBoot)
	 */
	
	/*
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE , 
	               generator ="COMPTE_SEQ_GENERATOR")
	@SequenceGenerator(name = "COMPTE_SEQ_GENERATOR", 
	                   sequenceName = "COMPTE_SEQ",
	                   initialValue = 10 , allocationSize=1 )	                   
	private Long numero;
	*/
	
	private String label;
	
	private Double solde;
	
	@JsonIgnore //pour demander à ignorer la liste des operations
	            //lorsque l'objet courant de la classe Compte
	            //sera automatiquement converti de java en json
	            //AUTRE SOLUTION : n'utiliser que des DTO au niveau des webServices REST
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "compte" , cascade = CascadeType.ALL)
	//@OneToMany(fetch = FetchType.EAGER, mappedBy = "compte")
	private List<Operation> operations; //+get/set
	
	

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

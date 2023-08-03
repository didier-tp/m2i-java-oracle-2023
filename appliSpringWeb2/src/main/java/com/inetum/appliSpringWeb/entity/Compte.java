package com.inetum.appliSpringWeb.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

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
	
	@Transient //@Transient (de javax.persistence) signifie 
	//ne pas sauvegarder le champ en base de doonnes
	private static Double decouvertAutorise = -500.0;
	
	public static Double getDecouvertAutorise() {
		return decouvertAutorise;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long numero;
	

	private String label;
	
	private Double solde;
	
	@ManyToOne
	//@JoinColumn(name = "id_customer") //pas evolutif vers many-many
	@JoinTable(name="CompteCustomer",
		joinColumns={@JoinColumn(name="num_compte")},
		inverseJoinColumns={@JoinColumn(name="id_customer")})
	private Customer customer;
	
	//@JsonIgnore 
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

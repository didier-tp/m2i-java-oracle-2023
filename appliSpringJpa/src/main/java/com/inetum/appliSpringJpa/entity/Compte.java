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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
               query="SELECT c FROM Compte c LEFT JOIN c.clients cli WHERE  cli.numero = ?1")
//             query="SELECT c FROM Compte c WHERE c.client.numero = ?1")
public class Compte {

	/*
	 NB: dans une query JPQL le mot clef fetch permet de remonter tous les 
	 éléments de la collection en mémoire (un peu comme un "eager" sur demande)
	 */
	
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
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "compte" , cascade = CascadeType.ALL)
	//@OneToMany(fetch = FetchType.EAGER, mappedBy = "compte")
	private List<Operation> operations; //+get/set
	
	public void addOperation(Operation op) {
		if(this.operations == null) this.operations = new ArrayList<>();
		this.operations.add(op);
	}
	
	/*
	@ManyToOne()
	@JoinColumn(name = "numero_client")
	private Client client;//+get/set
	*/
	
	@ManyToMany
	@JoinTable(name = "compte_client",
	          joinColumns = {@JoinColumn(name = "num_compte")},
	          inverseJoinColumns = {@JoinColumn(name = "num_client")})
	private List<Client> clients = new ArrayList<>(); //+get/set
	

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



	public List<Client> getClients() {
		return clients;
	}



	public void setClients(List<Client> clients) {
		this.clients = clients;
	}


/*
	public Client getClient() {
		return client;
	}



	public void setClient(Client client) {
		this.client = client;
	}
	*/
	
	
	
	
	
	

}

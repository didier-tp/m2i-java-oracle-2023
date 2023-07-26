package com.inetum.appliSpringJpa.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class AdresseDePersonne {
	
	@Id
	//pas de @GeneratedValue ici mais @MapIds ou  @PrimaryKeyJoinColumn
	//au dessus de .compte
	private Long id;
	
	private String rue;
	private String codePostal;
	private String ville;
	
	@OneToOne(optional=false)
	//@PrimaryKeyJoinColumn
	 @MapsId //mieux que @PrimaryKeyJoinColumn
	   	@JoinColumn(name = "id")
	private Client client;
	
	//...
	//+get/set , .toString(), constructeurs
	
	
	
	@Override
	public String toString() {
		return "Adresse [id=" + id + ", rue=" + rue + ", codePostal=" + codePostal + ", ville=" + ville + "]";
	}
	
	public AdresseDePersonne(Long id, String rue, String codePostal, String ville , Client client) {
		super();
		this.id = id;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.client = client;
	}
	
	

	public AdresseDePersonne() {
		super();
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public String getRue() {
		return rue;
	}
	public void setRue(String rue) {
		this.rue = rue;
	}
	public String getCodePostal() {
		return codePostal;
	}
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	
	
	
	
	
}

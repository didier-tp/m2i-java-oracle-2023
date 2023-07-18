package com.inetum.mbean;

//import jakarta.annotation.ManagedBean;
//import jakarta.enterprise.context.RequestScoped;

import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
//@SessionScoped
public class CalculTva {
    private Double ht; //à saisir
    private Double tauxTva; // en pourcentage, à saisir ou choisir
    private Double tva; //à calculer et afficher
    private Double ttc; //à calculer et afficher
    
    public CalculTva() {
    	this.tauxTva = 20.0; //par défaut
    }
    
    public String calculerTvaEtTtc() {
    	String suite=null; //rester sur même page
    	this.tva = this.ht * this.tauxTva / 100;
    	this.ttc = this.ht + this.tva;
    	return suite;
    }
    
    
	public Double getHt() {
		return ht;
	}
	public void setHt(Double ht) {
		this.ht = ht;
	}
	public Double getTauxTva() {
		return tauxTva;
	}
	public void setTauxTva(Double tauxTva) {
		this.tauxTva = tauxTva;
	}
	public Double getTva() {
		return tva;
	}
	public void setTva(Double tva) {
		this.tva = tva;
	}
	public Double getTtc() {
		return ttc;
	}
	public void setTtc(Double ttc) {
		this.ttc = ttc;
	}
    
    
}

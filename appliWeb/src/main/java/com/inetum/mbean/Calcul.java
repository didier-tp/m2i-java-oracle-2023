package com.inetum.mbean;

import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.RequestScoped;

@ManagedBean
@RequestScoped
public class Calcul {
	
	private Double x; //à saisir
	
	private Double res; //resultat calcul de racine carree
	
	public String calculerRacineCarree() {
		String suite=null;//ou "page2" ou ..
		this.res = Math.sqrt(this.x);
		return suite; //si null , rester sur même page
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getRes() {
		return res;
	}

	public void setRes(Double res) {
		this.res = res;
	}
	
	

}

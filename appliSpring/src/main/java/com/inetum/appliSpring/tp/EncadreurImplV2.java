package com.inetum.appliSpring.tp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EncadreurImplV2 implements Encadreur {
	
	private Prefixeur prefixeur;
	
	private Suffixeur suffixeur;
	
	
	public EncadreurImplV2() {
		//constructeur par défaut (zéro paramètre) qui ne servira pas
	}
	
	
	@Autowired
	public EncadreurImplV2(Prefixeur prefixeur,Suffixeur sufixeur) {
		//constructeur pour injection de dépendance
		System.out.println("EncadreurImplV2 avec prefixeur="+prefixeur);
		this.prefixeur = prefixeur;
		this.suffixeur = sufixeur;
	}
	


	@Override
	public String encadrer(String chaine) {
		return suffixeur.suffixer(prefixeur.prefixer(chaine));
	}

	@Override
	public String encadrerMaj(String chaine) {
		return suffixeur.suffixerMaj(prefixeur.prefixerMaj(chaine));
	}

}

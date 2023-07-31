package com.inetum.appliSpring.tp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component
public class EncadreurImpl implements Encadreur {
	
	public EncadreurImpl() {
		System.out.println("dans constructeur , prefixeur="+this.prefixeur);//null
	}
	
	@PostConstruct
	public void  initialisation() {
		System.out.println("dans initialisation préfixée par @PostConstruct , prefixeur="
	                  +this.prefixeur);//pas null
	}
	
	@Autowired
	//@Autowired (auto-laison) demande à Spring de automatiquement 
	//initialiser .prefixeur (jusqu'ici à null) pour que ça référence
	//un composant Spring existant compatible avec l'interface Prefixeur
	private Prefixeur prefixeur;
	
	@Autowired
	private Suffixeur suffixeur;

	@Override
	public String encadrer(String chaine) {
		return suffixeur.suffixer(prefixeur.prefixer(chaine));
	}

	@Override
	public String encadrerMaj(String chaine) {
		return suffixeur.suffixerMaj(prefixeur.prefixerMaj(chaine));
	}

}

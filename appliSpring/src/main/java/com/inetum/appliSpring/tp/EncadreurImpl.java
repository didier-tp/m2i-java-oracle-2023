package com.inetum.appliSpring.tp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EncadreurImpl implements Encadreur {
	
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

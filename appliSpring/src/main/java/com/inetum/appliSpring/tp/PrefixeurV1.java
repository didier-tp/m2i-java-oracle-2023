package com.inetum.appliSpring.tp;

import org.springframework.stereotype.Component;

//@Lazy //très rare (pour charger en mémoire un composant spring que lorsque l'on a besoin)
//PAR DEFAUT : tous les composants Spring sont créés dès le démarrage de l'application
//@Component() //pour que cette classe soit prise en charge par Spring
//@Scope("singleton") //par défaut avec spring "objet/composant de traitement" 
//créé en un seul exemplaire réutilisé
//@Component("prefixeurV1QueJaime") //id spécifique "prefixeurV1QueJaime" 
@Component//id par défaut = "prefixeurV1" 
//(nom de la classe avec minuscule au début)
public class PrefixeurV1 implements Prefixeur {
	
	private String prefixe="**";

	@Override
	public String prefixer(String chaine) {
		return prefixe+chaine;
	}

	@Override
	public String prefixerMaj(String chaine) {
		return prefixe + chaine.toUpperCase();
	}

}

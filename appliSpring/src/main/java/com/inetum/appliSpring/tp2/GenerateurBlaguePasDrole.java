package com.inetum.appliSpring.tp2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@Profile("ProfilePasDrole") //à choisir au démarrage
@Qualifier("PasDrole") //pouvant coexister à l'exécution
public class GenerateurBlaguePasDrole implements GenerateurBlague {
	
	private List<String> listeBlagues = new ArrayList<>();
	
	public GenerateurBlaguePasDrole(){
		listeBlagues.add("peux tu porter six troncs ?\n"
		        + "Quoi tu ne peux pas porter citron (pas drôle) ?");
		listeBlagues.add("un chien qui court , un mur et ... \n" 
                + "paf le chien (pas drôle pour le chien)");
		listeBlagues.add("un hélicoptère , une girafe et ... \n" 
                + "tchac tchac la girafe (pas drôle pour la girafe)");
	}

	@Override
	public String nouvelleBlague() {
		int nbBlagues = listeBlagues.size();
		int indexAleatoire = (int)Math.round(Math.random() * (nbBlagues -1));
		return listeBlagues.get(indexAleatoire);
	}

	
}

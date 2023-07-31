package com.inetum.appliSpring.tp2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class GenerateurBlagueDrole implements GenerateurBlague {
	
	private List<String> listeBlagues = new ArrayList<>();
	
	public GenerateurBlagueDrole(){
		listeBlagues.add("qu'est ce qui est jaune et qui attend ?\n"+
	                     "Jonathan");
		listeBlagues.add("je voulais aller à calais \n" 
		                + "mais j'ai fais demi tour devant de panneau 'pas de calais'");
		listeBlagues.add("peux tu porter six troncs ?\n"
				        + "Quoi tu ne peux pas porter citron ?");
		listeBlagues.add("pourquoi le formateur porte des lunettes de soleil ? \n" 
		                + "parce que les stagiaires sont brillants");
	}

	@Override
	public String nouvelleBlague() {
		int nbBlagues = listeBlagues.size();
		int indexAleatoire = (int)Math.round(Math.random() * (nbBlagues -1));
		return listeBlagues.get(indexAleatoire);
	}

	
}

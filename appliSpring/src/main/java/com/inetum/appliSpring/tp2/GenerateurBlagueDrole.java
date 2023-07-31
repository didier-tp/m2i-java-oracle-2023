package com.inetum.appliSpring.tp2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
//@Profile("ProfileDrole")
@Qualifier("Drole") //pouvant coexister à l'exécution
public class GenerateurBlagueDrole implements GenerateurBlague {
	
	private List<String> listeBlagues = new ArrayList<>();
	
	public GenerateurBlagueDrole(){
		listeBlagues.add("qu'est ce qui est jaune et qui attend ?\n"+
	                     "Jonathan");
		listeBlagues.add("je voulais aller à calais \n" 
		                + "mais j'ai fais demi tour devant de panneau 'pas de calais'");
		listeBlagues.add("Qu'est ce qu'un chameau à 3 bosses ? \n" 
                        + "un chameau qui s'est cogné");
		listeBlagues.add("Quel est l'animal le plus malheureux ? \n" 
                        + "le taureau car sa femme est vache");
		listeBlagues.add("Qu'est ce qui commence par un e se termine par e \n" 
				         + " et comporte souvent qu'une seule lettre ? \n" 
                         + " une enveloppe");
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

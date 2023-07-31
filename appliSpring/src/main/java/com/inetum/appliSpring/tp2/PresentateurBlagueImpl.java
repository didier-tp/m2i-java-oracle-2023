package com.inetum.appliSpring.tp2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class PresentateurBlagueImpl implements PresentateurBlague {
	
	@Autowired @Qualifier("Drole")
	private GenerateurBlague generateurBlagueDrole; 
	
	@Autowired @Qualifier("PasDrole")
	private GenerateurBlague generateurBlaguePasDrole;
	
	private GenerateurBlague generateurBlagueCourant =  null;
	
	/*
	@Autowired
	public PresentateurBlagueImpl(GenerateurBlague @Qualifier("Drole") generateurBlagueDrole , 
			                 GenerateurBlague @Qualifier("PasDrole") generateurBlaguePasDrole ) {
		this.generateurBlagueDrole = generateurBlagueDrole;
		this.generateurBlaguePasDrole = generateurBlaguePasDrole;
	}
	*/

	@Override
	public void presenterBlague() {
		//une fois sur 2 blague "Drole" ou "PasDrole" :
		if(generateurBlagueCourant==null || generateurBlagueCourant== generateurBlagueDrole) {
			generateurBlagueCourant=generateurBlaguePasDrole;
		}else {
			generateurBlagueCourant = generateurBlagueDrole;
		}
		
		System.out.println("Encore une petite blague="
				+ generateurBlagueCourant.nouvelleBlague());

	}

}

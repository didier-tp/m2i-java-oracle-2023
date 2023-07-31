package com.inetum.appliSpring.tp2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PresentateurBlagueImpl implements PresentateurBlague {
	
	@Autowired
	private GenerateurBlague generateurBlague; 

	@Override
	public void presenterBlague() {
		System.out.println("Encore une petite blague="
				+ generateurBlague.nouvelleBlague());

	}

}

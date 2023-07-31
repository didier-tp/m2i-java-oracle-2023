package com.inetum.appliSpring.tp;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component//id par défaut = "suffixeurV1" 
@Profile("V2")
public class SuffixeurV2 implements Suffixeur {
	
	private String suffixe="##";

	@Override
	public String suffixer(String chaine) {
		return chaine + suffixe;
	}

	@Override
	public String suffixerMaj(String chaine) {
		return chaine.toUpperCase() + suffixe;
	}

}

package com.inetum.appliSpring.tp;

import org.springframework.stereotype.Component;

@Component //pour que cette classe soit prise en charge par Spring
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

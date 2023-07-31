package com.inetum.appliSpring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.inetum.appliSpring.tp.Prefixeur;

public class AppliSpringApplication {

	public static void main(String[] args) {
		
		AnnotationConfigApplicationContext springContext = new
				AnnotationConfigApplicationContext(MySpringConfig.class);
		//NB: dès la création de springContext via une configuration xml ou java
		//toutes les instances des composants spring nécessaires sont créées et initailisées en 
		//mémoire
		
		//la méthode .getBean() permet d'obtenir une référence sur
		//un des composants pris en charge par spring
		Prefixeur prefixeur = springContext.getBean(Prefixeur.class);
		
		System.out.println("appliSpring démarrée");
		System.out.println("chaine prefixee=" + prefixeur.prefixer("lundi"));
		
		springContext.close();//arrêt/fermeture de Spring
	}

}

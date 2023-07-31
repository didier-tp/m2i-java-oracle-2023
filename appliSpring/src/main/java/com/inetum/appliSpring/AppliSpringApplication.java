package com.inetum.appliSpring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.inetum.appliSpring.tp.Encadreur;
import com.inetum.appliSpring.tp.Prefixeur;
import com.inetum.appliSpring.tp2.PresentateurBlague;

public class AppliSpringApplication {

	public static void main(String[] args) {
		//testEncadreur();
		testBlagues();
	}
	
	public static void testBlagues() {	
		AnnotationConfigApplicationContext springContext = new
				AnnotationConfigApplicationContext(MySpringConfig.class);
				
		PresentateurBlague  presentateurBlague= springContext.getBean(PresentateurBlague.class);
		presentateurBlague.presenterBlague();
		
		springContext.close();
	}
		
	public static void testEncadreur() {	
		AnnotationConfigApplicationContext springContext = new
				AnnotationConfigApplicationContext(MySpringConfig.class);
		//NB: dès la création de springContext via une configuration xml ou java
		//toutes les instances des composants spring nécessaires sont créées et initialisées en 
		//mémoire
		
		//.getBean(TypeSouhaite.class) ou bien .getBean("idBeanSouhaite")
		
		//la méthode .getBean() permet d'obtenir une référence sur
		//un des composants pris en charge par spring
		Prefixeur prefixeur = springContext.getBean(Prefixeur.class);
		//Prefixeur prefixeur = (Prefixeur) springContext.getBean("prefixeurV1");
		
		System.out.println("appliSpring démarrée");
		System.out.println("chaine prefixee=" + prefixeur.prefixer("lundi"));
		
		Encadreur encadreur = springContext.getBean(Encadreur.class);
		System.out.println("chaine encadrée=" + encadreur.encadrer("bob"));
		
		
		springContext.close();//arrêt/fermeture de Spring
	}

}

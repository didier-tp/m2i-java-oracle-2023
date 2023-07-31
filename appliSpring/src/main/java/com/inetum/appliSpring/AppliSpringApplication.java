package com.inetum.appliSpring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.inetum.appliSpring.tp.Encadreur;
import com.inetum.appliSpring.tp.Prefixeur;
import com.inetum.appliSpring.tp2.PresentateurBlague;

public class AppliSpringApplication {
	
	private static Logger logger = LoggerFactory.getLogger(AppliSpringApplication.class);

	public static void main(String[] args) {
		//testEncadreur();
		testBlagues();
	}
	
	public static void testBlagues() {	
		
		System.setProperty("spring.profiles.active", "V1,eventuelProfilComplementaire");
		//System.setProperty("spring.profiles.active", "V2,eventuelProfilComplementaire");
				
		AnnotationConfigApplicationContext springContext = new
				AnnotationConfigApplicationContext(MySpringConfig.class);
				
		PresentateurBlague  presentateurBlague= springContext.getBean(PresentateurBlague.class);
		//plusieurs appels pouvoir avoir une fois sur 2 blagues drôles ou pas
		presentateurBlague.presenterBlague();
		presentateurBlague.presenterBlague();
		presentateurBlague.presenterBlague();
		presentateurBlague.presenterBlague();
		
		springContext.close();
	}
		
	public static void testEncadreur() {	
		
		//System.setProperty("spring.profiles.active", "V1,eventuelProfilComplementaire");
		System.setProperty("spring.profiles.active", "V2,eventuelProfilComplementaire");
		//ou bien java .... -Dspring.profiles.active=V1,... dans un .bat ou .sh
		
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
		
		logger.debug("appliSpring démarrée");
		System.out.println("chaine prefixee=" + prefixeur.prefixer("lundi"));
		
		Encadreur encadreur = springContext.getBean(Encadreur.class);
		System.out.println("chaine encadrée=" + encadreur.encadrer("bob"));
		
		
		springContext.close();//arrêt/fermeture de Spring
	}

}

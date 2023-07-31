package com.inetum.appliSpring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.inetum.appliSpring.tp.Prefixeur;

public class AppliSpringApplication {

	public static void main(String[] args) {
		
		
		AnnotationConfigApplicationContext springContext = new
				AnnotationConfigApplicationContext(MySpringConfig.class);
		Prefixeur prefixeur = springContext.getBean(Prefixeur.class);
		System.out.println("appliSpring démarrée");
		System.out.println("chaine prefixee=" + prefixeur.prefixer("lundi"));
		
		springContext.close();
	}

}

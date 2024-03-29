package com.inetum.appliSpringWeb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppliSpringWebApplication {

	public static void main(String[] args) {
		
		//System.setProperty("spring.profiles.active", "oracle,init");
		System.setProperty("spring.profiles.active", "h2,init");
		//System.setProperty("spring.profiles.active", "init");//pour test docker avec mycontainer_mysql
		SpringApplication.run(AppliSpringWebApplication.class, args);
		
		/*
		SpringApplication app = new SpringApplication(AppliSpringWebApplication.class);
		app.setAdditionalProfiles("init", "oracle","profileComplementaireQueJaime") ;
		app.run(args);
		*/
		
		System.out.println("http://localhost:8181/appliSpringWeb");
	}

}

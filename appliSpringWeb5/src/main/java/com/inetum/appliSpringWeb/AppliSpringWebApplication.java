package com.inetum.appliSpringWeb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppliSpringWebApplication {
	
	public static void initActiveProfiles() {
		String activeProfilesString = System.getProperty("spring.profiles.active");
		if(activeProfilesString!=null) {
			System.out.println("spring.profiles.active is already initialized: "
		                       + activeProfilesString);
		}else {
			//activeProfilesString="oracle,init";
			//activeProfilesString="h2,init";
			activeProfilesString="h2,init,withSecurity";
			System.setProperty("spring.profiles.active", activeProfilesString);
			System.out.println("uninitialized spring.profiles.active is now set to this default value: " + activeProfilesString);
		}
	}

	public static void main(String[] args) {
		initActiveProfiles();//to call before run()
		SpringApplication.run(AppliSpringWebApplication.class, args);
		System.out.println("default (dev) url: http://localhost:8181/appliSpringWeb"); //selon application.properties
	}

}

package org.mycontrib.mysecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/*
 * si ajout de spring-boot-starter-security dans pom.xml
 * et aucune config de securité alors securité par defaut
 * username=user
 * mot_passe=a lire dans la console a chaque redémarrage de l'appli
 * 
 * -----------------
 
 extends WebSecurityConfigurerAdapter et coder .configure()
 etait la manière stable pour configurer Spring-security en Spring 4 et Spring 5
 ----
 Depuis les dernières versions >=5.7 et springBoot 2.7 , cette manière est maintenant
 considérée comme "deprecated" (car pas assez souple).
 Le code ci-dessous correspond à la nouvelle manière conseillée:
 */

@Configuration
@Profile("!withSecurity")
public class WithoutSecurityRecentConfig {
	
	@Bean
	protected SecurityFilterChain configureAndBuildSecurityFilterChain(final HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/**/*.*").permitAll()
		.and().cors() //enable CORS (avec @CrossOrigin sur class @RestController)
		.and().csrf().disable();
		return http.build();
	} 
}
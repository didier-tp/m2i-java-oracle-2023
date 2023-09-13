package org.mycontrib.mysecurity.chain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("!withSecurity")
@EnableWebSecurity
public class WithoutSecurityMainFilterChainConfig {

	@Bean
	protected SecurityFilterChain configureAndBuildSecurityFilterChain(final HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/**/*.*").permitAll()
		.and().cors() //enable CORS (avec @CrossOrigin sur class @RestController)
		.and().headers().frameOptions().sameOrigin()
		.and().csrf().disable();
		return http.build();
	} 
}

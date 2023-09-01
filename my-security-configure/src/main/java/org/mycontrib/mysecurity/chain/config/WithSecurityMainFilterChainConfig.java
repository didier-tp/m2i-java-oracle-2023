package org.mycontrib.mysecurity.chain.config;

import org.mycontrib.mysecurity.area.config.AreasConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("withSecurity")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//necessary for @PreAuthorize("hasRole('ADMIN or ...')")
public class WithSecurityMainFilterChainConfig {
	
	private static Logger logger = LoggerFactory.getLogger(WithSecurityMainFilterChainConfig.class);

	
	@Autowired
	protected AreasConfig areasConfig; //set by WebProtectedAreaConfigurer and .properties
	
	@Autowired
	protected MyFilterSimpleConfigurer myFilterSimpleConfigurer;
	
	@Bean // **** IMPORTANT *****
	protected SecurityFilterChain filterChain(HttpSecurity http/*, AuthenticationManager authenticationManager*/)
			throws Exception {

		HttpSecurity partialConfiguredHttp =
		http.authorizeRequests().antMatchers(areasConfig.getStaticWhitelist()).permitAll()
		        .antMatchers(areasConfig.getSwaggerWhitelist()).permitAll()
		        .antMatchers(HttpMethod.POST, "/api-login/public/login").permitAll()
				.antMatchers(areasConfig.getApiWhitelist()).permitAll()
				.antMatchers(HttpMethod.GET, areasConfig.getApiReadonlyWhitelist()).permitAll()
				.antMatchers(areasConfig.getApiProtectedlist()).authenticated()
				.and().cors() // enable CORS (avec @CrossOrigin sur class @RestController)
				.and().csrf().disable();
				//.authenticationManager(authenticationManager); // **** IMPORTANT *****
				

		return myFilterSimpleConfigurer.configureAndBuildEndOfSecurityChain(partialConfiguredHttp);
		
	}

}

package org.mycontrib.mysecurity.chain.config;

import org.mycontrib.mysecurity.area.config.AreasConfig;
import org.mycontrib.mysecurity.chain.properties.MySecurityChainProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("withSecurity")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//necessary for @PreAuthorize("hasRole('ADMIN or ...')")
@ConfigurationPropertiesScan("org.mycontrib.mysecurity.chain.properties")
public class WithSecurityMainFilterChainConfig {
	
	private static Logger logger = LoggerFactory.getLogger(WithSecurityMainFilterChainConfig.class);

	@Autowired(required = false)
	public MySecurityChainProperties mySecurityChainProperties;
	
	@Autowired
	protected AreasConfig areasConfig; //set by WebProtectedAreaConfigurer and .properties
	
	@Autowired(required = true)
	@Qualifier("OAuth2ResourceServer")
	protected MyFilterSimpleConfigurer myOauth2FilterSimpleConfigurer;
	
	
	@Autowired(required = true)
	@Qualifier("BasicAuth")
	protected MyFilterSimpleConfigurer myBasicAuthFilterSimpleConfigurer;
	
	@Autowired(required = false)
	@Qualifier("StandaloneJwt")
	protected MyFilterSimpleConfigurer myStandaloneJwtFilterSimpleConfigurer;
	
	
	//NB: 3 securityChain avec ordre important à respecter
	//@Order(1) pour les URL commencant par /rest (ex: /rest/api-xxx , /rest/api-yyy)
	//@Order(2) pour une éventuelle partie /site/ basée sur @Controller + JSPoyThymeleaf
	//@Order(3) pour le reste (pages static ou pas "spring")
	
	//NB: quand une requête arrive, le FilterChainProxy  de Spring-security
	//va utiliser le premier SecurityFilterChain correpondant à l'url de la requête.
	//et va ignorer les autres (NB: la correspondance se fait via httpSecurity.antMatcher() sans s
	//conventions d'URL : /rest/api-xyz/... ou /site/... ou **
	
	
	@Bean
    @Order(1)
	protected SecurityFilterChain restApiFilterChain(HttpSecurity http/*, AuthenticationManager authenticationManager*/)
			throws Exception {

		HttpSecurity partialConfiguredHttp =
		http
		     .antMatcher("/rest/**") //VERY IMPORTANT (matching for rest api and @Order(1) FilterChain)
		     .authorizeRequests()
		        /*.antMatchers(areasConfig.getStaticWhitelist()).permitAll()
		        .antMatchers(areasConfig.getSwaggerWhitelist()).permitAll()*/
		        .antMatchers(HttpMethod.POST, "/rest/api-login/public/login").permitAll()
				.antMatchers(areasConfig.getApiWhitelist()).permitAll()
				.antMatchers(HttpMethod.GET, areasConfig.getApiReadonlyWhitelist()).permitAll()
				.antMatchers(areasConfig.getApiProtectedlist()).authenticated()
				.and().cors() // enable CORS (avec @CrossOrigin sur class @RestController)
				.and().csrf().disable();
				//.authenticationManager(authenticationManager); // **** IMPORTANT *****
		
		MyFilterSimpleConfigurer myFilterSimpleConfigurer=myOauth2FilterSimpleConfigurer; //by default
		logger.debug("myStandaloneJwtFilterSimpleConfigurer="+myStandaloneJwtFilterSimpleConfigurer);
		logger.info("rest-auth-type="+mySecurityChainProperties.getRestAuthType());
		
		
		if(myStandaloneJwtFilterSimpleConfigurer!=null) {
			if(mySecurityChainProperties.getRestAuthType()!=null
					&& mySecurityChainProperties.getRestAuthType().equals("StandaloneJwt"))
			   myFilterSimpleConfigurer=myStandaloneJwtFilterSimpleConfigurer;
		}

		return myFilterSimpleConfigurer.configureAndBuildEndOfSecurityChain(partialConfiguredHttp);
		
	}
	
	@Bean
    @Order(2)
	protected SecurityFilterChain springMvcSiteFilterChain(HttpSecurity http/*, AuthenticationManager authenticationManager*/)
			throws Exception {

		return
		http
		      .antMatcher("/site/**") //VERY IMPORTANT (matching for spring mvc site part and @Order(2) FilterChain)
		      .authorizeRequests()
		        //.antMatchers( "/site/**").permitAll()
				//.antMatchers( "/site/**").authenticated()
				.antMatchers( "/site/**").denyAll()
				.and().formLogin().permitAll()
				.and().httpBasic()
				.and().csrf()
		        .and().cors().disable()
				.build();
	}
	
	@Bean
    @Order(3)
	protected SecurityFilterChain staticWebPartFilterChain(HttpSecurity http/*, AuthenticationManager authenticationManager*/)
			throws Exception {

		return
		     http.antMatcher("/**") //VERY IMPORTANT (matching for all other parts (static, ...) and @Order(3) FilterChain)
		        .authorizeRequests()
		        .antMatchers(areasConfig.getStaticWhitelist()).permitAll()
		        .antMatchers(areasConfig.getSwaggerWhitelist()).permitAll()
		        .and().csrf()
		        .and().cors().disable()
		        .build();
			
	}

}

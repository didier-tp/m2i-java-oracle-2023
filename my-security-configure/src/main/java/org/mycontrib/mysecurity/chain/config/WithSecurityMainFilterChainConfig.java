package org.mycontrib.mysecurity.chain.config;

import org.mycontrib.mysecurity.area.config.AreasConfig;
import org.mycontrib.mysecurity.chain.properties.MySecurityChainProperties;
import org.mycontrib.mysecurity.common.MyFilterChainSimpleConfigurer;
import org.mycontrib.mysecurity.common.MyRealmConfigurer;
import org.mycontrib.mysecurity.common.RealmPurposeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	protected MyFilterChainSimpleConfigurer myOauth2FilterSimpleConfigurer;
	
	
	@Autowired(required = false)
	@Qualifier("StandaloneJwt")
	protected MyFilterChainSimpleConfigurer myStandaloneJwtFilterSimpleConfigurer;
	
	@Autowired(required = false)
	MyRealmConfigurer myRealmConfigurer;
	
	
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
	protected SecurityFilterChain restApiFilterChain(HttpSecurity http)
			throws Exception {

		HttpSecurity partialConfiguredHttp =
		     http.antMatcher("/rest/**") //VERY IMPORTANT (matching for rest api and @Order(1) FilterChain)
		     .authorizeRequests()
		        /*.antMatchers(areasConfig.getStaticWhitelist()).permitAll()
		        .antMatchers(areasConfig.getSwaggerWhitelist()).permitAll()*/
		        .antMatchers(HttpMethod.POST, "/rest/api-login/public/login").permitAll()
				.antMatchers(areasConfig.getApiWhitelist()).permitAll()
				.antMatchers(HttpMethod.GET, areasConfig.getApiReadonlyWhitelist()).permitAll()
				.antMatchers(areasConfig.getApiProtectedlist()).authenticated()
				.and().cors() // enable CORS (avec @CrossOrigin sur class @RestController)
				.and().csrf().disable();

		partialConfiguredHttp = withSpecificAuthenticationManagerIfNotNull(partialConfiguredHttp,RealmPurposeEnum.rest);
	
		
		MyFilterChainSimpleConfigurer myFilterSimpleConfigurer=myOauth2FilterSimpleConfigurer; //by default
		logger.debug("myStandaloneJwtFilterSimpleConfigurer="+myStandaloneJwtFilterSimpleConfigurer);
		logger.info("rest-auth-type="+mySecurityChainProperties.getRestAuthType());
		
		
		
		if(myStandaloneJwtFilterSimpleConfigurer!=null) {
			if(mySecurityChainProperties.getRestAuthType()!=null
					&& mySecurityChainProperties.getRestAuthType().equals("StandaloneJwt"))
			   myFilterSimpleConfigurer=myStandaloneJwtFilterSimpleConfigurer;
		}

		HttpSecurity fullConfiguredHttp =  myFilterSimpleConfigurer.configureEndOfSecurityChain(partialConfiguredHttp);
		return fullConfiguredHttp.build();
		
	}
	
	@Bean
    @Order(2)
	protected SecurityFilterChain springMvcSiteFilterChain(HttpSecurity http)
			throws Exception {
		
		/*
		 IMPORTANT DEFAULT VALUE : .and().csrf()
		 tant que pas .and().csrf().disable()
		 et donc besoin de 
		     <input type="hidden"  name="${_csrf.parameterName}"  value="${_csrf.token}"/>
		 ou de
		     <form:form> ou equivalent thymeleaf
		 sinon 403 / Forbidden !!!!
		 */

	
		http=http
		      .antMatcher("/site/**") //VERY IMPORTANT (matching for spring mvc site part and @Order(2) FilterChain)
		      .authorizeRequests()
		        //.antMatchers( "/site/**").permitAll()
		        .antMatchers( "/site/login").permitAll()
		        .antMatchers( "/site/logout").permitAll()
				.antMatchers( "/site/**").authenticated()
				//.antMatchers( "/site/**").denyAll()
				.and().formLogin().loginPage("/site/login")
				
		        .and().csrf()
		        .and().cors().disable();
		
		http = withSpecificAuthenticationManagerIfNotNull(http,RealmPurposeEnum.site);
		return http.build();
		
	}
	
	/*
	 * .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/site/logout", "GET"))
	 * ok mais redirection automatique vers login?logout
	 */
	
	@Bean
    @Order(3)
	protected SecurityFilterChain staticWebPartFilterChain(HttpSecurity http)
			throws Exception {

		http=http.antMatcher("/**") //VERY IMPORTANT (matching for all other parts (static, ...) and @Order(3) FilterChain)
		        .authorizeRequests()
		        .antMatchers(areasConfig.getStaticWhitelist()).permitAll()
		        .antMatchers(areasConfig.getToolsWhitelist()).permitAll()
		        //.and().headers().frameOptions().disable() //ok for h2-console
		        .and().headers().frameOptions().sameOrigin() //ok for h2-console
		        .and().csrf().disable();//ok for h2-console
		return http.build();
			
	}
	
	@Bean //default globalAuthenticationManager
	//@ConditionalOnMissingBean(AuthenticationManager.class)
	@Qualifier("global")
	@Primary
	public AuthenticationManager globalAuthenticationManager(HttpSecurity httpSecurity)throws Exception {
		    if(myRealmConfigurer==null) return null;
			return myRealmConfigurer.getRealmAuthenticationManager(httpSecurity,RealmPurposeEnum.global);
	}
	
	@Bean //default restAuthenticationManager
	//@ConditionalOnMissingBean(AuthenticationManager.class)
	@Qualifier("rest")
	public AuthenticationManager restAuthenticationManager(HttpSecurity httpSecurity)throws Exception {
		    if(myRealmConfigurer==null) return null;
			return myRealmConfigurer.getRealmAuthenticationManager(httpSecurity,RealmPurposeEnum.rest);
	}
	
	@Bean //default siteAuthenticationManager
	//@ConditionalOnMissingBean(AuthenticationManager.class)
	@Qualifier("site")
	public AuthenticationManager siteAuthenticationManager(HttpSecurity httpSecurity)throws Exception {
		    if(myRealmConfigurer==null) return null;
			return myRealmConfigurer.getRealmAuthenticationManager(httpSecurity,RealmPurposeEnum.site);
	}
	
	private HttpSecurity withSpecificAuthenticationManagerIfNotNull(HttpSecurity http,RealmPurposeEnum realmPurpose)throws Exception {
	    if(myRealmConfigurer==null) return http;
	    AuthenticationManager authMgr =  myRealmConfigurer.getRealmAuthenticationManager(http,realmPurpose);
	    if(authMgr!=null)
	    	return http.authenticationManager(authMgr);
	    else
	    	return http;
    }

}

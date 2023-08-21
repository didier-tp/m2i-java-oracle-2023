package org.mycontrib.mysecurity.config;

import java.util.Arrays;

import org.mycontrib.mysecurity.properties.MySecurityProperties;
import org.mycontrib.mysecurity.util.JwtAuthenticationFilter;
import org.mycontrib.mysecurity.util.MyNoAuthenticationEntryPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*
 extends WebSecurityConfigurerAdapter et coder .configure()
 etait la manière stable pour configurer Spring-security en Spring 4 et Spring 5
 ----
 Depuis les dernières versions >=5.7 et springBoot 2.7 , cette manière est maintenant
 considérée comme "deprecated" (car pas assez souple).
 Le code ci-dessous correspond à la nouvelle manière conseillée:
 */

@Configuration
@Profile("withSecurity")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//necessary for @PreAuthorize("hasRole('ADMIN or ...')")
@ConfigurationPropertiesScan("org.mycontrib.mysecurity.properties")
public class WebSecurityRecentConfig {
	// plus de extends WebSecurityConfigurerAdapter (now deprecated) !!

	private static Logger logger = LoggerFactory.getLogger(WebSecurityRecentConfig.class);

	@Autowired(required = false)
	public MySecurityProperties mySecurityProperties;

	private static final String[] SWAGGER_AUTH_WHITELIST = { "/swagger-ui/**",
			"/v3/api-docs" }; // "/swagger-resources/**" ?? , "/webjars/**" ??

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private MySecuritySimpleConfigurer mySecuritySimpleConfigurer;

	@Autowired(required = false)
	@Qualifier(MySecurityExtension.MY_EXCLUSIVE_USERDETAILSSERVICE_NAME)
	UserDetailsService myExclusiveUserDetailsService;

	@Autowired(required = false)
	@Qualifier(MySecurityExtension.MY_ADDITIONAL_USERDETAILSSERVICE_NAME)
	UserDetailsService myAdditionalUserDetailsService;

	@Bean // **** IMPORTANT *****
	public AuthenticationManager authenticationManagerFromHttpSecurity(HttpSecurity http) throws Exception {
		// Configure AuthenticationManagerBuilder
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class); // **** IMPORTANT *****
		authenticationManagerBuilder.parentAuthenticationManager(null);// **** VERY VERY IMPORTANT in order to avoid
																		// infinite loop when .authenticate() fail

		logger.info("myExclusiveUserDetailsService=" + myExclusiveUserDetailsService);
		logger.info("myAdditionalUserDetailsService=" + myAdditionalUserDetailsService);
		if (myExclusiveUserDetailsService != null) {
			authenticationManagerBuilder.userDetailsService(myExclusiveUserDetailsService)
										.passwordEncoder(passwordEncoder);
		} else {
			UserDetailsManagerConfigurer udmc = null;
			MyAppGlobalUserDetailsConfigHelper myAppGlobalUserDetailsConfigHelper = new MyAppGlobalUserDetailsConfigHelper();
			if (mySecurityProperties != null && mySecurityProperties.getJdbcRealm() != null) {
				// JdbcUserDetailsManagerConfigurer if mysecurity.jdbc-realm properties not null
				DataSourceProperties dsProps = mySecurityProperties.getJdbcRealm();
				logger.info(
						"configuring jdbcRealm from mysecurity.jdbc-realm properties : " + dsProps.getDriverClassName()
								+ " , url=" + dsProps.getUrl() + " , username=" + dsProps.getUsername());
				udmc = myAppGlobalUserDetailsConfigHelper.initJdbcGlobalUserDetails(authenticationManagerBuilder,
						mySecuritySimpleConfigurer, dsProps);
			} else {
				// inMemoryAuthentication by default
				logger.info("configuring inMemoryAuthentication by default");
				udmc = myAppGlobalUserDetailsConfigHelper.initInMemoryGlobalUserDetails(authenticationManagerBuilder,
						mySecuritySimpleConfigurer);
			}
			logger.info("UserDetailsManagerConfigurer class=" + udmc.getClass().getName());
			if (myAdditionalUserDetailsService != null) {
				final ObjectPostProcessor<Object> objectPostProcessor = new ObjectPostProcessor<Object>() {
					  @Override
					  public <O extends Object> O postProcess(final O object) {
					   return object;
					  }
				};
				AuthenticationManagerBuilder authBuilder2  = new AuthenticationManagerBuilder(objectPostProcessor);
				authBuilder2.userDetailsService(myAdditionalUserDetailsService)
				            .passwordEncoder(passwordEncoder);
				AuthenticationManager additionalAuthenticationManager = authBuilder2.build();
				authenticationManagerBuilder.parentAuthenticationManager(additionalAuthenticationManager);
			}
		}

		return authenticationManagerBuilder.build(); // **** IMPORTANT *****
	}

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private MyNoAuthenticationEntryPoint unauthorizedHandler;

	// @Override protected void configure(final HttpSecurity http) throws Exception
	// {...}
	// maintenant à remplacer par
	// @Bean public SecurityFilterChain filterChain(HttpSecurity http) throws
	// Exception { ... return http.build(); }

	@Bean // **** IMPORTANT *****
	protected SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager)
			throws Exception {

		String[] staticWhitelist = { "/", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg",
				"/**/*.html", "/**/*.css", "/**/*.js" }; // default value
		String[] apiWhitelist = { "/my-api/public/**" }; // default value
		String[] apiReadonlyWhitelist = { "/my-api/readonly/**" }; // default value
		String[] apiProtectedlist = { "/my-api/private/**" }; // default value

		if (mySecurityProperties != null && mySecurityProperties.getStaticWhitelist() != null)
			staticWhitelist = mySecurityProperties.getStaticWhitelist().split(";");
		if (mySecurityProperties != null && mySecurityProperties.getWhitelist() != null)
			apiWhitelist = mySecurityProperties.getWhitelist().split(";");
		if (mySecurityProperties != null && mySecurityProperties.getReadonlyWhitelist() != null)
			apiReadonlyWhitelist = mySecurityProperties.getReadonlyWhitelist().split(";");
		if (mySecurityProperties != null && mySecurityProperties.getProtectedlist() != null)
			apiProtectedlist = mySecurityProperties.getProtectedlist().split(";");

		logger.info("staticWhitelist=" + Arrays.asList(staticWhitelist));
		logger.info("whitelist=" + Arrays.asList(apiWhitelist));
		logger.info("readonlyWhitelist=" + Arrays.asList(apiReadonlyWhitelist));
		logger.info("protectedlist=" + Arrays.asList(apiProtectedlist));

		http.authorizeRequests().antMatchers(staticWhitelist).permitAll().antMatchers(SWAGGER_AUTH_WHITELIST)
				.permitAll().antMatchers(HttpMethod.POST, "/api-login/public/login").permitAll()
				.antMatchers(apiWhitelist).permitAll()
				.antMatchers(HttpMethod.GET, apiReadonlyWhitelist).permitAll()
				.antMatchers(apiProtectedlist).authenticated()
				.and().cors() // enable CORS (avec @CrossOrigin sur class
																			// @RestController)
				.and().csrf().disable()
				// If the user is not authenticated, returns 401
				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				// This is a stateless application, disable sessions
				.authenticationManager(authenticationManager) // **** IMPORTANT *****
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				// Custom filter for authenticating users using tokens
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build(); // **** IMPORTANT *****
	}

}
package org.mycontrib.mysecurity.realm.config;

import java.util.Arrays;

import org.mycontrib.mysecurity.jwt.util.JwtAuthenticationFilter;
import org.mycontrib.mysecurity.realm.config.default_users.MySecurityDefaultUsersSimpleConfigurer;
import org.mycontrib.mysecurity.realm.properties.MySecurityRealmProperties;
import org.mycontrib.mysecurity.standalone.util.MyNoAuthenticationEntryPoint;
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


//restSecondaryRealm=InMemory or none
//none by default

//webSiteSecondaryRealm=InMemory or none
//none by default

//restApiMainRealm=Jdbc or SpecificUserDetailsService or none(if oAuth2Server) or Ldap or ...
//none by default (with oAuth2Server))

//webSiteMainRealm=Jdbc or SpecificUserDetailsService or Ldap or ...
//SpecificUserDetailsService par default 

//restApiGlobalRealm=restApiMainRealm+restSecondaryRealm
//none+none=none by default

//webSiteGlobalRealm=webSiteMainRealm+webSiteSecondaryRealm

//DefaultUsers config
//(default values in MySecurityDefaultUsersSimpleConfigurerDefaultImpl or not specific value in another specific implementation in calling project)
//defaultUsersTargets=site-(or rest-)main-(or secondary-)realm , ...

//A determiner/configurer selon ce qui existe
//ex:
//mysecurity.main-site-realm.jdbc-realm....
//...
	


@Configuration
@Profile("withSecurity")
@ConfigurationPropertiesScan("org.mycontrib.mysecurity.realm.properties")
public class MySecurityRealmConfigurer {

	private static Logger logger = LoggerFactory.getLogger(MySecurityRealmConfigurer.class);

	@Autowired(required = false)
	public MySecurityRealmProperties mySecurityRealmProperties;

	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private MySecurityDefaultUsersSimpleConfigurer mySecuritySimpleConfigurer;

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
			if (mySecurityRealmProperties != null && mySecurityRealmProperties.getJdbcRealm() != null) {
				// JdbcUserDetailsManagerConfigurer if mysecurity.jdbc-realm properties not null
				DataSourceProperties dsProps = mySecurityRealmProperties.getJdbcRealm();
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


}
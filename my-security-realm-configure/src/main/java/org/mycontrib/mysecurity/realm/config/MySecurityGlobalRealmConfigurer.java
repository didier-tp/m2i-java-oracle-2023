package org.mycontrib.mysecurity.realm.config;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.mycontrib.mysecurity.realm.config.default_users.MySecurityDefaultUsersSimpleConfigurer;
import org.mycontrib.mysecurity.realm.config.jdbc.MyJdbcRealmSubConfigurer;
import org.mycontrib.mysecurity.realm.config.memory.MyInMemoryRealmSubConfigurer;
import org.mycontrib.mysecurity.realm.properties.MySecurityRealmProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;





@Configuration
@Profile("withSecurity")
@ConfigurationPropertiesScan("org.mycontrib.mysecurity.realm.properties")
public class MySecurityGlobalRealmConfigurer {

	private static Logger logger = LoggerFactory.getLogger(MySecurityGlobalRealmConfigurer.class);

	@Autowired(required = false)
	public MySecurityRealmProperties mySecurityRealmProperties;
	
	private Map<String,AuthenticationManager> authenticationMgrMap ;
	
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private MySecurityDefaultUsersSimpleConfigurer mySecurityDefaultUsersSimpleConfigurer;

	@Bean //default global AuthenticationManager
	@ConditionalOnMissingBean(AuthenticationManager.class)
	public AuthenticationManager authenticationManager(HttpSecurity httpSecurity)throws Exception {
		initAuthenticationMgrMap(httpSecurity);
		return authenticationMgrMap.get("global");
	}
	
	
	public void initAuthenticationMgrMap(HttpSecurity httpSecurity)throws Exception {
		if(authenticationMgrMap!=null) return;
		authenticationMgrMap = new HashMap<>();
		
		MyInMemoryRealmSubConfigurer myInMemoryRealmSubConfigurer = new MyInMemoryRealmSubConfigurer(mySecurityRealmProperties,
			      authenticationMgrMap,passwordEncoder,mySecurityDefaultUsersSimpleConfigurer);
		myInMemoryRealmSubConfigurer.initInMemoryAuthenticationManagersInMap();
		
		
		MyJdbcRealmSubConfigurer myJdbcRealmSubConfigurer = new MyJdbcRealmSubConfigurer(mySecurityRealmProperties,
				      authenticationMgrMap,passwordEncoder,mySecurityDefaultUsersSimpleConfigurer);
		myJdbcRealmSubConfigurer.initJdbcAuthenticationManagersInMap();
		
		authenticationMgrMap.put("global",authenticationMgrMap.get("jdbc.global"));
	}
	
	
	
	@Bean
	public Map<String,AuthenticationManager> authenticationManagerMap(HttpSecurity httpSecurity) throws Exception {
		initAuthenticationMgrMap(httpSecurity);
		return authenticationMgrMap;
	}
	
	
	
/*
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
			MyJdbcUdmcHelper myAppGlobalUserDetailsConfigHelper = new MyJdbcUdmcHelper();
			if (mySecurityRealmProperties != null && mySecurityRealmProperties.getJdbcRealm() != null) {
				// JdbcUserDetailsManagerConfigurer if mysecurity.jdbc-realm properties not null
				DataSourceProperties dsProps = mySecurityRealmProperties.getJdbcRealm();
				logger.info(
						"configuring jdbcRealm from mysecurity.jdbc-realm properties : " + dsProps.getDriverClassName()
								+ " , url=" + dsProps.getUrl() + " , username=" + dsProps.getUsername());
				udmc = myAppGlobalUserDetailsConfigHelper.initJdbcGlobalUserDetails(authenticationManagerBuilder,
						mySecurityDefaultUsersSimpleConfigurer, dsProps);
			} else {
				// inMemoryAuthentication by default
				logger.info("configuring inMemoryAuthentication by default");
				udmc = myAppGlobalUserDetailsConfigHelper.initInMemoryGlobalUserDetails(authenticationManagerBuilder,
						mySecurityDefaultUsersSimpleConfigurer);
			}
			logger.info("UserDetailsManagerConfigurer class=" + udmc.getClass().getName());
			if (myAdditionalUserDetailsService != null) {

				AuthenticationManagerBuilder authBuilder2  = MyAuthenticationManagerBuilderHelper.newAuthenticationManagerBuilder();
				authBuilder2.userDetailsService(myAdditionalUserDetailsService)
				            .passwordEncoder(passwordEncoder);
				AuthenticationManager additionalAuthenticationManager = authBuilder2.build();
				authenticationManagerBuilder.parentAuthenticationManager(additionalAuthenticationManager);
			}
		}

		return authenticationManagerBuilder.build(); // **** IMPORTANT *****
	}
*/

}
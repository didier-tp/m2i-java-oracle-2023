package org.mycontrib.mysecurity.realm.config;

import java.util.HashMap;
import java.util.Map;

import org.mycontrib.mysecurity.realm.config.default_users.MySecurityDefaultUsersSimpleConfigurer;
import org.mycontrib.mysecurity.realm.config.jdbc.MyJdbcRealmSubConfigurer;
import org.mycontrib.mysecurity.realm.config.memory.MyInMemoryRealmSubConfigurer;
import org.mycontrib.mysecurity.realm.config.userdetails.MyUserDetailsServiceSubConfigurerBean;
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

	@Autowired
	MyUserDetailsServiceSubConfigurerBean myUserDetailsServiceSubConfigurerBean;
	
	@Bean //default global AuthenticationManager
	@ConditionalOnMissingBean(AuthenticationManager.class)
	public AuthenticationManager authenticationManager(HttpSecurity httpSecurity)throws Exception {
		initAuthenticationMgrMap(httpSecurity);
		return authenticationMgrMap.get("global");
	}

		
	public void initAuthenticationMgrMap(HttpSecurity httpSecurity)throws Exception {
		if(authenticationMgrMap!=null) return;
		AuthenticationManager secondaryAuthenticationManager=null;
		authenticationMgrMap = new HashMap<>();
		
		//build first AuthManager that may be secondary (ex: InMemory or Jdbc)
		//after that, build primary AuthManager (sometimes attached to parent=secondaryAuthManager) 
		
		MyInMemoryRealmSubConfigurer myInMemoryRealmSubConfigurer = new MyInMemoryRealmSubConfigurer(mySecurityRealmProperties,
			      authenticationMgrMap,passwordEncoder,mySecurityDefaultUsersSimpleConfigurer);
		myInMemoryRealmSubConfigurer.initInMemoryAuthenticationManagersInMap();
		
		
		if(mySecurityRealmProperties.isWithGlobalDefaultSecondaryInMemoryRealm()
				&& authenticationMgrMap.get("inMemory.global")!=null) {
			secondaryAuthenticationManager=authenticationMgrMap.get("inMemory.global");
		}
		else {
			if(mySecurityRealmProperties.isWithRestDefaultSecondaryInMemoryRealm()
					&& authenticationMgrMap.get("inMemory.rest")!=null)
				secondaryAuthenticationManager=authenticationMgrMap.get("inMemory.rest");
			if(mySecurityRealmProperties.isWithSiteDefaultSecondaryInMemoryRealm()
					&& authenticationMgrMap.get("inMemory.site")!=null)
				secondaryAuthenticationManager=authenticationMgrMap.get("inMemory.site");
		}
		
		MyJdbcRealmSubConfigurer myJdbcRealmSubConfigurer = new MyJdbcRealmSubConfigurer(mySecurityRealmProperties,
				      authenticationMgrMap,passwordEncoder,mySecurityDefaultUsersSimpleConfigurer);
		myJdbcRealmSubConfigurer.initJdbcAuthenticationManagersInMap(secondaryAuthenticationManager);
		
		
		if(authenticationMgrMap.get("jdbc.global")!=null) {
			secondaryAuthenticationManager=authenticationMgrMap.get("jdbc.global");
		}else {
			if(authenticationMgrMap.get("jdbc.rest")!=null)
				secondaryAuthenticationManager=authenticationMgrMap.get("jdbc.rest");
			if( authenticationMgrMap.get("jdbc.site")!=null)
				secondaryAuthenticationManager=authenticationMgrMap.get("jdbc.site");
		}
		
		
		myUserDetailsServiceSubConfigurerBean.initUserDetailsAuthenticationManagersInMap(authenticationMgrMap,
				secondaryAuthenticationManager);
		
		setHighPriorityAliasInauthenticationMgrMap();
		
	}
	
	public void setHighPriorityAliasInauthenticationMgrMap() {
		if(authenticationMgrMap.get("userdetails.global")!=null)
		   authenticationMgrMap.put("global",authenticationMgrMap.get("userdetails.global"));
		//à continuer ... !!!!
	}
	
	
	
	@Bean
	public Map<String,AuthenticationManager> authenticationManagerMap(HttpSecurity httpSecurity) throws Exception {
		initAuthenticationMgrMap(httpSecurity);
		return authenticationMgrMap;
	}
	
	

}
package org.mycontrib.mysecurity.realm.config.jdbc;

import java.util.Map;

import org.mycontrib.mysecurity.realm.config.MyAuthenticationManagerBuilderHelper;
import org.mycontrib.mysecurity.realm.config.default_users.MySecurityDefaultUsersSimpleConfigurer;
import org.mycontrib.mysecurity.realm.properties.MySecurityRealmProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


public class MyJdbcRealmSubConfigurer {

	private static Logger logger = LoggerFactory.getLogger(MyJdbcRealmSubConfigurer.class);
	
	public MyJdbcRealmSubConfigurer(
			MySecurityRealmProperties mySecurityRealmProperties,
			Map<String,AuthenticationManager> authenticationMgrMap,
			PasswordEncoder passwordEncoder,
			MySecurityDefaultUsersSimpleConfigurer mySecurityDefaultUsersSimpleConfigurer) {
		this.authenticationMgrMap=authenticationMgrMap;
		this.mySecurityRealmProperties=mySecurityRealmProperties;
		this.passwordEncoder=passwordEncoder;
		this.mySecurityDefaultUsersSimpleConfigurer=mySecurityDefaultUsersSimpleConfigurer;
	}
	
	
	public MySecurityRealmProperties mySecurityRealmProperties;
	private Map<String,AuthenticationManager> authenticationMgrMap ;
	private PasswordEncoder passwordEncoder;
	private MySecurityDefaultUsersSimpleConfigurer mySecurityDefaultUsersSimpleConfigurer;
	
	private AuthenticationManager secondaryAuthenticationManager=null;
	
	public void initJdbcAuthenticationManagersInMap(
			AuthenticationManager secondaryAuthenticationManager
			) {
		if (mySecurityRealmProperties !=null)
			{
			DataSourceProperties dsProps = null;
			AuthenticationManager authManager=null;
			if(mySecurityRealmProperties.getGlobal()!=null) {
			   dsProps = mySecurityRealmProperties.getGlobal().getJdbcRealm();
			   if(dsProps!=null) {
			     authManager=buildJdbcAuthenticationManager(dsProps);
			     if(authManager!=null) authenticationMgrMap.put("jdbc.global",authManager);
			   }
			}
			if(mySecurityRealmProperties.getRest()!=null) {
				   dsProps = mySecurityRealmProperties.getRest().getJdbcRealm();
				   if(dsProps!=null) {
				     authManager=buildJdbcAuthenticationManager(dsProps);
				     if(authManager!=null) authenticationMgrMap.put("jdbc.rest",authManager);
				   }
				}
			if(mySecurityRealmProperties.getSite()!=null) {
				   dsProps = mySecurityRealmProperties.getSite().getJdbcRealm();
				   if(dsProps!=null) {
				     authManager=buildJdbcAuthenticationManager(dsProps);
				     if(authManager!=null) authenticationMgrMap.put("jdbc.site",authManager);
				   }
				}
			}
	}
	
	public AuthenticationManager buildJdbcAuthenticationManager(DataSourceProperties dsProps) {
		AuthenticationManager jdbcAuthenticationManager = null;	
		try {
			AuthenticationManagerBuilder authenticationManagerBuilder = 
			    //MyAuthenticationManagerBuilderHelper.authenticationManagerBuilderFromHttpSecurity(httpSecurity);
				MyAuthenticationManagerBuilderHelper.newAuthenticationManagerBuilder();

			UserDetailsManagerConfigurer udmc = null;
			

			MyJdbcUdmcHelper myAppGlobalUserDetailsConfigHelper
			   = new MyJdbcUdmcHelper();
			
			
			logger.info("configuring jdbcRealm from mysecurity.realm properties : " 
			       + dsProps.getDriverClassName()
				+ " , url=" + dsProps.getUrl() 
				+ " , username=" + dsProps.getUsername());
			
			udmc = myAppGlobalUserDetailsConfigHelper.initJdbcGlobalUserDetails(
						authenticationManagerBuilder,
						mySecurityDefaultUsersSimpleConfigurer,
						dsProps,
						passwordEncoder);
			
			if(secondaryAuthenticationManager!=null)
				authenticationManagerBuilder.parentAuthenticationManager(secondaryAuthenticationManager);
		
			jdbcAuthenticationManager = authenticationManagerBuilder.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
	   return jdbcAuthenticationManager;
	}

	


}
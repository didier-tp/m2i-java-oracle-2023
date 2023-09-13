package org.mycontrib.mysecurity.realm.config.memory;

import java.util.Map;

import org.mycontrib.mysecurity.common.RealmPurposeEnum;
import org.mycontrib.mysecurity.realm.config.MyAuthenticationManagerBuilderHelper;
import org.mycontrib.mysecurity.realm.config.default_users.MySecurityDefaultUsersSimpleConfigurer;
import org.mycontrib.mysecurity.realm.properties.MySecurityRealmProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;


public class MyInMemoryRealmSubConfigurer {

	private static Logger logger = LoggerFactory.getLogger(MyInMemoryRealmSubConfigurer.class);
	
	public MyInMemoryRealmSubConfigurer(
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
	
	
	public void initInMemoryAuthenticationManagersInMap() {
		AuthenticationManager authManager=null;
		
		if(mySecurityRealmProperties.isWithGlobalDefaultSecondaryInMemoryRealm()) {
			authManager=buildInMemoryAuthenticationManager(RealmPurposeEnum.global);
			if(authManager!=null)
				authenticationMgrMap.put("inMemory.global",authManager);
		}
		else {
			if(mySecurityRealmProperties.isWithRestDefaultSecondaryInMemoryRealm()) {
				authManager=buildInMemoryAuthenticationManager(RealmPurposeEnum.rest);
				if(authManager!=null)
					authenticationMgrMap.put("inMemory.rest",authManager);
			}
			
			if(mySecurityRealmProperties.isWithSiteDefaultSecondaryInMemoryRealm()) {
				authManager=buildInMemoryAuthenticationManager(RealmPurposeEnum.site);
				if(authManager!=null)
					authenticationMgrMap.put("inMemory.site",authManager);
			}
		}
	}
	
	public AuthenticationManager buildInMemoryAuthenticationManager(RealmPurposeEnum realmPurpose) {
		AuthenticationManager inMemoryAuthenticationManager =null;
		try {
			AuthenticationManagerBuilder authenticationManagerBuilder = 
			    //MyAuthenticationManagerBuilderHelper.authenticationManagerBuilderFromHttpSecurity(httpSecurity);
				MyAuthenticationManagerBuilderHelper.newAuthenticationManagerBuilder();
			
			UserDetailsManagerConfigurer udmc  = 
					authenticationManagerBuilder
					.inMemoryAuthentication()
					.passwordEncoder(passwordEncoder);
			switch(realmPurpose) {
			case rest :
				mySecurityDefaultUsersSimpleConfigurer.configureRestDefaultUsers(udmc);
				break;
			case site :
				mySecurityDefaultUsersSimpleConfigurer.configureSiteDefaultUsers(udmc);
				break;
			case global :
			default:
				mySecurityDefaultUsersSimpleConfigurer.configureGlobalDefaultUsers(udmc);
				break;
			}
			
			inMemoryAuthenticationManager = authenticationManagerBuilder.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
	   return inMemoryAuthenticationManager;
	}

}
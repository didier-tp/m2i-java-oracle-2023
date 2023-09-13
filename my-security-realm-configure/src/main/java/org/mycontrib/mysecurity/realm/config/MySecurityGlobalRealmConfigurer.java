package org.mycontrib.mysecurity.realm.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.mycontrib.mysecurity.common.MyRealmConfigurer;
import org.mycontrib.mysecurity.common.RealmPurposeEnum;
import org.mycontrib.mysecurity.realm.config.default_users.MySecurityDefaultUsersSimpleConfigurer;
import org.mycontrib.mysecurity.realm.config.jdbc.MyJdbcRealmSubConfigurer;
import org.mycontrib.mysecurity.realm.config.memory.MyInMemoryRealmSubConfigurer;
import org.mycontrib.mysecurity.realm.config.userdetails.MyUserDetailsServiceSubConfigurerBean;
import org.mycontrib.mysecurity.realm.properties.MySecurityRealmProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;



@Component
@Profile("withSecurity")
@ConfigurationPropertiesScan("org.mycontrib.mysecurity.realm.properties")
public class MySecurityGlobalRealmConfigurer implements MyRealmConfigurer {

	private static Logger logger = LoggerFactory.getLogger(MySecurityGlobalRealmConfigurer.class);

	@Autowired(required = false)
	public MySecurityRealmProperties mySecurityRealmProperties;
	
	private Map<String,AuthenticationManager> authenticationMgrMap ;
	//mapEntry can be:
	// "global" , "rest" or "site" (main alias)
	// "userDetails.global" , "userDetails.rest" , "userDetails.site"
	// "jdbc.global" , "jdbc.rest" , "jdbc.site"
	// "inMemory.global" , "inMemory.rest" , "inMemory.site"
	// other values in future versions
	//NB: AuthenticationManager interface is implemented by ProviderManager
	
	
	@Autowired
	private PasswordEncoder passwordEncoder; //BCryptPasswordEncoder

	@Autowired
	private MySecurityDefaultUsersSimpleConfigurer mySecurityDefaultUsersSimpleConfigurer;

	@Autowired
	MyUserDetailsServiceSubConfigurerBean myUserDetailsServiceSubConfigurerBean;
	


	
	@PostConstruct
	public void initMySecurityGlobalRealmConfigurer(){
		logger.debug("MySecurityGlobalRealmConfigurer with mySecurityRealmProperties="+mySecurityRealmProperties);
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
		
		
		//first priority : "userdetails"
		if(authenticationMgrMap.get("userDetails.global")!=null) {
		   authenticationMgrMap.put("global",authenticationMgrMap.get("userDetails.global"));
		}
		else {
			if(authenticationMgrMap.get("userDetails.rest")!=null) {
				   authenticationMgrMap.put("rest",authenticationMgrMap.get("userDetails.rest"));
				}
			if(authenticationMgrMap.get("userDetails.site")!=null) {
				   authenticationMgrMap.put("site",authenticationMgrMap.get("userDetails.site"));
				}
		}
		
		//second priority : "jdbc"
		if(authenticationMgrMap.get("global")==null
			&& authenticationMgrMap.get("jdbc.global")!=null) {
				   authenticationMgrMap.put("global",authenticationMgrMap.get("jdbc.global"));
				}
		else {
			if(authenticationMgrMap.get("rest")==null
					&& authenticationMgrMap.get("jdbc.rest")!=null) {
						   authenticationMgrMap.put("rest",authenticationMgrMap.get("jdbc.rest"));
						}
			if(authenticationMgrMap.get("site")==null
					&& authenticationMgrMap.get("jdbc.site")!=null) {
						   authenticationMgrMap.put("site",authenticationMgrMap.get("jdbc.site"));
						}
		}		
		
		//last priority : "inMemory"
		if(authenticationMgrMap.get("global")==null
			&& authenticationMgrMap.get("inMemory.global")!=null) {
						   authenticationMgrMap.put("global",authenticationMgrMap.get("inMemory.global"));
						}
		else {
			if(authenticationMgrMap.get("rest")==null
				&& authenticationMgrMap.get("inMemory.rest")!=null) {
								   authenticationMgrMap.put("rest",authenticationMgrMap.get("inMemory.rest"));
				}
			if(authenticationMgrMap.get("site")==null
				&& authenticationMgrMap.get("inMemory.site")!=null) {
								   authenticationMgrMap.put("site",authenticationMgrMap.get("inMemory.site"));
				}
			}
		
		for( Entry<String,AuthenticationManager> entry: authenticationMgrMap.entrySet()) {
			logger.debug("authenticationMgr[" + entry.getKey() + "]=" + entry.getValue().toString());
			//AuthenticationProvider authProv = (AuthenticationProvider)entry.getValue();
			//logger.debug("---" + authProv.);
		}
	}
	


	@Override
	public AuthenticationManager getRealmAuthenticationManager(HttpSecurity httpSecurity,RealmPurposeEnum realmPurpose) {
		try {
			initAuthenticationMgrMap(httpSecurity);
		} catch (Exception e) {
			logger.error("initAuthenticationMgrMap error",e);
		}
		String realmPurposeString  =  realmPurpose.toString();
		AuthenticationManager authMgr =  authenticationMgrMap.get(realmPurposeString);
		if(authMgr==null)
			authMgr =  authenticationMgrMap.get("global");//as default if no specific for "rest" or "site"
		return authMgr;
	}
	
	

}
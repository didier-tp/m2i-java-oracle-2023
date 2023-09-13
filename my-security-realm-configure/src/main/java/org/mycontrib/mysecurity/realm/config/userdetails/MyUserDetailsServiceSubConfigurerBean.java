package org.mycontrib.mysecurity.realm.config.userdetails;

import java.util.Map;

import org.mycontrib.mysecurity.common.MySecurityExtension;
import org.mycontrib.mysecurity.realm.config.MyAuthenticationManagerBuilderHelper;
import org.mycontrib.mysecurity.realm.properties.MySecurityRealmProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("withSecurity")
public class MyUserDetailsServiceSubConfigurerBean {

	private static Logger logger = LoggerFactory.getLogger(MyUserDetailsServiceSubConfigurerBean.class);
	
	@Autowired(required = false)
	@Qualifier(MySecurityExtension.MY_EXCLUSIVE_USERDETAILSSERVICE_NAME)
	UserDetailsService myExclusiveUserDetailsService;

	@Autowired(required = false)
	@Qualifier(MySecurityExtension.MY_ADDITIONAL_USERDETAILSSERVICE_NAME)
	UserDetailsService myAdditionalUserDetailsService;
	
	@Autowired(required = false)
	@Qualifier(MySecurityExtension.MY_EXCLUSIVE_RESTONLY_USERDETAILSSERVICE_NAME)
	UserDetailsService myExclusiveRestOnlyUserDetailsService;

	@Autowired(required = false)
	@Qualifier(MySecurityExtension.MY_ADDITIONAL_RESTONLY_USERDETAILSSERVICE_NAME)
	UserDetailsService myAdditionalRestOnlyUserDetailsService;
	
	@Autowired(required = false)
	@Qualifier(MySecurityExtension.MY_EXCLUSIVE_SITEONLY_USERDETAILSSERVICE_NAME)
	UserDetailsService myExclusiveSiteOnlyUserDetailsService;

	@Autowired(required = false)
	@Qualifier(MySecurityExtension.MY_ADDITIONAL_SITEONLY_USERDETAILSSERVICE_NAME)
	UserDetailsService myAdditionalSiteOnlyUserDetailsService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
    private AuthenticationManager secondaryAuthenticationManager=null;
  
	public void initUserDetailsAuthenticationManagersInMap(
			Map<String,AuthenticationManager> authenticationMgrMap,
			AuthenticationManager secondaryAuthenticationManager) {
		AuthenticationManager authManager=null;
		this.secondaryAuthenticationManager=secondaryAuthenticationManager;
		if (myExclusiveUserDetailsService != null) {
			authManager=buildUserDetailsAuthenticationManager(myExclusiveUserDetailsService,passwordEncoder,true);
			if(authManager!=null) {
				authenticationMgrMap.put("userDetails.global",authManager);
			}
		}else {
			if (myExclusiveRestOnlyUserDetailsService != null) {
				authManager=buildUserDetailsAuthenticationManager(myExclusiveRestOnlyUserDetailsService,passwordEncoder,true);
				if(authManager!=null) {
					authenticationMgrMap.put("userDetails.rest",authManager);
				}
			}
			if (myExclusiveSiteOnlyUserDetailsService != null) {
				authManager=buildUserDetailsAuthenticationManager(myExclusiveSiteOnlyUserDetailsService,passwordEncoder,true);
				if(authManager!=null) {
					authenticationMgrMap.put("userDetails.site",authManager);
				}
			}
		}
		if (myAdditionalUserDetailsService != null) {
			authManager=buildUserDetailsAuthenticationManager(myAdditionalUserDetailsService,passwordEncoder,false);
			if(authManager!=null) {
				authenticationMgrMap.put("userDetails.global",authManager);
			}
		}else {
			if (myAdditionalRestOnlyUserDetailsService != null) {
				authManager=buildUserDetailsAuthenticationManager(myAdditionalRestOnlyUserDetailsService,passwordEncoder,false);
				if(authManager!=null) {
					authenticationMgrMap.put("userDetails.rest",authManager);
				}
			}
			if (myAdditionalSiteOnlyUserDetailsService != null) {
				authManager=buildUserDetailsAuthenticationManager(myAdditionalSiteOnlyUserDetailsService,passwordEncoder,false);
				if(authManager!=null) {
					authenticationMgrMap.put("userDetails.site",authManager);
				}
			}
		}
	}
	
	
	public AuthenticationManager buildUserDetailsAuthenticationManager(
			UserDetailsService userDetailsService,
			BCryptPasswordEncoder passwordEncoder,
			boolean exclusive){
		AuthenticationManager authManager=null;
		try {
			AuthenticationManagerBuilder authenticationManagerBuilder  = MyAuthenticationManagerBuilderHelper.newAuthenticationManagerBuilder();
			authenticationManagerBuilder.userDetailsService(userDetailsService)
										.passwordEncoder(passwordEncoder);
			if(!exclusive && secondaryAuthenticationManager!=null) {
				logger.debug("secondaryAuthenticationManager="+secondaryAuthenticationManager);
				authenticationManagerBuilder.parentAuthenticationManager(secondaryAuthenticationManager);
			}
			authManager = authenticationManagerBuilder.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return authManager;
	}


}
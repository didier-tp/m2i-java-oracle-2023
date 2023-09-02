package org.mycontrib.mysecurity.realm.config;

import org.mycontrib.mysecurity.realm.config.default_users.MySecurityDefaultUsersSimpleConfigurer;
import org.mycontrib.mysecurity.realm.config.jdbc.MyJdbcUdmcHelper;
import org.mycontrib.mysecurity.realm.properties.MySecurityRealmProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class MyUserDetailsServiceConfigurer {
	
	@Autowired(required = false)
	@Qualifier(MySecurityExtension.MY_EXCLUSIVE_USERDETAILSSERVICE_NAME)
	UserDetailsService myExclusiveUserDetailsService;

	@Autowired(required = false)
	@Qualifier(MySecurityExtension.MY_ADDITIONAL_USERDETAILSSERVICE_NAME)
	UserDetailsService myAdditionalUserDetailsService;

	private static Logger logger = LoggerFactory.getLogger(MyUserDetailsServiceConfigurer.class);

	public AuthenticationManagerBuilder settingAuthenticationManagerBuilderWithUserDetailsService(
			AuthenticationManagerBuilder authenticationManagerBuilder,
			MySecurityRealmProperties mySecurityRealmProperties,
			BCryptPasswordEncoder passwordEncoder,
			UserDetailsManagerConfigurer optionalSecondaryUdmc
			) throws Exception {
		
		logger.info("myExclusiveUserDetailsService=" + myExclusiveUserDetailsService);
		logger.info("myAdditionalUserDetailsService=" + myAdditionalUserDetailsService);
		if (myExclusiveUserDetailsService != null) {
			authenticationManagerBuilder.userDetailsService(myExclusiveUserDetailsService)
										.passwordEncoder(passwordEncoder);
		} else {
			logger.info("UserDetailsManagerConfigurer class=" + optionalSecondaryUdmc.getClass().getName());
			if (myAdditionalUserDetailsService != null) {
				AuthenticationManagerBuilder authBuilder2  = MyAuthenticationManagerBuilderHelper.newAuthenticationManagerBuilder();
				authBuilder2.userDetailsService(myAdditionalUserDetailsService)
				            .passwordEncoder(passwordEncoder);
				AuthenticationManager additionalAuthenticationManager = authBuilder2.build();
				authenticationManagerBuilder.parentAuthenticationManager(additionalAuthenticationManager);
			}
		}
		
		return authenticationManagerBuilder;
	}


}
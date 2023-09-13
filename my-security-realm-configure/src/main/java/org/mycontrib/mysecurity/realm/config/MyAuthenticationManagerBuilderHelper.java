package org.mycontrib.mysecurity.realm.config;

import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public class MyAuthenticationManagerBuilderHelper {
	
	public static AuthenticationManagerBuilder newAuthenticationManagerBuilder() {
		final ObjectPostProcessor<Object> objectPostProcessor = new ObjectPostProcessor<Object>() {
			  @Override
			  public <O extends Object> O postProcess(final O object) {
			   return object;
			  }
		};
		AuthenticationManagerBuilder authMgrBuilder  = new AuthenticationManagerBuilder(objectPostProcessor);
		return authMgrBuilder;
	}
	
	public static AuthenticationManagerBuilder authenticationManagerBuilderFromHttpSecurity(HttpSecurity httpSecurity) {
		
		AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity
				.getSharedObject(AuthenticationManagerBuilder.class); // **** IMPORTANT *****
		authenticationManagerBuilder.parentAuthenticationManager(null);
		                     // **** VERY VERY IMPORTANT in order to avoid
							 // infinite loop when .authenticate() fail
        return authenticationManagerBuilder;
	}
	
	

}

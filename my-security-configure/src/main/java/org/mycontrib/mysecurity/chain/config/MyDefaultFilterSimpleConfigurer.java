package org.mycontrib.mysecurity.chain.config;

import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

//@Component
@Profile("withSecurity")
public class MyDefaultFilterSimpleConfigurer implements MyFilterSimpleConfigurer {

	@Override
	public SecurityFilterChain configureAndBuildEndOfSecurityChain(HttpSecurity http) throws Exception{
		return http.build();
	}

}

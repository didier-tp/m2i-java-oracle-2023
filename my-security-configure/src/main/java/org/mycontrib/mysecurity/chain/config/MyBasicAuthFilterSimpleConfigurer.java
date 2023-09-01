package org.mycontrib.mysecurity.chain.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
@Profile("withSecurity")
@Qualifier("BasicAuth")
public class MyBasicAuthFilterSimpleConfigurer implements MyFilterSimpleConfigurer {

	@Override
	public SecurityFilterChain configureAndBuildEndOfSecurityChain(HttpSecurity http) throws Exception{
		return http.build();
	}

}

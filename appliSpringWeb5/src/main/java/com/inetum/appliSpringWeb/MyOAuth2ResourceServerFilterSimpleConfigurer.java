package com.inetum.appliSpringWeb;

import org.mycontrib.mysecurity.chain.config.MyFilterSimpleConfigurer;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
@Profile("withSecurity")
public class MyOAuth2ResourceServerFilterSimpleConfigurer implements MyFilterSimpleConfigurer {

	@Override
	public SecurityFilterChain configureAndBuildEndOfSecurityChain(HttpSecurity http)throws Exception{
		return http.
		sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
		.build();
	}

}

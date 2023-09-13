package org.mycontrib.mysecurity.standalone.config;

import org.mycontrib.mysecurity.common.MyFilterChainSimpleConfigurer;
import org.mycontrib.mysecurity.jwt.util.JwtAuthenticationFilter;
import org.mycontrib.mysecurity.standalone.util.MyNoAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@Profile("withSecurity")
@Qualifier("StandaloneJwt")
public class MyStandaloneFilterSimpleConfigurer implements MyFilterChainSimpleConfigurer {

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private MyNoAuthenticationEntryPoint unauthorizedHandler;
	
	@Override
	public HttpSecurity configureEndOfSecurityChain(HttpSecurity http)
			throws Exception {
		return http
		// If the user is not authenticated, returns 401
		.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
		// This is a stateless application, disable sessions
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		// Custom filter for authenticating users using tokens
		.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
	}

}

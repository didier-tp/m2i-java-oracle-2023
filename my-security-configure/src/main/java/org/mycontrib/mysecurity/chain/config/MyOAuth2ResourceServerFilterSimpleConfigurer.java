package org.mycontrib.mysecurity.chain.config;

import org.mycontrib.mysecurity.common.MyFilterChainSimpleConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.stereotype.Component;

@Component
@Profile("withSecurity")
@Qualifier("OAuth2ResourceServer")
public class MyOAuth2ResourceServerFilterSimpleConfigurer implements MyFilterChainSimpleConfigurer {
	
	private static Logger logger = LoggerFactory.getLogger(MyOAuth2ResourceServerFilterSimpleConfigurer.class);
	

	@Override
	public HttpSecurity configureEndOfSecurityChain(HttpSecurity http)throws Exception{
	return http.
			sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
	}

}

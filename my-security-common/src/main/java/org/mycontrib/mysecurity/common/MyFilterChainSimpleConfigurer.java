package org.mycontrib.mysecurity.common;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface MyFilterChainSimpleConfigurer {
	HttpSecurity configureEndOfSecurityChain(HttpSecurity http)throws Exception;
	//...
}

/*
 at least 2 implementations :
 ------------------
 MyOAuth2ResourceServerFilterSimpleConfigurer | @Qualifier("OAuth2ResourceServer")
 MyStandaloneFilterSimpleConfigurer           | @Qualifier("StandaloneJwt") dans projet my-security-std-jwt-configure
 */

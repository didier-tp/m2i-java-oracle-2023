package org.mycontrib.mysecurity.chain.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

public interface MyFilterSimpleConfigurer {
	SecurityFilterChain configureAndBuildEndOfSecurityChain(HttpSecurity http)throws Exception;
	//...
}

/*
 3 implementations :
 ------------------
 MyBasicAuthFilterSimpleConfigurer            | @Qualifier("BasicAuth")
 MyOAuth2ResourceServerFilterSimpleConfigurer | @Qualifier("OAuth2ResourceServer")
 MyStandaloneFilterSimpleConfigurer           | @Qualifier("StandaloneJwt") dans projet my-security-std-jwt-configure
 */

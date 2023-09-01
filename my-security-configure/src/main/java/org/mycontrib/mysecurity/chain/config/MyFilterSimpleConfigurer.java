package org.mycontrib.mysecurity.chain.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

public interface MyFilterSimpleConfigurer {
	SecurityFilterChain configureAndBuildEndOfSecurityChain(HttpSecurity http)throws Exception;
	//...
}

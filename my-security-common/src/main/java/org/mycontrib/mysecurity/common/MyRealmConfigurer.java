package org.mycontrib.mysecurity.common;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/* contract of OPTIONAL realm initialization that may be used in SecurityChain configuration */
public interface MyRealmConfigurer {
	
	public AuthenticationManager getRealmAuthenticationManager(HttpSecurity httpSecurity,RealmPurposeEnum realmPurpose); 

}

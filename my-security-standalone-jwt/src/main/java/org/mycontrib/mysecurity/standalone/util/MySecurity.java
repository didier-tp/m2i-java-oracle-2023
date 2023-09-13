package org.mycontrib.mysecurity.standalone.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("withSecurity")
public class MySecurity {
	
	public static String DEFAULT_SPRING_SECURITY_ROLE_PREFIX = "ROLE_";
	
	//NB: passwordEncoder now in my-security-common 
	//(org.mycontrib.mysecurity.common.CommonSecurityConfig)
	
	//need to scan org.mycontrib.mysecurity.common package
	//in auto-configure (my-security-std-jwt-configure , my-security-configure, ...)

	public static List<String> roleNameList(Authentication authentication) {
		UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
		List<String> roleNameList = new ArrayList<String>();
		for (GrantedAuthority ga : userPrincipal.getAuthorities()) {
			String springSecurityRoleName = ga.getAuthority();
			String roleName = springSecurityRoleName;
			System.out.println("roleName="+roleName);
			// ou bien roleName = springSecurityRoleName moins le préfixe "ROLE_" (affaire
			// de préférence)
			/*
			 * if(roleName.startsWith(DEFAULT_SPRING_SECURITY_ROLE_PREFIX)){ roleName =
			 * roleName.substring(DEFAULT_SPRING_SECURITY_ROLE_PREFIX.length()); }
			 */
			roleNameList.add(roleName);
		}
		return roleNameList;
	}
}

package org.mycontrib.mysecurity.jwt.util;

import java.util.ArrayList;
import java.util.List;

import org.mycontrib.mysecurity.standalone.util.MySecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;

/* Un peu comme JwtUtil mais sous forme de composant Spring */

@Component
@Profile("withSecurity")
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    
    //private String DEFAULT_SPRING_SECURITY_ROLE_PREFIX="ROLE_";

    //@Value("${app.jwtSecret}") // in application.properties
    private String jwtSecret = "L7R6O0ZR9LmKJN6tfZ1K6U7hLZcpBT8ObPKPl6ewCtU="; //by default (example)

    //@Value("${app.jwtExpirationInMs}") // in application.properties
    private int jwtExpirationInMs =  30*60*1000 ;//pour 30 minutes (example) par defaut

    public String generateToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        List<String> roleNameList=MySecurity.roleNameList(authentication);
        return buildToken(userPrincipal.getUsername(),/*null*/roleNameList);
    }
        
    public String buildToken(String userNameOrId,List<String> roleNameList) {
        return JwtUtil.buildToken(userNameOrId, jwtExpirationInMs, jwtSecret,roleNameList);
    }
    
    //si pas de roles dans jwt claims:
    public String getUserNameOrIdFromJWT(String token) {
        return JwtUtil.extractClaimsFromJWT(token, jwtSecret).getSubject();
    }
    
    //si roles dans jwt claims:
    public UserDetails getUserDetailsFromJWT(String token) {
    	Claims jwtClaims = JwtUtil.extractClaimsFromJWT(token, jwtSecret);
        String username = jwtClaims.getSubject();
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
       
        Object rolesInClaim = jwtClaims.get(JwtUtil.ROLES_AUTHORITIES_CLAIM); 
		if(rolesInClaim!=null){
			String rolesInClaimAsString = (String) rolesInClaim.toString();
			//exemples: "[]" ou [USER] ou [USER,ADMIN]
			if(rolesInClaimAsString.length()>2){
				rolesInClaimAsString = rolesInClaimAsString.substring(1, rolesInClaimAsString.length()-1);//sans'['ni']'
				String[] tabOfRoleNames = rolesInClaimAsString.split(",");
		        for(String roleName : tabOfRoleNames){
		        	roleName=roleName.trim();
		        	logger.debug("in jwt claims, found roleName="+roleName);
		        	String springSecurityRoleName = roleName;
		        	//ou bien springSecurityRoleName = "ROLE_" + roleName; si besoin :
		        	if(!(springSecurityRoleName.startsWith(MySecurity.DEFAULT_SPRING_SECURITY_ROLE_PREFIX))){
		        		springSecurityRoleName=MySecurity.DEFAULT_SPRING_SECURITY_ROLE_PREFIX + roleName; 
		        	}
					authorities.add(new SimpleGrantedAuthority(springSecurityRoleName));
				}
			}
		}
		
		// User(username, password, enabled, accountNonExpired, credentialsNotExpired, accountNonLocked, authorities)
		User user = new User(username, "unknown_in_jwt_claims_but_already_check", true /*account.isEnabled()*/, true, true, true, authorities);
		return user;
    }

    public boolean validateToken(String authToken) {
        return JwtUtil.validateToken(authToken, jwtSecret);
    }
}

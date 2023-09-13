package org.mycontrib.mysecurity.jwt.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Profile("withSecurity")
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
    @Autowired
    private JwtTokenProvider tokenProvider;
    
    
    private final RequestMatcher restUriMatcher = 
            new AntPathRequestMatcher("/rest/**");

    //@Autowired
    //private UserDetailsService userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        RequestMatcher noRestUriMatcher = new NegatedRequestMatcher(restUriMatcher);
        return noRestUriMatcher.matches(request); //DO NOT USE THIS FILTER IF NOT FOR REST API REQUEST !!!
    }
    
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            logger.info("jwt extract by JwtAuthenticationFilter in request:"+jwt);
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            	//plan A avec roles dans jwt claims:
            	UserDetails userDetails = tokenProvider.getUserDetailsFromJWT(jwt);
            	if(userDetails.getAuthorities()==null){
            		//plan B sans roles dans jwt claims:
                    String userNameOrId = tokenProvider.getUserNameOrIdFromJWT(jwt);
                    //userDetails = userDetailsService.loadUserByUsername(userNameOrId);
            	}
            	
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.info("JwtAuthenticationFilter is storing authentication:"+authentication + " in spring security SecurityContextHolder");
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
        
        // Clears the context from authentication
     	SecurityContextHolder.getContext().setAuthentication(null);
     		
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}

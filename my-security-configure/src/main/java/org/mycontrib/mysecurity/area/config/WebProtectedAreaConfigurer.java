package org.mycontrib.mysecurity.area.config;

import java.util.Arrays;
import java.util.stream.Stream;

import org.mycontrib.mysecurity.area.properties.MySecurityAreaProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("withSecurity")
@ConfigurationPropertiesScan("org.mycontrib.mysecurity.area.properties")
public class WebProtectedAreaConfigurer {

	private static Logger logger = LoggerFactory.getLogger(WebProtectedAreaConfigurer.class);

	@Autowired(required = false)
	public MySecurityAreaProperties mySecurityProperties;

	public static String[] concatenateArray(String[] first, String[] second)
	{
	    return Stream.concat(Arrays.stream(first), Arrays.stream(second))
	                    .toArray(String[]::new);
	}

	@Bean 
	protected AreasConfig areasConfig() {
		
		AreasConfig areasConfig = new AreasConfig();

		String[] defaultStaticWhitelist = { "/", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg",
				"/**/*.html", "/**/*.css", "/**/*.js" }; // default value
		
		String[] defaultApiWhitelist = { "/rest/my-api/public/**" }; // default value
		
		String[] defaultToolsWhitelist = { "/swagger-ui/**","/v3/api-docs" ,"/h2-console/**"}; 
		
		String[] defaultApiReadonlyWhitelist = { "/rest/my-api/readonly/**" }; // default value
		
		String[] defaultApiProtectedlist = { "/rest/my-api/private/**" }; // default value

		if (mySecurityProperties != null && mySecurityProperties.getStaticWhitelist() != null)
			areasConfig.setStaticWhitelist(mySecurityProperties.getStaticWhitelist().split(";"));
		else areasConfig.setStaticWhitelist(defaultStaticWhitelist);
		
		if (mySecurityProperties != null && mySecurityProperties.getToolsWhitelist() != null)
			areasConfig.setToolsWhitelist(mySecurityProperties.getToolsWhitelist().split(";"));
		else areasConfig.setToolsWhitelist(defaultToolsWhitelist);
		
		if (mySecurityProperties != null && mySecurityProperties.getWhitelist() != null)
			areasConfig.setApiWhitelist(mySecurityProperties.getWhitelist().split(";"));
		else areasConfig.setApiWhitelist(defaultApiWhitelist);
		
		if (mySecurityProperties != null && mySecurityProperties.getReadonlyWhitelist() != null)
			areasConfig.setApiReadonlyWhitelist(mySecurityProperties.getReadonlyWhitelist().split(";"));
		else areasConfig.setApiReadonlyWhitelist(defaultApiReadonlyWhitelist);
		
		if (mySecurityProperties != null && mySecurityProperties.getProtectedlist() != null)
			areasConfig.setApiProtectedlist(mySecurityProperties.getProtectedlist().split(";"));
		else areasConfig.setApiProtectedlist(defaultApiProtectedlist);
		
		//Readaptation de ApiProtectedlist :
		//toute url de ApiReadonlyWhitelist doit normalement être également placée
		//dans ApiProtectedlist pour les appels autres qu'en GET (POST,PUT,DELETE,...)
		areasConfig.setApiProtectedlist(this.concatenateArray(areasConfig.getApiReadonlyWhitelist(),
				                                              areasConfig.getApiProtectedlist()));

		logger.info("staticWhitelist=" + Arrays.asList(areasConfig.getStaticWhitelist()));
		logger.info("toolsWhitelist=" + Arrays.asList(areasConfig.getToolsWhitelist()));
		logger.info("whitelist=" + Arrays.asList(areasConfig.getApiWhitelist()));
		logger.info("readonlyWhitelist=" + Arrays.asList(areasConfig.getApiReadonlyWhitelist()));
		logger.info("protectedlist=" + Arrays.asList(areasConfig.getApiProtectedlist()));
		
		return areasConfig;
	}
}
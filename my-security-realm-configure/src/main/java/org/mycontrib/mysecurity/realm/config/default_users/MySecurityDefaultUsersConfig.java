package org.mycontrib.mysecurity.realm.config.default_users;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@Profile("withSecurity")
public class MySecurityDefaultUsersConfig {
	
	@Bean
	@ConditionalOnMissingBean(type= { "org.mycontrib.mysecurity.config.MySecuritySimpleConfigurer"})
	MySecurityDefaultUsersSimpleConfigurer  defaultMySecuritySimpleConfigurer(BCryptPasswordEncoder passwordEncoder) {
		return new MySecurityDefaultUsersSimpleConfigurerDefaultImpl(passwordEncoder);
	}

}

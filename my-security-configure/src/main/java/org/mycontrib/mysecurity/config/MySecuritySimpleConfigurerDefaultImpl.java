package org.mycontrib.mysecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MySecuritySimpleConfigurerDefaultImpl implements MySecuritySimpleConfigurer {

	
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	public MySecuritySimpleConfigurerDefaultImpl(BCryptPasswordEncoder passwordEncoder){
		this.passwordEncoder=passwordEncoder;
	}
	
	@Override
	public void configureDefaultUsers(UserDetailsManagerConfigurer udmc) {
		udmc
		.withUser("user1").password(passwordEncoder.encode("pwd1")).roles("USER")
		.and().withUser("admin1").password(passwordEncoder.encode("pwd1")).roles("ADMIN")
		.and().withUser("user2").password(passwordEncoder.encode("pwd2")).roles("USER")
		.and().withUser("admin2").password(passwordEncoder.encode("pwd2")).roles("ADMIN");
	}

}

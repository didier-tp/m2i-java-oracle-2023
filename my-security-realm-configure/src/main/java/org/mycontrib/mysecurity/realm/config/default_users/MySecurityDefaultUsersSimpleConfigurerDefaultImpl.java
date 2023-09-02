package org.mycontrib.mysecurity.realm.config.default_users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//NB: on peut créer une version spécifique de cette classe
//dans le projet principal qui sera alors prioritaire par rapport à celle ci
//exemple :
//@Component @Profile("withSecurity")
// class MySecurityDefaultUsersSimpleConfigurerSpecificImpl implements MySecurityDefaultUsersSimpleConfigurer {
public class MySecurityDefaultUsersSimpleConfigurerDefaultImpl implements MySecurityDefaultUsersSimpleConfigurer {

	
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	public MySecurityDefaultUsersSimpleConfigurerDefaultImpl(BCryptPasswordEncoder passwordEncoder){
		this.passwordEncoder=passwordEncoder;
	}
	
	@Override
	public void configureGlobalDefaultUsers(UserDetailsManagerConfigurer udmc) {
		udmc
		.withUser("user1").password(passwordEncoder.encode("pwd1")).roles("USER")
		.and().withUser("admin1").password(passwordEncoder.encode("pwd1")).roles("ADMIN")
		.and().withUser("user2").password(passwordEncoder.encode("pwd2")).roles("USER")
		.and().withUser("admin2").password(passwordEncoder.encode("pwd2")).roles("ADMIN");
	}

}

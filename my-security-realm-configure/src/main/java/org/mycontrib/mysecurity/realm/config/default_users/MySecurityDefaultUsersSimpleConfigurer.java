package org.mycontrib.mysecurity.realm.config.default_users;

import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;

public interface MySecurityDefaultUsersSimpleConfigurer {
	void configureDefaultUsers(UserDetailsManagerConfigurer udmc);
	//...
}

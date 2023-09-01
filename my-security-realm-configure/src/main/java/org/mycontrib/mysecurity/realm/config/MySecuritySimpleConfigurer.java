package org.mycontrib.mysecurity.realm.config;

import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;

public interface MySecuritySimpleConfigurer {
	void configureDefaultUsers(UserDetailsManagerConfigurer udmc);
	//...
}

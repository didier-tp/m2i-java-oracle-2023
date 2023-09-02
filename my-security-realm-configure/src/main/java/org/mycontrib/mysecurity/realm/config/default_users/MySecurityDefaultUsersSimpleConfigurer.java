package org.mycontrib.mysecurity.realm.config.default_users;

import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;

public interface MySecurityDefaultUsersSimpleConfigurer {
	default void configureRestDefaultUsers(UserDetailsManagerConfigurer udmc) {
		configureGlobalDefaultUsers(udmc);
	}
	default void configureSiteDefaultUsers(UserDetailsManagerConfigurer udmc) {
		configureGlobalDefaultUsers(udmc);
	}
	void configureGlobalDefaultUsers(UserDetailsManagerConfigurer udmc);
	//...
}

package org.mycontrib.mysecurity.realm.config;

public interface MySecurityExtension {
	public static final String MY_EXCLUSIVE_USERDETAILSSERVICE_NAME="MyExclusiveUserDetailsService";
	public static final String MY_ADDITIONAL_USERDETAILSSERVICE_NAME="MyAdditionalUserDetailsService";
	public static final String MY_SECURITY_DEFAULT_LOGIN_PATH="/rest/api-login/public/login";
}

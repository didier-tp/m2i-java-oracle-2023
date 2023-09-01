package org.mycontrib.mysecurity.realm.properties;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="mysecurity.realm")
public class MySecurityRealmProperties {
	
	private DataSourceProperties jdbcRealm;
	

	@Override
	public String toString() {
		return "MySecurityProperties [ jdbcRealm="+ jdbcRealm + "]";
	}

	public MySecurityRealmProperties() {
		super();
	}


	public DataSourceProperties getJdbcRealm() {
		return jdbcRealm;
	}

	public void setJdbcRealm(DataSourceProperties jdbcRealm) {
		this.jdbcRealm = jdbcRealm;
	}
	
}

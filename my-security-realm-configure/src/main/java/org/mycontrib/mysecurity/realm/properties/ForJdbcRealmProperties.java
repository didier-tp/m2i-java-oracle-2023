package org.mycontrib.mysecurity.realm.properties;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

public class ForJdbcRealmProperties {
	
	private DataSourceProperties jdbcRealm;
	

	@Override
	public String toString() {
		return "ForJdbcRealmProperties [ jdbcRealm="+ jdbcRealm + "]";
	}

	public ForJdbcRealmProperties() {
		super();
	}


	public DataSourceProperties getJdbcRealm() {
		return jdbcRealm;
	}

	public void setJdbcRealm(DataSourceProperties jdbcRealm) {
		this.jdbcRealm = jdbcRealm;
	}
	
}

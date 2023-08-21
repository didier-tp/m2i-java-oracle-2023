package org.mycontrib.mysecurity.properties;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="mysecurity")
public class MySecurityProperties {
	private String staticWhitelist;
	private String whitelist;
	private String readonlyWhitelist;
	private String protectedlist;
	
	private DataSourceProperties jdbcRealm;
	

	@Override
	public String toString() {
		return "MySecurityProperties [staticWhitelist=" + staticWhitelist + ", whitelist=" + whitelist
				+ ", readonlyWhitelist=" + readonlyWhitelist + ", protectedlist=" + protectedlist + ", jdbcRealm="
				+ jdbcRealm + "]";
	}

	public MySecurityProperties() {
		super();
	}

	public String getWhitelist() {
		return whitelist;
	}

	public void setWhitelist(String whitelist) {
		this.whitelist = whitelist;
	}

	public String getStaticWhitelist() {
		return staticWhitelist;
	}

	public void setStaticWhitelist(String staticWhitelist) {
		this.staticWhitelist = staticWhitelist;
	}

	public String getReadonlyWhitelist() {
		return readonlyWhitelist;
	}

	public void setReadonlyWhitelist(String readonlyWhitelist) {
		this.readonlyWhitelist = readonlyWhitelist;
	}

	public String getProtectedlist() {
		return protectedlist;
	}

	public void setProtectedlist(String protectedlist) {
		this.protectedlist = protectedlist;
	}

	public DataSourceProperties getJdbcRealm() {
		return jdbcRealm;
	}

	public void setJdbcRealm(DataSourceProperties jdbcRealm) {
		this.jdbcRealm = jdbcRealm;
	}
	
}

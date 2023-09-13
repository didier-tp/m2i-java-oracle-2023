package org.mycontrib.mysecurity.realm.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="mysecurity.realm")
public class MySecurityRealmProperties {
	
	private static Logger logger = LoggerFactory.getLogger(MySecurityRealmProperties.class);

	
	private ForJdbcRealmProperties site;//mysecurity.realm.site.jdbc-realm....
	private ForJdbcRealmProperties rest;//mysecurity.realm.rest.jdbc-realm....
	private ForJdbcRealmProperties global;//mysecurity.realm.global.jdbc-realm....
	
	private boolean withGlobalDefaultSecondaryInMemoryRealm =false;
	private boolean withRestDefaultSecondaryInMemoryRealm=false;
	private boolean withSiteDefaultSecondaryInMemoryRealm=false;


	public MySecurityRealmProperties() {
		super();
		logger.debug("MySecurityRealmProperties");
	}


	@Override
	public String toString() {
		return "MySecurityRealmProperties [site=" + site + ", rest=" + rest + ", global=" + global
				+ ", withGlobalDefaultSecondaryInMemoryRealm=" + withGlobalDefaultSecondaryInMemoryRealm
				+ ", withRestDefaultSecondaryInMemoryRealm=" + withRestDefaultSecondaryInMemoryRealm
				+ ", withSiteDefaultSecondaryInMemoryRealm=" + withSiteDefaultSecondaryInMemoryRealm + "]";
	}


	public ForJdbcRealmProperties getSite() {
		return site;
	}


	public void setSite(ForJdbcRealmProperties site) {
		this.site = site;
	}


	public ForJdbcRealmProperties getRest() {
		return rest;
	}


	public void setRest(ForJdbcRealmProperties rest) {
		this.rest = rest;
	}


	public ForJdbcRealmProperties getGlobal() {
		return global;
	}


	public void setGlobal(ForJdbcRealmProperties global) {
		this.global = global;
	}


	public boolean isWithGlobalDefaultSecondaryInMemoryRealm() {
		return withGlobalDefaultSecondaryInMemoryRealm;
	}


	public void setWithGlobalDefaultSecondaryInMemoryRealm(boolean withGlobalDefaultSecondaryInMemoryRealm) {
		this.withGlobalDefaultSecondaryInMemoryRealm = withGlobalDefaultSecondaryInMemoryRealm;
	}


	public boolean isWithRestDefaultSecondaryInMemoryRealm() {
		return withRestDefaultSecondaryInMemoryRealm;
	}


	public void setWithRestDefaultSecondaryInMemoryRealm(boolean withRestDefaultSecondaryInMemoryRealm) {
		this.withRestDefaultSecondaryInMemoryRealm = withRestDefaultSecondaryInMemoryRealm;
	}


	public boolean isWithSiteDefaultSecondaryInMemoryRealm() {
		return withSiteDefaultSecondaryInMemoryRealm;
	}


	public void setWithSiteDefaultSecondaryInMemoryRealm(boolean withSiteDefaultSecondaryInMemoryRealm) {
		this.withSiteDefaultSecondaryInMemoryRealm = withSiteDefaultSecondaryInMemoryRealm;
	}

	

	
}

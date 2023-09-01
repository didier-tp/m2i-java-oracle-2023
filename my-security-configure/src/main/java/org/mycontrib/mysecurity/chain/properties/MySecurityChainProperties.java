package org.mycontrib.mysecurity.chain.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="mysecurity.chain")
public class MySecurityChainProperties {
	

	public MySecurityChainProperties() {
		super();
	}

	private String restAuthType; //"StandaloneJwt" or "OAuth2ResourceServer"
	                             //default is "OAuth2ResourceServer"

	public String getRestAuthType() {
		return restAuthType;
	}

	public void setRestAuthType(String restAuthType) {
		this.restAuthType = restAuthType;
	}
	
    

}

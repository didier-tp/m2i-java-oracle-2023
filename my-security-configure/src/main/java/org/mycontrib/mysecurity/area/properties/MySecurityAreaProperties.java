package org.mycontrib.mysecurity.area.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/*
 a decomposer en 3 parties (abstract + webSite + restApi )
 
 mysecurity.area.whitelist 
 deviendra
 mysecurity.site.whitelist 
 plus
 mysecurity.rest.whitelist
 
 */

@ConfigurationProperties(prefix="mysecurity.area")
public class MySecurityAreaProperties {
	
	private String staticWhitelist; //public static area (*.html , *.jpeg , ...)
	private String toolsWhitelist; //public static area for tools (swagger /openapidoc , h2-console, ...)
	private String whitelist; //public area , whitelist (no need of authentication , all mode:GET/POST/PUT/DELETE)
	private String readonlyWhitelist;//whitelist in mode GET only 
	         //(nb: all elements in readonlyWhitelist will also automatic be put in protectedlist for other modes (POST/PUT/...)
	private String protectedlist;//need of authentication in all mode (GET, POST , PUT , DELETE , ...)
	
	//NB: default values can be set by WebProtectedAreaConfigurer

	public MySecurityAreaProperties() {
		super();
	}

	@Override
	public String toString() {
		return "AreaProperties [staticWhitelist=" + staticWhitelist + ", whitelist=" + whitelist
				+ ", readonlyWhitelist=" + readonlyWhitelist + ", protectedlist=" + protectedlist 
				+ ", toolsWhitelist=" + toolsWhitelist + "]";
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

	public String getToolsWhitelist() {
		return toolsWhitelist;
	}

	public void setToolsWhitelist(String toolsWhitelist) {
		this.toolsWhitelist = toolsWhitelist;
	}




}

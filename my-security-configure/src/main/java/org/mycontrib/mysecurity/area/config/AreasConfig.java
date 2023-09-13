package org.mycontrib.mysecurity.area.config;

//Web Protected Area Config as arrays
public class AreasConfig {
	private String[] staticWhitelist; 
	private String[] apiWhitelist ;
	private String[] apiReadonlyWhitelist ; 
	private String[] apiProtectedlist;
	private String[] toolsWhitelist; //h2-console , swagger, ...
	
	public String[] getStaticWhitelist() {
		return staticWhitelist;
	}
	public void setStaticWhitelist(String[] staticWhitelist) {
		this.staticWhitelist = staticWhitelist;
	}
	public String[] getApiWhitelist() {
		return apiWhitelist;
	}
	public void setApiWhitelist(String[] apiWhitelist) {
		this.apiWhitelist = apiWhitelist;
	}
	public String[] getApiReadonlyWhitelist() {
		return apiReadonlyWhitelist;
	}
	public void setApiReadonlyWhitelist(String[] apiReadonlyWhitelist) {
		this.apiReadonlyWhitelist = apiReadonlyWhitelist;
	}
	public String[] getApiProtectedlist() {
		return apiProtectedlist;
	}
	public void setApiProtectedlist(String[] apiProtectedlist) {
		this.apiProtectedlist = apiProtectedlist;
	}
	public String[] getToolsWhitelist() {
		return toolsWhitelist;
	}
	public void setToolsWhitelist(String[] toolsWhitelist) {
		this.toolsWhitelist = toolsWhitelist;
	}
	
	

}

package org.mycontrib.mysecurity.standalone.dto;


public class LoginResponse {
	private String username;
	private Boolean ok; //true or false
	private String message; // "authentification réussie" ou "echec ...."
	private String token; //jwt ou autre
	
	public LoginResponse(String username, Boolean ok, String message, String token) {
		super();
		this.username = username;
		this.ok = ok;
		this.message = message;
		this.token = token;
	}
	

	public LoginResponse() {
		super();
	}
	
	

	@Override
	public String toString() {
		return "LoginResponse [username=" + username + ", ok=" + ok + ", message=" + message + ", token=" + token + "]";
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getOk() {
		return ok;
	}

	public void setOk(Boolean ok) {
		this.ok = ok;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
	
}


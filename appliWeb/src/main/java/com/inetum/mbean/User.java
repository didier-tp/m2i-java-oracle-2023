package com.inetum.mbean;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.context.FacesContext;
@ManagedBean
@SessionScoped
public class User {
	
	private String username;
	private String password;
	private Boolean isConnected=false;
	
	public String login() {
		String suite=null;
		if(this.username != null && this.password != null
		   && this.password.equals("pwd" + this.username)) {
			 //mot de passe considéré comme correct si "pwd" + valeur du username
			this.isConnected = true;
			suite = "user"; //pour demnder à naviguer vers user.xhtml/jsp/jsf
		}else {
			this.isConnected = false;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("login fail" , "wrong password"));
		}
		return suite;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsConnected() {
		return isConnected;
	}

	public void setIsConnected(Boolean isConnected) {
		this.isConnected = isConnected;
	}

	
	
}

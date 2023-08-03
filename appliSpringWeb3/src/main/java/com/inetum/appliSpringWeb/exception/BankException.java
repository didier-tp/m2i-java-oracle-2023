package com.inetum.appliSpringWeb.exception;

//developpeur faineant --> BankException generique
//developpeur courageux --> VirementException + SoldeInsuffisantException
public class BankException extends RuntimeException {
	
	//private String detail;

	public BankException() {
	}

	public BankException(String message) {
		super(message);
	}

	
	public BankException(String message, Throwable cause) {
		super(message, cause);
	}

	
}

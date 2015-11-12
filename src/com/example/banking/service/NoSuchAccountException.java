package com.example.banking.service;

@SuppressWarnings("serial")
public class NoSuchAccountException extends Exception {

	private final String iban;

	public NoSuchAccountException(String iban, String message) {
		super(message);
		this.iban= iban;		
	}

	public String getIban() {
		return iban;
	}

}

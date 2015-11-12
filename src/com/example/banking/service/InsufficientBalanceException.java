package com.example.banking.service;

@SuppressWarnings("serial")
public class InsufficientBalanceException extends Exception {

	private final double deficit;

	public InsufficientBalanceException(double deficit, 
			    String message) {
		super(message);
		this.deficit= deficit;
	}

	public double getDeficit() {
		return deficit;
	}

}

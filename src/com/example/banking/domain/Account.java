package com.example.banking.domain;

import com.example.banking.service.InsufficientBalanceException;

public class Account {

	// information hiding
	private String iban; // attribute, state, data
	protected double balance;
	protected AccountStatus status; 
	// Constructor
	public Account(String iban, double balance) {
		this.iban = iban;
		this.balance = balance;
		status= AccountStatus.ACTIVE;
	}

	public final String getIban() {
		return iban;
	}

	public final double getBalance() {
		return balance;
	}

	public void deposit(final double amount) {
		// validation
		if (amount <= 0.0)
			throw new IllegalArgumentException("Amount cannot be negative!");
		if (status==AccountStatus.CLOSED || 
			status==AccountStatus.BLOCKED) // BR
			throw new IllegalStateException(
					"Account is closed or blocked!");
		// balance = balance + amount ;
		balance += amount;
	}

	public void withdraw(final double amount) 
			    throws InsufficientBalanceException
	   {
		if (amount <= 0.0)
			throw new IllegalArgumentException("Amount cannot be negative!");
		if (status==AccountStatus.CLOSED || 
				status==AccountStatus.BLOCKED) // BR
			throw new IllegalStateException(
					"Account is closed or blocked!");
		// business rule
		if (amount > balance) {
			double deficit = amount-balance;
			throw new InsufficientBalanceException(deficit,
			  "Your balance does not cover your expenses!");
		} 
		balance -= amount;
	}
 


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((iban == null) ? 0 : iban.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (iban == null) {
			if (other.iban != null)
				return false;
		} else if (!iban.equals(other.iban))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Account [iban=" + iban + ", balance=" + balance + "]";
	}

}

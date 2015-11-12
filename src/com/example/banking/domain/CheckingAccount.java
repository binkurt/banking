package com.example.banking.domain;

import com.example.banking.service.InsufficientBalanceException;

public class CheckingAccount extends Account {
	private double overdraftAmount;

	public CheckingAccount(String iban, double balance, double overdraftAmount) {
		super(iban, balance);
		this.overdraftAmount = overdraftAmount;
	}

	public double getOverdraftAmount() {
		return overdraftAmount;
	}

	@Override
	public void withdraw(double amount) 
			 throws InsufficientBalanceException {
		// validation
		if (amount <= 0.0)
			throw new IllegalArgumentException("Amount cannot be negative!");
		if (status==AccountStatus.CLOSED || 
			status==AccountStatus.BLOCKED) // BR
			throw new IllegalStateException(
					"Account is closed or blocked!");
		// BR
          if (amount > (balance+overdraftAmount)){
  			double deficit = amount-balance-overdraftAmount;
  			throw new InsufficientBalanceException(deficit,
  			  "Your balance does not cover your expenses!");
  		} 
        balance -= amount; // BL
	}
	
}

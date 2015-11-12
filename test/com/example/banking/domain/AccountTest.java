package com.example.banking.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.banking.service.InsufficientBalanceException;

public class AccountTest {

	@Test(expected=IllegalArgumentException.class)
	public void deposit_negativeAmount() throws Exception {
		Account account = new Account("TR1", 100.);
		account.deposit(-1.0);
	}

	@Test
	public void deposit_positiveAmount() throws Exception {
		Account account = new Account("TR1", 100.);
		account.deposit(10.0);
		assertEquals(110, account.getBalance(), 0.001);
	}

	@Test(expected=IllegalArgumentException.class)
	public void withdraw_negativeAmount() throws Exception {
		Account account = new Account("TR1", 100.);
		account.withdraw(-1.0);
	}
	
	@Test(expected=InsufficientBalanceException.class)
	public void withdraw_overBalance() throws Exception {
		Account account = new Account("TR1", 100.);
		account.withdraw(101.0);
	}
	
	@Test
	public void withdraw_ok() throws Exception {
		Account account = new Account("TR1", 100.);
		account.withdraw(100.0);
		assertEquals(0.0, account.getBalance(), 0.001);
	}
}

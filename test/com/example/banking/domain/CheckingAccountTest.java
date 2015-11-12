package com.example.banking.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.banking.service.InsufficientBalanceException;

public class CheckingAccountTest {

	@Test(expected=IllegalArgumentException.class)
	public void withdraw_negativeAmount() throws Exception {
		CheckingAccount account = new CheckingAccount("TR1", 1_000, 500.);
		account.withdraw(-10);
	}

	@Test
	public void widthdraw_belowBalance() throws Exception {
		CheckingAccount account = new CheckingAccount("TR1", 1_000, 500.);
		account.withdraw(1_000);
		assertEquals(0., account.getBalance(), 0.001);
	}

	@Test
	public void withdraw_overBalance() throws Exception {
		CheckingAccount account = new CheckingAccount("TR1", 1_000, 500.);
		account.withdraw(1_500);
		assertEquals(-500., account.getBalance(), 0.001);
	}

	@Test(expected=InsufficientBalanceException.class)
	public void withdraw_overDraftAmount() throws Exception {
		CheckingAccount account = new CheckingAccount("TR1", 1_000, 500.);
		account.withdraw(1_501);
	}
}

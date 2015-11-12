package com.example.banking.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CustomerTest {

	@Test
	public void createCustomer_noAccount() throws Exception {
		Customer customer = new Customer("1", "Jack", "Shephard");
		assertEquals("1", customer.getIdentityNo());
		assertEquals("Jack", customer.getFirstName());
		assertEquals("Shephard", customer.getLastName());
		assertEquals(0, customer.getNumberOfAccounts());
	}

	@Test
	public void addAccount_oneAccount() throws Exception {
		Customer customer = new Customer("1", "Jack", "Shephard");
		assertEquals(0, customer.getNumberOfAccounts());
		Account account = new Account("1", 100.);
		assertTrue(customer.addAccount(account));
		assertEquals(1, customer.getNumberOfAccounts());
		assertEquals(account, customer.getAccount("1"));
	}

	@Test
	public void addAccount_twoAccounts() throws Exception {
		Customer customer = new Customer("1", "Jack", "Shephard");
		assertEquals(0, customer.getNumberOfAccounts());
		Account account1 = new Account("1", 100.);
		Account account2 = new Account("2", 200.);
		assertTrue(customer.addAccount(account1));
		assertTrue(customer.addAccount(account2));
		assertEquals(2, customer.getNumberOfAccounts());
		assertEquals(account1, customer.getAccount(0));
		assertEquals(account2, customer.getAccount(1));
	}

}

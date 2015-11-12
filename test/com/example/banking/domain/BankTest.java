package com.example.banking.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.banking.service.CustomerService;
import com.example.banking.service.InsufficientBalanceException;

public class BankTest {

	@Test
	public void createEmptyBank() throws Exception {
		Bank bank = new Bank("1", "garanti");
		assertEquals("1", bank.getId());
		assertEquals("garanti", bank.getName());
		assertEquals(0, bank.getNumberOfCustomers());
	}

	@Test
	public void addCustomer_oneCustomer() throws Exception {
		CustomerService bank = new Bank("1", "garanti");
		assertEquals(0, bank.getNumberOfCustomers());
		Customer customer = bank.createCustomer("1", "Kate", "Austen");
		assertEquals(1, bank.getNumberOfCustomers());
		assertEquals("1", customer.getIdentityNo());
		assertEquals("Kate", customer.getFirstName());
		assertEquals("Austen", customer.getLastName());
		Customer found = bank.getCustomer("1");
		assertEquals(found, customer);
	}

	@Test
	public void addCustomer_twoCustomers() throws Exception {
		CustomerService bank = new Bank("1", "garanti");
		assertEquals(0, bank.getNumberOfCustomers());
		Customer kate = bank.createCustomer("1", "Kate", "Austen");
		Customer jack = bank.createCustomer("2", "James", "Sawyer");
		assertEquals(2, bank.getNumberOfCustomers());
		Customer first = bank.getCustomer(0);
		Customer second = bank.getCustomer(1);
		assertEquals(kate, first);
		assertEquals(jack, second);
	}

	@Test
	public void findAccount() throws Exception {
		Bank bank = new Bank("1", "garanti");
		Customer kate = bank.createCustomer("1", "Kate", "Austen");
		Customer jack = bank.createCustomer("2", "James", "Sawyer");
		kate.addAccount(new Account("TR1", 10_000));
		kate.addAccount(new Account("TR2", 20_000));
		jack.addAccount(new Account("TR3", 30_000));
		jack.addAccount(new Account("TR4", 40_000));
		assertEquals(new Account("TR1", 10_000), bank.findAccount("TR1"));
		assertEquals(new Account("TR2", 20_000), bank.findAccount("TR2"));
		assertEquals(new Account("TR3", 30_000), bank.findAccount("TR3"));
		assertEquals(new Account("TR4", 40_000), bank.findAccount("TR4"));
	}

	@Test
	public void transfer_success() throws Exception {
		Bank bank = new Bank("1", "garanti");
		Customer kate = bank.createCustomer("1", "Kate", "Austen");
		Customer jack = bank.createCustomer("2", "James", "Sawyer");
		Account tr1 = new Account("TR1", 10_000);
		Account tr2 = new Account("TR2", 20_000);
		Account tr3 = new Account("TR3", 30_000);
		Account tr4 = new Account("TR4", 40_000);
		kate.addAccount(tr1);
		kate.addAccount(tr2);
		jack.addAccount(tr3);
		jack.addAccount(tr4);
		bank.transfer("TR1", "TR3", 10_000);
		assertEquals(0.0, tr1.getBalance(), 0.001);
		assertEquals(40_000.0, tr3.getBalance(), 0.001);
	}

	@Test(expected = InsufficientBalanceException.class)
	public void transfer_fail() throws Exception {
		Bank bank = new Bank("1", "garanti");
		Customer kate = bank.createCustomer("1", "Kate", "Austen");
		Customer jack = bank.createCustomer("2", "James", "Sawyer");
		Account tr1 = new Account("TR1", 10_000);
		Account tr2 = new Account("TR2", 20_000);
		Account tr3 = new Account("TR3", 30_000);
		Account tr4 = new Account("TR4", 40_000);
		kate.addAccount(tr1);
		kate.addAccount(tr2);
		jack.addAccount(tr3);
		jack.addAccount(tr4);
		bank.transfer("TR1", "TR3", 10_001);
	}
}

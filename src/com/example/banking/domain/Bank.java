package com.example.banking.domain;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.banking.service.CustomerService;
import com.example.banking.service.InsufficientBalanceException;
import com.example.banking.service.NoSuchAccountException;
import com.example.banking.service.Reportable;
import com.example.banking.service.Transferable;

public class Bank implements CustomerService, Transferable, Reportable {
	private static final String CUSTOMER_ROW_FORMAT = "%-11s %-16s %-12s";
	private static final String ACCOUNT_ROW_FORMAT = "%-11s %9.2f";
	private static final String BUNDLE_BASENAME = "messages";
	private static final int COLUMN_SIZE = 42;
	private static final Locale tr_TR = new Locale("tr", "TR");
	private ResourceBundle bundle;
	private DateFormat df;
	private String id;
	private String name;
	private Map<String, Customer> customers = new LinkedHashMap<>();

	public Bank(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public int getNumberOfCustomers() {
		return customers.size();
	}

	@Override
	public Customer createCustomer(String identityNo, String firstName, String lastName) {
		Customer customer = new Customer(identityNo, firstName, lastName);
		customers.put(identityNo, customer);
		return customer;
	}

	@Override
	public Customer getCustomer(String identityNo) {
		return customers.get(identityNo);
	}

	@Override
	public Customer getCustomer(int index) {
		if (customers.isEmpty())
			return null;
		if (index < 0 || index >= getNumberOfCustomers())
			return null;
		int i = 0;
		for (Customer customer : customers.values()) {
			if (i == index)
				return customer;
			i++;
		}
		return null;
	}

	public Account findAccount(String iban) throws NoSuchAccountException {
		for (Customer customer : customers.values()) {
			Account account = customer.getAccount(iban);
			if (account != null)
				return account;
		}
		throw new NoSuchAccountException(iban, "Account does not exist!");
	}

	@Override
	public void transfer(String fromIban, String toIban, double amount)
			throws NoSuchAccountException, InsufficientBalanceException {
		Account fromAccount = findAccount(fromIban);
		Account toAccount = findAccount(toIban);
		fromAccount.withdraw(amount);
		try {
			toAccount.deposit(amount);
		} catch (Exception e) {
			fromAccount.deposit(amount);
			throw e;
		}
	}

	@Override
	public void report() {
		report(tr_TR);
	}

	@Override
	public void report(Locale locale) {
		bundle = ResourceBundle.getBundle(BUNDLE_BASENAME, locale);
		df = DateFormat.getDateInstance(DateFormat.FULL, locale);
		printReportHeader(locale);
		printEmptyLine();
		reportCustomers(locale);
	}

	private void printReportHeader(Locale locale) {
		Date now = new Date();
		MessageFormat formatter = new MessageFormat(bundle.getString("label.generate.time"), locale);
		System.out.println(formatter.format(new Object[] { df.format(now) }));
	}

	private void reportCustomers(Locale locale) {
		printCustomerTableHeader();
		printHR('=', COLUMN_SIZE);
		for (Customer customer : customers.values()) {
			printCustomer(customer);
			printEmptyLine();
			printNumberOfAccounts(customer, locale);
			printCustomerAccounts(customer);
			printEmptyLine();
		}
	}

	private void printNumberOfAccounts(Customer customer, Locale locale) {
		MessageFormat formatter = new MessageFormat(bundle.getString("message.numberOfAccounts"), locale);
		System.out.println(formatter.format(new Object[] { customer.getNumberOfAccounts() }));
	}

	private void printCustomerAccounts(Customer customer) {
		for (int i = 0; i < customer.getNumberOfAccounts(); ++i) {
			Account account = customer.getAccount(i);
			System.out.println(String.format(ACCOUNT_ROW_FORMAT, getAccountType(account), account.getBalance()));
		}

	}

	private String getAccountType(Account account) {
		return bundle.getString(account.getClass().getSimpleName());
	}

	private void printCustomer(Customer customer) {
		System.out.println(String.format(CUSTOMER_ROW_FORMAT, customer.getIdentityNo(), customer.getLastName(),
				customer.getFirstName()));
	}

	private void printEmptyLine() {
		System.out.println();
	}

	private void printHR(char c, int length) {
		String hr = Stream.generate(() -> String.valueOf(c)).limit(length).collect(Collectors.joining());
		System.out.println(hr);

	}

	private void printCustomerTableHeader() {
		System.out.println(String.format(CUSTOMER_ROW_FORMAT, bundle.getString("column.identityNo"),
				bundle.getString("column.lastName"), bundle.getString("column.firstName")));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Bank other = (Bank) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

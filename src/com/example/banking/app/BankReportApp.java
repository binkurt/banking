package com.example.banking.app;

import java.util.Locale;

import com.example.banking.domain.Account;
import com.example.banking.domain.Bank;
import com.example.banking.domain.Customer;
import com.example.banking.service.CustomerService;
import com.example.banking.service.Reportable;

public class BankReportApp {

	public static void main(String[] args) {
		Bank bank = new Bank("1", "garanti");
		createCustomers(bank);		
        generateReport(bank,Locale.ENGLISH);
        generateReport(bank,new Locale("tr","TR"));
	}

	private static void generateReport(Reportable reportable,Locale locale) {
		reportable.report(locale);
	}

	private static void createCustomers(CustomerService customerService) {
		Customer kate = customerService.createCustomer("1", "Kate", "Austen");
		Customer jack = customerService.createCustomer("2", "James", "Sawyer");
		kate.addAccount(new Account("TR1",10_000));
		kate.addAccount(new Account("TR2",20_000));
		jack.addAccount(new Account("TR3",30_000));
		jack.addAccount(new Account("TR4",40_000));
	}

}

package com.example.banking.domain;

import com.example.banking.service.CustomerService;

public class Branch {
	// Dependency Injection
	private CustomerService customerService;

	
	public Branch() {
	}

	public Branch(CustomerService customerService) {
		this.customerService = customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public void fun() {
		int numberOfCustomers = customerService.getNumberOfCustomers();
		System.err.println(numberOfCustomers);
	}

	public static void main(String[] args) {
		Bank garanti= new Bank("1","garanti");
		Branch uskudar= new Branch(); 
		// DI by setter
		uskudar.setCustomerService(garanti);
		uskudar.fun(); // NPE
	}
}

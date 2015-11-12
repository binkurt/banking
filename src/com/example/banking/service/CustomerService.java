package com.example.banking.service;

import com.example.banking.domain.Customer;

public interface CustomerService {

	int getNumberOfCustomers();

	Customer createCustomer(String identityNo, String firstName, String lastName);

	Customer getCustomer(String identityNo);

	Customer getCustomer(int index);

}
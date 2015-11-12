package com.example.banking.domain;

import java.util.ArrayList;
import java.util.List;

public class Customer {
	private String identityNo;
	private String firstName;
	private String lastName;
	private List<Account> accounts= new ArrayList<Account>();

	public Customer(String identityNo, String firstName, String lastName) {
		this.identityNo = identityNo;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getIdentityNo() {
		return identityNo;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getNumberOfAccounts() {
		return accounts.size();
	}
	
	public boolean addAccount(Account account){
		if (account == null) return false; // validation
		accounts.add(account);
		return true;
	}
	
	public Account getAccount(int index){
		if (index<0 || index >= getNumberOfAccounts()) 
			return null;
		return accounts.get(index);
	}
	
	public Account getAccount(String iban){
		if (accounts.isEmpty()) return null;
		for (Account account: accounts){
			if (account.getIban().equals(iban))
				return account;
		}
		return null;	
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((identityNo == null) ? 0 : identityNo.hashCode());
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
		Customer other = (Customer) obj;
		if (identityNo == null) {
			if (other.identityNo != null)
				return false;
		} else if (!identityNo.equals(other.identityNo))
			return false;
		return true;
	}
	
}

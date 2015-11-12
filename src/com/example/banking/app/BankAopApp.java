package com.example.banking.app;

import java.lang.reflect.Proxy;

import com.example.banking.aop.BddkAuditHandler;
import com.example.banking.domain.Account;
import com.example.banking.domain.Bank;
import com.example.banking.domain.Customer;
import com.example.banking.service.InsufficientBalanceException;
import com.example.banking.service.NoSuchAccountException;
import com.example.banking.service.Transferable;

public class BankAopApp {

	public static void main(String[] args) throws NoSuchAccountException, InsufficientBalanceException {
		Bank bank = new Bank("1", "garanti");
		Customer kate = bank.createCustomer("1", "Kate", "Austen");
		Customer jack = bank.createCustomer("2", "James", "Sawyer");
		kate.addAccount(new Account("TR1",10_000));
		kate.addAccount(new Account("TR2",20_000));
		jack.addAccount(new Account("TR3",30_000));
		jack.addAccount(new Account("TR4",40_000));		
		Class<? extends Bank> clazz = bank.getClass();
		Transferable transferable=
				(Transferable) 
			Proxy.newProxyInstance(
				clazz.getClassLoader(), 
				clazz.getInterfaces(),
				new BddkAuditHandler(bank));
		transferable.transfer("TR1", "TR3", 7_500);
		transferable.transfer("TR2", "TR4", 2_500);
		transferable.transfer("TR3", "TR1", 5_000);
	}

}

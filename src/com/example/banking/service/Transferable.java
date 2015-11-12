package com.example.banking.service;

import com.example.banking.aop.AuditTransfer;

public interface Transferable {
	@AuditTransfer(language = "FR", country = "FR")
	void transfer(String fromIban, String toIban, double amount)
			throws NoSuchAccountException, InsufficientBalanceException;
}

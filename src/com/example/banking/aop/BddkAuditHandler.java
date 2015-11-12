package com.example.banking.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class BddkAuditHandler implements InvocationHandler {
	private Object target;
	private double TRANSFER_LIMIT;

	public BddkAuditHandler(Object target) {
		this.target = target;
		ResourceBundle bundle = ResourceBundle.getBundle("bddk");
		String limit = bundle.getString("transfer.audit.limit");
		TRANSFER_LIMIT = Double.parseDouble(limit);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (method.isAnnotationPresent(AuditTransfer.class)) {
			AuditTransfer auditTransfer = method.getAnnotation(AuditTransfer.class);
			int amountParamOrder = auditTransfer.amountParamOrder();
			double amount = (Double) args[amountParamOrder];
			if (amount >= TRANSFER_LIMIT) {
				int fromIbanParamOrder = auditTransfer.fromIbanParamOrder();
				int toIbanParamOrder = auditTransfer.toIbanParamOrder();
				String language = auditTransfer.language();
				String country = auditTransfer.country();
				String fromIban = (String) args[fromIbanParamOrder];
				String toIban = (String) args[toIbanParamOrder];
				printTransfer(amount, fromIban, toIban, language, country);
			}
		}
		return method.invoke(target, args);
	}

	private void printTransfer(double amount, String fromIban, String toIban, String language, String country) {
		Locale locale = new Locale(language, country);
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
		System.err.println(
				String.format("Transferring %s from %s to %s.", numberFormat.format(amount), fromIban, toIban));
	}

}

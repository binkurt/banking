package com.example.banking.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface AuditTransfer {
	int fromIbanParamOrder() default 0;

	int toIbanParamOrder() default 1;

	int amountParamOrder() default 2;

	String language() default "tr";

	String country() default "TR";
}

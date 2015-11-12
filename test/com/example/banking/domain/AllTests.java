package com.example.banking.domain;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AccountTest.class, BankTest.class, CheckingAccountTest.class, CustomerTest.class })
public class AllTests {

}

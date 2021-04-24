package com.iyte.bankatm.tek_bankatm;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.time.LocalDate;

import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.*;
public class TransactionTest {
	
	private Bank ParentBank =  new Bank();
	ATM TestATM = ParentBank.getMyATM();;
    MonetaryAmount maxWithdrawPerDayAccount = Money.of(4000, "USD");
    MonetaryAmount CashOnHand = Money.of(158743, "USD");
	int minWithdrawPerTransaction = 20;
	int maxWithdrawPerTransaction = 20000;
	Account MainAccount = ParentBank.createNewAccount(1234, "123", 0, 33333, LocalDate.of(2022, 1, 8));
	Account SecondaryAccount;
	Card TestCard; 
	
	
	Transaction TestTransaction;
	
	@Rule
	  public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();
	
	@Before
    public void setUp() {
		TestATM.getMyOperatorPanel().initializeATM(CashOnHand, minWithdrawPerTransaction, maxWithdrawPerTransaction, maxWithdrawPerDayAccount);
		TestTransaction = new Transaction(TestATM, TestCard);
    }
	/*
	@Test
	public void readAccountNumberTest() {
		systemInMock.provideLines(String.valueOf(MainAccount.getAccount_number()));
		assertEquals(MainAccount, TestTransaction.readAccountNumber());
	}
	
	@Test
	public void readAmountTest() {
		String Value = "1597";
		systemInMock.provideLines(Value);
		TestTransaction.readAmount();
		assertEquals(Double.parseDouble(Value), TestTransaction.getAmount(), 0.0001);
	}
	
	*/
	
	@Test
	public void SuccessfulVerifyTest() {
		TestTransaction.setAmount(maxWithdrawPerTransaction - 150);
		assertEquals(true, TestTransaction.verify());
	}
	@Test
	public void UnSuccessfulVerifyTestWithTooMuchWithdraw() {
		TestTransaction.setAmount(maxWithdrawPerTransaction + 150);
		assertEquals(false, TestTransaction.verify());
	}
	@Test
	public void UnSuccessfulVerifyTestWithLessATM() {
		TestATM.getMyCashDispenser().setInitialCash(Money.of(250, "USD"));
		TestTransaction.setAmount(maxWithdrawPerTransaction - 350);
		assertEquals(false, TestTransaction.verify());
	}
}

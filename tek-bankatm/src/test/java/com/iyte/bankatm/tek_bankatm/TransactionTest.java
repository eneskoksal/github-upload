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
	Account SecondaryAccount = ParentBank.createNewAccount(4321, "123", 0, 33333, LocalDate.of(2022, 1, 8));
	Card TestCard = MainAccount.getMyCard(); 
	
	
	Transaction TestTransaction;
	
	@Rule
	  public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();
	
	@Before
    public void setUp() {
		TestATM.getMyOperatorPanel().initializeATM(CashOnHand, minWithdrawPerTransaction, maxWithdrawPerTransaction, maxWithdrawPerDayAccount);
		TestTransaction = new Transaction(TestATM, TestCard);
    }
	
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
	@Test
	public void SuccessfulinitiateSequenceTest() {
		MainAccount.setBalance(CashOnHand);
		MainAccount.setLeftMaxWithdrawPerDay(maxWithdrawPerDayAccount);
		TestTransaction.setAmount(500);
		TestTransaction.initiateSequence();
		assertEquals(CashOnHand.subtract(Money.of(500, "USD")), MainAccount.getBalance());
		//Ekleme
	}	
	@Test
	public void UnSuccessfulinitiateSequenceTestWithTooMuchWithdraw() {
		MainAccount.setBalance(CashOnHand);
		MainAccount.setLeftMaxWithdrawPerDay(maxWithdrawPerDayAccount);
		TestTransaction.setAmount(maxWithdrawPerDayAccount.getNumber().doubleValue() + 150);
		TestTransaction.initiateSequence();
		assertEquals(ATMstate.EJECTING_CARD, TestATM.getState());
		assertEquals(CashOnHand, MainAccount.getBalance());
	}
	@Test
	public void UnSuccessfulinitiateSequenceTestWithBalanceNotEnough() {
		MainAccount.setBalance(CashOnHand);
		MainAccount.setLeftMaxWithdrawPerDay(maxWithdrawPerDayAccount);
		TestTransaction.setAmount(CashOnHand.getNumber().doubleValue() + 150);
		TestTransaction.initiateSequence();
		assertEquals(ATMstate.EJECTING_CARD, TestATM.getState());
		assertEquals(CashOnHand, MainAccount.getBalance());
	}
	
	@Test
	public void SuccessfulinitiateTransferTest() {
		MainAccount.setBalance(CashOnHand);
		MainAccount.setLeftMaxWithdrawPerDay(maxWithdrawPerDayAccount);
		SecondaryAccount.setLeftMaxWithdrawPerDay(maxWithdrawPerDayAccount);
		TestTransaction.setFromAccount(MainAccount);
		TestTransaction.setToAccount(SecondaryAccount);
		TestTransaction.setAmount(150);
		TestTransaction.initiateTransfer();
		
		assertEquals(CashOnHand.subtract(Money.of(150, "USD")), MainAccount.getBalance());
		assertEquals(Money.of(150, "USD"), SecondaryAccount.getBalance());
	}
	
	@Test
	public void UnSuccessfulinitiateTransferTestWithNotEnoughBalance() {
		MainAccount.setBalance(CashOnHand);
		MainAccount.setLeftMaxWithdrawPerDay(maxWithdrawPerDayAccount);
		SecondaryAccount.setLeftMaxWithdrawPerDay(maxWithdrawPerDayAccount);
		TestTransaction.setFromAccount(MainAccount);
		TestTransaction.setToAccount(SecondaryAccount);
		TestTransaction.setAmount(CashOnHand.add(Money.of(150, "USD")).getNumber().doubleValue());
		TestTransaction.initiateTransfer();
		
		assertEquals(CashOnHand, MainAccount.getBalance());
		assertEquals(Money.of(0, "USD"), SecondaryAccount.getBalance());
	}		
	@Test
	public void UnSuccessfulinitiateTransferTestWithLimitExceed() {
		MainAccount.setBalance(maxWithdrawPerDayAccount.add(Money.of(1789, "USD")));
		MainAccount.setLeftMaxWithdrawPerDay(maxWithdrawPerDayAccount);
		SecondaryAccount.setLeftMaxWithdrawPerDay(maxWithdrawPerDayAccount);
		TestTransaction.setFromAccount(MainAccount);
		TestTransaction.setToAccount(SecondaryAccount);
		TestTransaction.setAmount(maxWithdrawPerDayAccount.add(Money.of(150, "USD")).getNumber().doubleValue());
		TestTransaction.initiateTransfer();
		
		assertEquals(maxWithdrawPerDayAccount.add(Money.of(1789, "USD")), MainAccount.getBalance());
		assertEquals(Money.of(0, "USD"), SecondaryAccount.getBalance());
	}	
}

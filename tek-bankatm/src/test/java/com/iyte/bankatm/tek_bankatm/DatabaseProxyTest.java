package com.iyte.bankatm.tek_bankatm;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;

public class DatabaseProxyTest {
	/*
	private DatabaseProxy testDatabaseProxy;
	private Account testAccounts[];
	private int NumberOfTestAccount = 1000; //Number of testAccount = number of test run
	
	//Generate random date
	private LocalDate randLocalDate() {
		long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
	    long maxDay = LocalDate.of(2030, 12, 31).toEpochDay();
	    long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
	    LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
	    return randomDate;
	}
	
	//Create new object before tests
    @Before
    public void setUp() {
    	testDatabaseProxy = new DatabaseProxy();
    	testAccounts = new Account[NumberOfTestAccount];
    	//Add random accounts into database array
    	for(int i=0; i<NumberOfTestAccount; i++) {
	    	//Generate account with random parameters
			int randomAccountNum = ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);			
			int randomSerialNum = ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
			LocalDate randomDate = randLocalDate();
			testAccounts[i] = new Account(Money.of(1000, "USD"));
			testAccounts[i].setAccount_number(randomAccountNum);
			testAccounts[i].setMyCard(new Card(randomSerialNum,randomDate));
			testDatabaseProxy.createNewAccount(testAccounts[i]);
    	}
    }
    
    @Test
    public void selectAccountByCardSerialNoTest_OK() {
    	for(int i=NumberOfTestAccount-1; i>=0; i--) {
    		Account findAccount = testDatabaseProxy.selectAccountByCardSerialNo(testAccounts[i].getMyCard().getSerialNumber());
    		assertEquals(testAccounts[i], findAccount);
    	}
    }
    @Test
    public void selectAccountByCardSerialNoTest_NULL() {
    	//There is no account tied with serialNumber = Integer.MAX_VALUE.
		Account findAccount = testDatabaseProxy.selectAccountByCardSerialNo(Integer.MAX_VALUE);
		assertEquals(null, findAccount);    	
    }
    @Test
    public void selectAccountByAccountNumberTest_OK() {
    	for(int i=NumberOfTestAccount-1; i>=0; i--) {
    		Account findAccount = testDatabaseProxy.selectAccountByAccountNumber(testAccounts[i].getAccount_number());
    		assertEquals(testAccounts[i], findAccount);
    	}
    }
    @Test
    public void selectAccountByAccountNumberTest_NULL() {    	
		//There is no account tied with accountNumber = Integer.MAX_VALUE.
		Account findAccount = testDatabaseProxy.selectAccountByAccountNumber(Integer.MAX_VALUE);
		assertEquals(null, findAccount);    	
    }
    @Test
    public void setWrongPasswordCountTest() {
    	for(int i=NumberOfTestAccount-1; i>=0; i--) {
    		int randomValue = ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
    		testDatabaseProxy.setWrongPasswordCount(testAccounts[i].getMyCard().getSerialNumber(), randomValue);
    		assertEquals(randomValue, testAccounts[i].getWrongPasswordCount());
    	}
    }
    @Test
    public void getWrongPasswordCountTest() {
    	for(int i=NumberOfTestAccount-1; i>=0; i--) {
    		int randomValue = ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
    		testAccounts[i].setWrongPasswordCount(randomValue);
    		int getValue = testDatabaseProxy.getWrongPasswordCount(testAccounts[i].getMyCard().getSerialNumber());
    		assertEquals(randomValue, getValue);
    	}
    }
    @Test
    public void minusBalanceTest() {
    	for(int i=NumberOfTestAccount-1; i>=0; i--) {
    		//Generate random value in between min and max
    		double randomAmount = Double.MIN_VALUE + Math.random() * (Double.MAX_VALUE - Double.MIN_VALUE);
    		double randomBalance = Double.MIN_VALUE + Math.random() * (Double.MAX_VALUE - Double.MIN_VALUE);
    		testAccounts[i].setBalance(Money.of(randomBalance, "USD"));
    		testDatabaseProxy.minusBalance(testAccounts[i].getMyCard().getSerialNumber(), Money.of(randomAmount, "USD"));    		
    		assertEquals(Money.of(randomBalance, "USD").subtract(Money.of(randomAmount, "USD")), testAccounts[i].getBalance());
    	}    	
    }
    @Test
    public void minusBalanceTest_Min() {		
		double randomAmount = Double.MIN_VALUE;
		double randomBalance = Double.MIN_VALUE;
		testAccounts[0].setBalance(Money.of(randomBalance, "USD"));
		testDatabaseProxy.minusBalance(testAccounts[0].getMyCard().getSerialNumber(), Money.of(randomAmount, "USD"));
		assertEquals(Money.of(randomBalance, "USD").subtract(Money.of(randomAmount, "USD")), testAccounts[0].getBalance());
    }
    @Test
    public void minusBalanceTest_Max() {		
		double randomAmount = Double.MAX_VALUE;
		double randomBalance = Double.MAX_VALUE;
		testAccounts[0].setBalance(Money.of(randomBalance, "USD"));
		testDatabaseProxy.minusBalance(testAccounts[0].getMyCard().getSerialNumber(), Money.of(randomAmount, "USD"));
		assertEquals(Money.of(randomBalance, "USD").subtract(Money.of(randomAmount, "USD")), testAccounts[0].getBalance());
    }
    
    @Test
    public void plusBalanceTest() {
    	for(int i=NumberOfTestAccount-1; i>=0; i--) {
    		//Generate random value in between min and max
    		double randomAmount = Double.MIN_VALUE + Math.random() * (Double.MAX_VALUE - Double.MIN_VALUE);
    		double randomBalance = Double.MIN_VALUE + Math.random() * (Double.MAX_VALUE - Double.MIN_VALUE);
    		testAccounts[i].setBalance(Money.of(randomBalance, "USD"));
    		testDatabaseProxy.plusBalance(testAccounts[i].getMyCard().getSerialNumber(), Money.of(randomAmount, "USD"));    		
    		assertEquals(Money.of(randomBalance, "USD").add(Money.of(randomAmount, "USD")), testAccounts[i].getBalance());
    	}
    }
    @Test
    public void plusBalanceTest_Min() {		
		double randomAmount = Double.MIN_VALUE;
		double randomBalance = Double.MIN_VALUE;
		testAccounts[0].setBalance(Money.of(randomBalance, "USD"));
		testDatabaseProxy.plusBalance(testAccounts[0].getMyCard().getSerialNumber(), Money.of(randomAmount, "USD"));    		
		assertEquals(Money.of(randomBalance, "USD").add(Money.of(randomAmount, "USD")), testAccounts[0].getBalance());    	
    }
    @Test
    public void plusBalanceTest_Max() {    			
		double randomAmount = Double.MAX_VALUE;
		double randomBalance = Double.MAX_VALUE;
		testAccounts[0].setBalance(Money.of(randomBalance, "USD"));
		testDatabaseProxy.plusBalance(testAccounts[0].getMyCard().getSerialNumber(), Money.of(randomAmount, "USD"));    		
		assertEquals(Money.of(randomBalance, "USD").add(Money.of(randomAmount, "USD")), testAccounts[0].getBalance());	
    }    
    @Test
    public void setLeftMaxWithdrawPerDayTest() {
    	for(int i=NumberOfTestAccount-1; i>=0; i--) {
    		//Generate random value in between min and max
    		double randomAmount = Double.MIN_VALUE + Math.random() * (Double.MAX_VALUE - Double.MIN_VALUE);
    		double randomInitLimit = Double.MIN_VALUE + Math.random() * (Double.MAX_VALUE - Double.MIN_VALUE);    		
    		MonetaryAmount randomWithdrawAmount = Money.of(randomAmount, "USD");
    		MonetaryAmount randomInitLimitAmount = Money.of(randomInitLimit, "USD");
    		testAccounts[i].setLeftMaxWithdrawPerDay(randomInitLimitAmount);
    		testDatabaseProxy.setLeftMaxWithdrawPerDay(testAccounts[i].getMyCard().getSerialNumber(), randomWithdrawAmount);    		
    		assertEquals(randomInitLimitAmount.subtract(randomWithdrawAmount), testAccounts[i].getLeftMaxWithdrawPerDay());
    	}
    }
    @Test
    public void setLeftMaxWithdrawPerDayTest_Min() {
		double randomAmount = Double.MIN_VALUE;
		double randomInitLimit = Double.MIN_VALUE;    		
		MonetaryAmount randomWithdrawAmount = Money.of(randomAmount, "USD");
		MonetaryAmount randomInitLimitAmount = Money.of(randomInitLimit, "USD");
		testAccounts[0].setLeftMaxWithdrawPerDay(randomInitLimitAmount);
		testDatabaseProxy.setLeftMaxWithdrawPerDay(testAccounts[0].getMyCard().getSerialNumber(), randomWithdrawAmount);    		
		assertEquals(randomInitLimitAmount.subtract(randomWithdrawAmount), testAccounts[0].getLeftMaxWithdrawPerDay());
    }
    @Test
    public void setLeftMaxWithdrawPerDayTest_Max() {
		double randomAmount = Double.MAX_VALUE;
		double randomInitLimit = Double.MAX_VALUE;    		
		MonetaryAmount randomWithdrawAmount = Money.of(randomAmount, "USD");
		MonetaryAmount randomInitLimitAmount = Money.of(randomInitLimit, "USD");
		testAccounts[0].setLeftMaxWithdrawPerDay(randomInitLimitAmount);
		testDatabaseProxy.setLeftMaxWithdrawPerDay(testAccounts[0].getMyCard().getSerialNumber(), randomWithdrawAmount);    		
		assertEquals(randomInitLimitAmount.subtract(randomWithdrawAmount), testAccounts[0].getLeftMaxWithdrawPerDay());
    }
    */
}

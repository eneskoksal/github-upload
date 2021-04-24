package com.iyte.bankatm.tek_bankatm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;

public class BankTest {
	
	private Bank testBank;
	private Account testAccountOK; //OK
	private Account testAccountNullPassword; //null password
	
	//Generate random numeric string
	private String randNumericString() {
		int randomPassword = 0;
		while(true) {
			randomPassword = ThreadLocalRandom.current().nextInt(100, 9999);
			if(randomPassword != 123)
				break;
		}
		return String.valueOf(randomPassword);
	}
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
    	testBank = new Bank();
    	testBank.getMyATM().setMaxWithdrawPerDayAccount(Money.of(1000, "USD"));
    	testAccountOK = testBank.createNewAccount(1234, "123", 0, 33333, LocalDate.of(2022, 1, 8));
    	testAccountNullPassword = testBank.createNewAccount(1234, null, 0, 44444, LocalDate.of(2022, 1, 8));    	
    }
    
    
	@Test
	public void verifyRequestTest_AccountOK() {		
		//Check Password and cardSerialNumber matches		
		assertEquals("account ok", testBank.verifyRequest("123", 33333));				
	}
	@Test
	public void verifyRequestTest_BadAccount() {
		//Check bad account
		assertEquals("bad account", testBank.verifyRequest("", 44444));		
	}
	@Test
	public void verifyRequestTest_BadBankCode() {
		//Check bad bank code - account not exist
		assertEquals("bad bank code", testBank.verifyRequest(randNumericString(), 33339));		
	}
	@Test
	public void verifyRequestTest_BadPasswordLimit() {
		//Check Password is wrong
		for(int i=1; i<10; i++) {
			String response = "";
			String password = randNumericString();			
			if(i<4)				
				response = "bad password";				
			else if(i>=4)
				response = "keep the card";
			
			assertEquals(response, testBank.verifyRequest(password, 33333));
		}
	}
	@Test
	public void verifyRequestTest_BadPassword() {
		String password = "123";		
		for(int i=1; i<13; i++) {
			if(i % 4 == 0) { //Enter correct password after 3 wrong attempt
				password = "123";
				assertEquals("account ok", testBank.verifyRequest(password, 33333));
			}
			else { //Enter wrong password 3 consecutive attempt
				password = randNumericString();
				assertEquals("bad password", testBank.verifyRequest(password, 33333));
			}			
		}
	}
	@Test
	public void verifyTransactionTest_Success() {
		//balance == withdraw amount == LeftMaxWithdrawPerDay
		testAccountOK.setBalance(Money.of(1000, "USD"));
		MonetaryAmount withdrawAmount = Money.of(1000, "USD");
		int cardSerialNumber = testAccountOK.getMyCard().getSerialNumber();				
		assertEquals("transaction succeeded", testBank.verifyTransaction(cardSerialNumber, withdrawAmount));
		
		//balance is slightly greater than amount		
		testAccountOK.setBalance(Money.of(1000.00001, "USD"));
		withdrawAmount = Money.of(1000, "USD");
		cardSerialNumber = testAccountOK.getMyCard().getSerialNumber();		
		assertEquals("transaction succeeded", testBank.verifyTransaction(cardSerialNumber, withdrawAmount));
	}
	@Test
	public void verifyTransactionTest_LowBalance() {
		//balance is slightly lower than amount
		testAccountOK.setBalance(Money.of(999.9999, "USD"));
		MonetaryAmount withdrawAmount = Money.of(1000, "USD");
		int cardSerialNumber = testAccountOK.getMyCard().getSerialNumber();	
		assertEquals("transaction failed", testBank.verifyTransaction(cardSerialNumber, withdrawAmount));
		
		//balance is 0		
		testAccountOK.setBalance(Money.of(0, "USD"));
		withdrawAmount = Money.of(1000, "USD");
		cardSerialNumber = testAccountOK.getMyCard().getSerialNumber();		
		assertEquals("transaction failed", testBank.verifyTransaction(cardSerialNumber, withdrawAmount));
		
		//balance is < 0		
		testAccountOK.setBalance(Money.of(-1, "USD"));
		withdrawAmount = Money.of(1000, "USD");
		cardSerialNumber = testAccountOK.getMyCard().getSerialNumber();		
		assertEquals("transaction failed", testBank.verifyTransaction(cardSerialNumber, withdrawAmount));
	}
	@Test
	public void verifyTransactionTest_WithdrawLimit() {
		//Amount is slightly higher than daily withdraw limit
		testAccountOK.setBalance(Money.of(1500, "USD"));
		MonetaryAmount withdrawAmount = Money.of(1000.0001, "USD");
		int cardSerialNumber = testAccountOK.getMyCard().getSerialNumber();	
		assertEquals("transaction failed", testBank.verifyTransaction(cardSerialNumber, withdrawAmount));		
	}
	@Test
	public void updateAccountTest() {
		/* Test if updateAccount method calling respective methods in DatabaseProxy
		 * Called methods are tested separately in DatabaseProxy*/		
		testAccountOK.setBalance(Money.of(1500.05, "USD"));
		MonetaryAmount LeftWithdrawBefore = testAccountOK.getLeftMaxWithdrawPerDay();		
		int cardSerialNumber = testAccountOK.getMyCard().getSerialNumber();
		MonetaryAmount Amount = Money.of(1000, "USD");
		
		testBank.updateAccount(cardSerialNumber, Amount);
		
		MonetaryAmount NewBalance = testAccountOK.getBalance();
		MonetaryAmount LeftWithdrawAfter = testAccountOK.getLeftMaxWithdrawPerDay();		
		assertEquals(NewBalance, Money.of(1500.05, "USD").subtract(Amount));
		assertEquals(LeftWithdrawAfter, LeftWithdrawBefore.subtract(Amount));
	}
	@Test
	public void createNewAccountTest() {
		//Generate an account with random parameters, observe it is not NULL
		int randomAccountNum = ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
		String randomPassword = randNumericString();
		int randomAccountType = ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
		int randomSerialNum = ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
		LocalDate randomDate = randLocalDate();
		Account createdAccount = testBank.createNewAccount(randomAccountNum, randomPassword, 
									randomAccountType, randomSerialNum, randomDate);		
		assertNotNull(createdAccount);
	}
	@Test
	public void verifyAccountNumberTest() {
		//Check if previously created testAccountOK is get correctly
		int accountNumber = testAccountOK.getAccount_number();
		assertEquals(testAccountOK, testBank.verifyAccountNumber(accountNumber));
	}
	
}


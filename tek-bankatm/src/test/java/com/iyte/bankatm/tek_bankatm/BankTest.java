package com.iyte.bankatm.tek_bankatm;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;

public class BankTest {
	
	private Bank testBank;
	
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
	
	//Create new object before tests
    @Before
    public void setUp() {
    	testBank = new Bank();
    	testBank.createNewAccount(1234, "123", 0, 33333, LocalDate.of(2022, 1, 8));
    	testBank.createNewAccount(1234, null, 0, 44444, LocalDate.of(2022, 1, 8));
    	testBank.getMyATM().setMaxWithdrawPerDayAccount(Money.of(1500, "USD"));
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
	
}


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
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.*;
public class ATMTest {
	
	private Bank ParentBank =  new Bank();
	ATM TestATM = ParentBank.getMyATM();;
    MonetaryAmount maxWithdrawPerDayAccount = Money.of(4000, "USD");
    MonetaryAmount CashOnHand = Money.of(158743, "USD");
	int minWithdrawPerTransaction = 20;
	int maxWithdrawPerTransaction = 20000;
	Account MainAccount;
	Account SecondaryAccount;
	Card TestCard; 
	//

	
  @Rule
  public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();
  

	
	@Before
    public void setUp() {
		TestATM.getMyOperatorPanel().initializeATM(CashOnHand, minWithdrawPerTransaction, maxWithdrawPerTransaction, maxWithdrawPerDayAccount);

		MainAccount = ParentBank.createNewAccount(1234, "123", 0, 33333, LocalDate.of(2022, 1, 8));
		SecondaryAccount = ParentBank.createNewAccount(4321, "1453", 0, 33333, LocalDate.of(2022, 1, 8));
		MainAccount.setBalance(Money.of(1500, "USD"));
		SecondaryAccount.setBalance(Money.of(2000, "USD"));
		TestCard = MainAccount.getMyCard();
    }

    @After
    public void restoreSystemInput() {
        System.setIn(System.in);        
    }
   
	@Test
	public void SuccessfulWithdrawTest() {
	    systemInMock.provideLines("123", "w", "50");
		TestATM.userInsertedCard(TestCard);
		assertEquals(Money.of(1450, "USD"), MainAccount.getBalance());
	}
	
	@Test
	public void UnSuccessfulWithdrawTestwithWrongPassword() {
	    systemInMock.provideLines("1234");
		TestATM.userInsertedCard(TestCard);
		assertEquals(ATMstate.EJECTING_CARD, TestATM.getState());
	}
	@Test
	public void UnSuccessfulWithdrawTestwithRetainCard() {
	    systemInMock.provideLines("1234", "1234", "1234", "1234");
		TestATM.userInsertedCard(TestCard);
		TestATM.userInsertedCard(TestCard);
		TestATM.userInsertedCard(TestCard);
		TestATM.userInsertedCard(TestCard);
		assertEquals(ATMstate.RETAINING_CARD, TestATM.getState());
	}	
	
	@Test
	public void UnSuccessfulWithdrawTestwithNoCash() {
		TestATM.getMyCashDispenser().setInitialCash(Money.of(0, "USD"));
		TestATM.userInsertedCard(TestCard);
		assertEquals(ATMstate.EJECTING_CARD, TestATM.getState());
	}	
	
	@Test
	public void UnSuccessfulWithdrawTestwithExpiredDate() {
		TestCard.setExpireDate(LocalDate.of(1789, 5, 5));
		TestATM.userInsertedCard(TestCard);
		assertEquals(ATMstate.EJECTING_CARD, TestATM.getState());
	}	
	
	@Test
	public void UnSuccessfulWithdrawTestwithNoAccountPassword() {
	    systemInMock.provideLines("123");
		MainAccount.setPassword(null);
		TestATM.userInsertedCard(TestCard);
		assertEquals(ATMstate.EJECTING_CARD,TestATM.getState() );
	}	
	
    
	@Test
	public void UnSuccessfulWithdrawTestwithNoCard() {
	    systemInMock.provideLines("123");
		TestCard = null;
		TestATM.userInsertedCard(TestCard);
		assertEquals(ATMstate.EJECTING_CARD,TestATM.getState() );
	}	
	
	@Test
	public void SuccessfulTransferTest() {
	    systemInMock.provideLines("123", "t", "4321", "150");
		TestATM.userInsertedCard(TestCard);
		assertEquals(Money.of(2150, "USD"), SecondaryAccount.getBalance());
		assertEquals(Money.of(1350, "USD"), MainAccount.getBalance());
		assertEquals(Money.of(3850, "USD"), MainAccount.getLeftMaxWithdrawPerDay());
		
	}
	
	@Test
	public void UnSuccessfulTransferTestwithInvalidAccount() {
	    systemInMock.provideLines("123", "t", "1598", "150");
		TestATM.userInsertedCard(TestCard);
		assertEquals(ATMstate.FailedTransfer, TestATM.getState());
		
	}
}

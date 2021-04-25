package com.iyte.bankatm.tek_bankatm;

import static org.junit.Assert.assertEquals;

import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;

public class CashDispenserTest {
	private CashDispenser testCashDispenser;
	
	private MonetaryAmount randomMoney() {
		double randomDbl = Double.MIN_VALUE + Math.random() * (Double.MAX_VALUE - Double.MIN_VALUE);
		MonetaryAmount randomAmount = Money.of(randomDbl, "USD");
		return randomAmount;
	}
	@Before
	public void setUp() {
		testCashDispenser = new CashDispenser();		
	}
	
	@Test
	public void dispenseCashTest() {
		MonetaryAmount initCash = randomMoney();
		testCashDispenser.setInitialCash(initCash);
		MonetaryAmount dispensedAmount = randomMoney();
		testCashDispenser.dispenseCash(dispensedAmount);
		assertEquals(initCash.subtract(dispensedAmount), testCashDispenser.getCashOnHand());		
	}

}

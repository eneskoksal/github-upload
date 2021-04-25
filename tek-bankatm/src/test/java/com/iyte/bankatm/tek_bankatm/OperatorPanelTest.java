package com.iyte.bankatm.tek_bankatm;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ThreadLocalRandom;

import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;

public class OperatorPanelTest {
	
	private OperatorPanel testOperatorPanel;
	private ATM testATM;
	
	private double randomInitAmount_dbl = Double.MIN_VALUE + Math.random() * (Double.MAX_VALUE - Double.MIN_VALUE);
	private double randomMaxWithdrawPerDay_dbl = Double.MIN_VALUE + Math.random() * (Double.MAX_VALUE - Double.MIN_VALUE);
	private MonetaryAmount randomInitAmount;
	private MonetaryAmount randomMaxWithdrawPerDay;
	private int randomMinWithdraw;
	private int randomMaxWithdraw;
	
	@Before
	public void setUp() {	
		testATM = new ATM(new Bank());
		testOperatorPanel = new OperatorPanel(testATM);
		//Generate random parameters
		randomInitAmount_dbl = Double.MIN_VALUE + Math.random() * (Double.MAX_VALUE - Double.MIN_VALUE);
		randomMaxWithdrawPerDay_dbl = Double.MIN_VALUE + Math.random() * (Double.MAX_VALUE - Double.MIN_VALUE);
		randomInitAmount = Money.of(randomInitAmount_dbl, "USD");
		randomMaxWithdrawPerDay = Money.of(randomMaxWithdrawPerDay_dbl, "USD");
		randomMinWithdraw = ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
		randomMaxWithdraw = ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	@Test
	public void initializeATMTest() {
		//Test if atm.setInitialParameters method is calling correctly		
		testOperatorPanel.initializeATM(randomInitAmount, randomMinWithdraw, randomMaxWithdraw, randomMaxWithdrawPerDay);
		assertEquals(randomInitAmount, testATM.getCashOnHand());
		assertEquals(randomMinWithdraw, testATM.getMinWithdrawPerTransaction());
		assertEquals(randomMaxWithdraw, testATM.getMaxWithdrawPerTransaction());
		assertEquals(randomMaxWithdrawPerDay, testATM.getMaxWithdrawPerDayAccount());
		//getInitialCash Testing
		assertEquals(testOperatorPanel.getInitialCash(), testATM.getCashOnHand());		
	}	
	
}

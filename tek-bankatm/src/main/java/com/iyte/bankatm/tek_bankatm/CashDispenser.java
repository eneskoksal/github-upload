package com.iyte.bankatm.tek_bankatm;

import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;

public class CashDispenser {

	private MonetaryAmount cashOnHand;

	public CashDispenser () {
		this.cashOnHand = Money.of(0, "USD");
	}
	
	public MonetaryAmount getCashOnHand() {
		return this.cashOnHand;
	}	
	/**
	 * 
	 * @param initialCash
	 */
	public void setInitialCash(MonetaryAmount initialCash) {
		this.cashOnHand = initialCash;
	}

	/**
	 * 
	 * @param amount
	 */
	public void dispenseCash(MonetaryAmount amount) {		
		this.cashOnHand = this.cashOnHand.subtract(amount);		
	}


}
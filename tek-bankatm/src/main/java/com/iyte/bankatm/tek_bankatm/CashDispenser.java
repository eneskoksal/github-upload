package com.iyte.bankatm.tek_bankatm;

import javax.money.MonetaryAmount;

public class CashDispenser {

	private MonetaryAmount cashOnHand;

	
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
package com.iyte.bankatm.tek_bankatm;

import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;

public class CashDispenser {

	private Log log;
	private MonetaryAmount cashOnHand;

	/**
	 * 
	 * @param log
	 */
	public CashDispenser(Log log) {
		// TODO - implement CashDispenser.CashDispenser
		throw new UnsupportedOperationException();
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
	public boolean checkCashOnHand(MonetaryAmount amount) {
		//Return true if input currencies are same and amount is less than cash on hand.
		return (this.cashOnHand.getNumber().doubleValue() >= amount.getNumber().doubleValue()
				&& this.cashOnHand.getCurrency() == amount.getCurrency());
	}

	/**
	 * 
	 * @param amount
	 */
	public void dispenseCash(MonetaryAmount amount) {
		// TODO - implement CashDispenser.dispenseCash
		throw new UnsupportedOperationException();
	}

	public void putCash() {
		// TODO - implement CashDispenser.putCash
		throw new UnsupportedOperationException();
	}

}
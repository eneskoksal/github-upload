package com.iyte.bankatm.tek_bankatm;

import javax.money.MonetaryAmount;

public class Withdrawal extends Transaction {

	public Withdrawal(ATM atm,Session session, Card card, int pin) {
		super(atm, card);
		throw new UnsupportedOperationException();
		// TODO Auto-generated constructor stub
	}

	private int from;
	private MonetaryAmount amount;

	/**
	 * 
	 * @param atm
	 * @param session
	 * @param card
	 * @param pin
	 */
	
	public Message getSpecificsFromCustomer() {
		// TODO - implement Withdrawal.getSpecificsFromCustomer
		throw new UnsupportedOperationException();
	}

	public Receipt completeTransaction() {
		// TODO - implement Withdrawal.completeTransaction
		throw new UnsupportedOperationException();
	}

}
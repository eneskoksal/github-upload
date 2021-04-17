package com.iyte.bankatm.tek_bankatm;

import javax.money.MonetaryAmount;

public class Deposit extends Transaction {

	private int to;
	private MonetaryAmount amount;

	/**
	 * 
	 * @param atm
	 * @param session
	 * @param card
	 * @param pin
	 */
	public Deposit(ATM atm, Session session, Card card, int pin) {
		// TODO - implement Deposit.Deposit

		super(atm, card);
		throw new UnsupportedOperationException();
	}

	public Message getSpecificsFromCustomer() {
		// TODO - implement Deposit.getSpecificsFromCustomer
		throw new UnsupportedOperationException();
	}

	public Receipt completeTransaction() {
		// TODO - implement Deposit.completeTransaction
		throw new UnsupportedOperationException();
	}

}
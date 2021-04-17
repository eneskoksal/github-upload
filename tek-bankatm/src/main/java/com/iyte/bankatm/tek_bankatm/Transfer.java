package com.iyte.bankatm.tek_bankatm;

import javax.money.MonetaryAmount;

public class Transfer extends Transaction {

	private int from;
	private int to;
	private MonetaryAmount amount;

	/**
	 * 
	 * @param atm
	 * @param session
	 * @param card
	 * @param pin
	 */
	public Transfer(ATM atm, Session session, Card card, int pin) {
		super(atm, card);
		// TODO - implement Transfer.Transfer
		throw new UnsupportedOperationException();
	}

	public Message getSpecificsFromCustomer() {
		// TODO - implement Transfer.getSpecificsFromCustomer
		throw new UnsupportedOperationException();
	}

	public Receipt completeTransaction() {
		// TODO - implement Transfer.completeTransaction
		throw new UnsupportedOperationException();
	}

}
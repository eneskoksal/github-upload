package com.iyte.bankatm.tek_bankatm;

import javax.money.MonetaryAmount;

public class Log {

	public Log() {
		
	}

	/**
	 * 
	 * @param message
	 */
	public void logSend(String message) {
		System.out.println("Log: " + message);
	}

	/**
	 * 
	 * @param status
	 */
	public void logResponse(Status status) {
		// TODO - implement Log.logResponse
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param amount
	 */
	public void logCashDispensed(MonetaryAmount amount) {
		// TODO - implement Log.logCashDispensed
		throw new UnsupportedOperationException();
	}

	public void logEnvelopeAccepted() {
		// TODO - implement Log.logEnvelopeAccepted
		throw new UnsupportedOperationException();
	}

}
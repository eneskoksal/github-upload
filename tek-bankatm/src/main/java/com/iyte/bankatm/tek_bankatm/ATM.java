package com.iyte.bankatm.tek_bankatm;

import java.time.LocalDateTime;
import javax.money.MonetaryAmount;
import org.javamoney.moneta.Money;

public class ATM {

	private int minWithdrawPerTransaction;
	private int maxWithdrawPerTransaction;
	private int maxWithdrawPerDayAccount;
	private int limitTimeForOperation;
	private MonetaryAmount initAmount;
	CashDispenser MyCashDispenser = new CashDispenser(new Log());
	
	//ATM Functional requirement 1
	public ATM(MonetaryAmount initAmount, int minWithdrawPerTransaction, 
			int maxWithdrawPerTransaction, int maxWithdrawPerDayAccount) {		
		this.initAmount = initAmount;
		this.minWithdrawPerTransaction = minWithdrawPerTransaction;
		this.maxWithdrawPerTransaction = maxWithdrawPerTransaction;
		this.maxWithdrawPerDayAccount = maxWithdrawPerDayAccount;
	}
	
	public void setInitialCash(MonetaryAmount amount) {
		this.MyCashDispenser.setInitialCash(initAmount);
	}
		
	
	/**
	 * 
	 * @param password
	 */
	public String verify(String password) {
		// TODO - implement ATM.verify
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param accountNum
	 */
	public void readAccountNum(int accountNum) {
		// TODO - implement ATM.readAccountNum
		throw new UnsupportedOperationException();
	}

	public Message checkAvailabilityOfCashInATM() {
		// TODO - implement ATM.checkAvailabilityOfCashInATM
		throw new UnsupportedOperationException();
	}

	public Message verifyInputAmount() {
		// TODO - implement ATM.verifyInputAmount
		throw new UnsupportedOperationException();
	}

	public LocalDateTime checkTime() {
		// TODO - implement ATM.checkTime
		return LocalDateTime.now();
	}

}